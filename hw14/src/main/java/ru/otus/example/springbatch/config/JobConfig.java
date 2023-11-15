package ru.otus.example.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.springbatch.listeners.ChunkListenerImpl;
import ru.otus.example.springbatch.listeners.ItemReadListenerImpl;
import ru.otus.example.springbatch.listeners.ItemWriteListenerImpl;
import ru.otus.example.springbatch.model.Author;
import ru.otus.example.springbatch.model.BookWithComments;
import ru.otus.example.springbatch.model.Genre;
import ru.otus.example.springbatch.model.h2.AuthorDto;
import ru.otus.example.springbatch.model.h2.BookDto;
import ru.otus.example.springbatch.model.h2.CommentDto;
import ru.otus.example.springbatch.model.h2.GenreDto;
import ru.otus.example.springbatch.service.TransformIdService;

import javax.sql.DataSource;
import java.util.Map;


@SuppressWarnings("unused")
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public MongoItemReader<BookWithComments> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<BookWithComments>()
                .name("bookItemReader")
                .targetType(BookWithComments.class)
                .template(template)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }

    @StepScope
    @Bean
    public IteratorItemReader<CommentDto> commentReader(TransformIdService libraryIdService) {
        return new IteratorItemReader<>(libraryIdService.getComments());
    }
    
    @Bean
    public MongoItemReader<Author> authorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .targetType(Author.class)
                .template(template)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }

    @Bean
    public MongoItemReader<Genre> genreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .targetType(Genre.class)
                .template(template)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }

    @Bean
    public ItemProcessor<BookWithComments, BookDto> bookProcessor(TransformIdService service) {
        return service::transform;
    }

    @Bean
    public ItemProcessor<Author, AuthorDto> authorProcessor(TransformIdService service) {
        return service::transform;
    }

    @Bean
    public ItemProcessor<Genre, GenreDto> genreProcessor(TransformIdService service) {
        return service::transform;
    }

    @Bean
    public JdbcBatchItemWriter<BookDto> writerBook(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into BOOKS (ID, NAME, AUTHOR_ID, GENRE_ID) values (:id, :name, :author.id, :genre.id)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<CommentDto> commentWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CommentDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into COMMENTS (ID, BODY, BOOK_ID) values (:id, :body, :bookId)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<AuthorDto> authorWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AuthorDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into AUTHORS (ID, NAME) values (:id, :name)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<GenreDto> genreWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<GenreDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into GENRES (ID, NAME) values (:id, :name)")
                .build();
    }

    @Bean
    public Job importUserJob(Step transformAuthorsStep, Step transformGenresStep, Step transformCommentsStep,
                             Step transformBooksStep) {
        return new JobBuilder(IMPORT_USER_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .next(transformCommentsStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step transformAuthorsStep(ItemReader<Author> reader,
                                     ItemWriter<AuthorDto> writer,
                                     ItemProcessor<Author, AuthorDto> itemProcessor) {
        return new StepBuilder("transformAuthorsStep", jobRepository)
                .<Author, AuthorDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }

    @Bean
    public Step transformGenresStep(ItemReader<Genre> reader,
                                    ItemWriter<GenreDto> writer,
                                    ItemProcessor<Genre, GenreDto> itemProcessor) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<Genre, GenreDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }

    @Bean
    public Step transformCommentsStep(ItemReader<CommentDto> reader,
                                      ItemWriter<CommentDto> writer) {
        return new StepBuilder("transformCommentsStep", jobRepository)
                .<CommentDto, CommentDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }


    @Bean
    public Step transformBooksStep(ItemReader<BookWithComments> reader,
                                   ItemWriter<BookDto> writer,
                                   ItemProcessor<BookWithComments, BookDto> itemProcessor) {
        return new StepBuilder("transformBooksStep", jobRepository)
                .<BookWithComments, BookDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }
}
