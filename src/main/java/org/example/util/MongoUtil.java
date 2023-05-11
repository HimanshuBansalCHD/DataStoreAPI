package org.example.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoUtil {
    @Autowired
    public MongoClient mongoClient;

    public MongoDatabase acquireDatabase(String database) {
        return mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> acquireCollection(String collection, MongoDatabase database) {
        return database.getCollection(collection);
    }

}
