package ru.otus.spring.initializer;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactRepositoryInitializer {

    private final ReactiveMongoTemplate template;

    private final MongoClient mongoClient;

    private final ObjectId nikolayId = ObjectId.get();

    private final ObjectId ivanId = ObjectId.get();

    @PostConstruct
    private void initialize() throws Throwable {
        Publisher<Void> phoneBook = mongoClient.getDatabase("phoneBook").drop();
        SubscriberHelpers.ObservableSubscriber<Void> dropSubscriber = new SubscriberHelpers.ObservableSubscriber<>();
        phoneBook.subscribe(dropSubscriber);
        dropSubscriber.await();

        Mono<MongoCollection<Document>> contact = template.getCollection("contact");

        var nikolay = new Document().append("name", "Nikolay").append("_id", nikolayId)
                .append("phone", "8573235442");
        var ivan = new Document().append("name", "Ivan").append("_id", ivanId).append("phone", "9111235443");
        Publisher<InsertManyResult> insertManyResultPublisher = contact.block().insertMany(List.of(nikolay, ivan));
        SubscriberHelpers.ObservableSubscriber<InsertManyResult> insertSubscriber =
                new SubscriberHelpers.ObservableSubscriber<>();
        insertManyResultPublisher.subscribe(insertSubscriber);
        insertSubscriber.await();
    }
}
