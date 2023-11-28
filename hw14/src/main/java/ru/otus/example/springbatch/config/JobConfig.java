package ru.otus.example.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.example.springbatch.customReader.MongoDBCommentItemReader;
import ru.otus.example.springbatch.listeners.ChunkListenerImpl;
import ru.otus.example.springbatch.listeners.ItemReadListenerImpl;
import ru.otus.example.springbatch.listeners.ItemWriteListenerImpl;
import ru.otus.example.springbatch.model.Author;
import ru.otus.example.springbatch.model.Book;
import ru.otus.example.springbatch.model.CommentMongo;
import ru.otus.example.springbatch.model.Genre;
import ru.otus.example.springbatch.model.h2.AuthorDto;
import ru.otus.example.springbatch.model.h2.BookDto;
import ru.otus.example.springbatch.model.h2.CommentDto;
import ru.otus.example.springbatch.model.h2.GenreDto;
import ru.otus.example.springbatch.service.CleanMongoIdsService;
import ru.otus.example.springbatch.service.FillCacheService;
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

    @Autowired
    private FillCacheService fillCacheService;

    @Autowired
    private TransformIdService transformIdService;

    @Bean
    public MongoItemReader<Book> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .targetType(Book.class)
                .template(template)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }

    @Bean
    public MongoItemReader<CommentMongo> commentReader(MongoTemplate template) {
        MongoDBCommentItemReader<CommentMongo> reader = new MongoDBCommentItemReader<>();
        reader.setTemplate(template);
        reader.setQuery("{}");
        reader.setSort(Map.of());
        reader.setName("commentItemReader");
        reader.setTargetType(CommentMongo.class);
        reader.setCollection("books");
        reader.setUnwind(Aggregation.unwind("comments"));
        reader.setProjection(Aggregation.project().and("comments.text").as("text").and("_id").as("bookId"));
        return reader;
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
    public ItemProcessor<Book, BookDto> bookProcessor() {
        return transformIdService::transform;
    }

    @Bean
    public ItemProcessor<CommentMongo, CommentDto> commentProcessor() {
        return transformIdService::transform;
    }

    @Bean
    public ItemProcessor<Author, AuthorDto> authorProcessor() {
        return transformIdService::transform;
    }

    @Bean
    public ItemProcessor<Genre, GenreDto> genreProcessor() {
        return transformIdService::transform;
    }

    @Bean
    public JdbcBatchItemWriter<BookDto> writerBook(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BookDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into BOOKS (NAME, AUTHOR_ID, GENRE_ID, MONGO_ID) values (:name, :author, :genre, :mongoId)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<AuthorDto> authorWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AuthorDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into AUTHORS (NAME, MONGO_ID) values (:name, :mongoId)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<CommentDto> commentWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CommentDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into COMMENTS (BODY, BOOK_ID) values (:body, :bookId)")
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<GenreDto> genreWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<GenreDto>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into GENRES (NAME, MONGO_ID) values (:name, :mongoId)")
                .build();
    }

    @Bean
    public Job importUserJob(Step transformAuthorsStep, Step transformGenresStep,
                             Step transformBooksStep, Step transformCommentsStep,
                             Step cleanMongoIdsStep) {
        return new JobBuilder(IMPORT_USER_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(fillCacheStep("AUTHORS", transformIdService.getAuthorIdMapping()))
                .next(transformGenresStep)
                .next(fillCacheStep("GENRES", transformIdService.getGenreIdMapping()))
                .next(transformBooksStep)
                .next(fillCacheStep("BOOKS", transformIdService.getBookIdMapping()))
                .next(transformCommentsStep)
                .next(cleanMongoIdsStep)
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
    public Step transformCommentsStep(ItemReader<CommentMongo> reader,
                                      ItemWriter<CommentDto> writer,
                                      ItemProcessor<CommentMongo, CommentDto> itemProcessor) {
        return new StepBuilder("transformGenresStep", jobRepository)
                .<CommentMongo, CommentDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }


    @Bean
    public Step transformBooksStep(ItemReader<Book> reader,
                                   ItemWriter<BookDto> writer,
                                   ItemProcessor<Book, BookDto> itemProcessor) {
        return new StepBuilder("transformBooksStep", jobRepository)
                .<Book, BookDto>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListenerImpl<>(logger))
                .listener(new ItemWriteListenerImpl<>(logger))
                .listener(new ChunkListenerImpl(logger))
                .build();
    }

    public MethodInvokingTaskletAdapter cleanUpTasklet(String tableName, Map<String, Long> cache) {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(fillCacheService);
        adapter.setTargetMethod("fillCache");
        adapter.setArguments(new Object[]{tableName, cache});
        return adapter;
    }

    public Step fillCacheStep(String tableName, Map<String, Long> cache) {
        return new StepBuilder(tableName + "fillCacheStep", jobRepository)
                .tasklet(cleanUpTasklet(tableName, cache), platformTransactionManager)
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanMongoIdsTasklet(CleanMongoIdsService cleanMongoIdsService) {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(cleanMongoIdsService);
        adapter.setTargetMethod("cleanIds");
        return adapter;
    }

    @Bean
    public Step cleanMongoIdsStep(MethodInvokingTaskletAdapter cleanMongoIdsTasklet) {
        return new StepBuilder("cleanMongoIds", jobRepository)
                .tasklet(cleanMongoIdsTasklet, platformTransactionManager)
                .build();
    }

}
