package ru.otus.spring.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

@ChangeLog(order = "001")
@SuppressWarnings("unused")
public class DatabaseChangelog {

    private final ObjectId nikolayId = ObjectId.get();

    private final ObjectId ivanId = ObjectId.get();

    @ChangeSet(order = "001", id = "dropDb", author = "Tema", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertContacts", author = "Tema")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("contact");

        var nikolay = new Document().append("name", "Nikolay").append("_id", nikolayId)
                .append("phone", "8573235442");
        var ivan = new Document().append("name", "Ivan").append("_id", ivanId).append("phone", "9111235443");
        myCollection.insertMany(List.of(nikolay, ivan));
    }
}
