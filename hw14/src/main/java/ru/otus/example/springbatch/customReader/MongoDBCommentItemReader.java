package ru.otus.example.springbatch.customReader;

import lombok.Setter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;

import java.util.Iterator;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;

@Setter
public class MongoDBCommentItemReader<T> extends MongoItemReader<T> {

    private MongoOperations template;
    private Class<? extends T> targetType;
    private UnwindOperation unwind;
    private ProjectionOperation projection;
    private String collection;

    @Override
    public void setTargetType(Class<? extends T> targetType) {
        this.targetType = targetType;
        super.setTargetType(targetType);
    }

    @Override
    public void setTemplate(MongoOperations template) {
        this.template = template;
        super.setTemplate(template);
    }

    @Override
    protected Iterator<T> doPageRead() {
        Pageable page = PageRequest.of(this.page, pageSize);
        Aggregation agg = newAggregation(unwind, projection, skip((long) page.getPageNumber() * page.getPageSize()), limit(page.getPageSize()));
        return (Iterator<T>) template.aggregate(agg, collection, targetType).iterator();

    }
}
