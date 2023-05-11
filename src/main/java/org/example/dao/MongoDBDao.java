package org.example.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.constants.Constants;
import org.example.types.BankDetails;
import org.example.util.MongoUtil;
import org.example.util.ObjectSerializerAndDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MongoDBDao {
    @Autowired
    ObjectSerializerAndDeserializer objectSerializerAndDeserializer;

    @Autowired
    MongoUtil mongoUtil;

    public boolean insertSingleDocumentToDB(MongoCollection<Document> collection, Document document) {
        InsertOneResult insertOneResult = collection.insertOne(document);
        return insertOneResult.wasAcknowledged();
    }

    public List<Document> fetchCompleteRecord(MongoCollection<Document> mongoCollection) {
        List<Document> documents = new ArrayList<>();
        mongoCollection.find().forEach(documents::add);
        return documents;
    }

    public Document fetchRecordWithFilter(MongoCollection<Document> mongoCollection, Bson filter) {
        FindIterable<Document> iterable = mongoCollection.find(filter).limit(1);
        return iterable.first();
    }


    public UpdateResult performUpdateOperation(String customerId, MongoCollection<Document> mongoCollection,
                                               BankDetails value, Document document) {
        Bson filter = Filters.eq(Constants.UNIQUE_ID.getName(), new ObjectId(customerId));
        System.out.println("Filter Created");
        System.out.println("Reflecting: " + ReflectionToStringBuilder.toString(filter));
        Bson update = Updates.set(Constants.BANK_DETAILS.getName(), objectSerializerAndDeserializer.serializeObject(value));
        System.out.println("Bson update Created");
        System.out.println("Reflecting: " + ReflectionToStringBuilder.toString(update));
        return mongoCollection.updateOne(filter, update);
    }

    public void dropDatabase() {
        MongoDatabase mongoDatabase = mongoUtil.acquireDatabase(Constants.DATABASE_NAME.getName());
        mongoDatabase.drop();
    }

}
