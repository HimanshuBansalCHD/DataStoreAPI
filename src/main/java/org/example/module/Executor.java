package org.example.module;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.constants.Constants;
import org.example.dao.MongoDBDao;
import org.example.types.BankDetails;
import org.example.util.MongoUtil;
import org.example.util.PrepareDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Executor {
    @Autowired
    MongoUtil mongoUtil;
    @Autowired
    PrepareDocument prepareDocument;
    @Autowired
    MongoDBDao mongoDBDao;

    private MongoCollection<Document> getCollection(String collectionName) {
        MongoDatabase mongoDatabase = mongoUtil.acquireDatabase(Constants.DATABASE_NAME.getName());
        return mongoUtil.acquireCollection(collectionName, mongoDatabase);
    }

    public void performInsertOperation() {

        for (int i = 0; i < 50; i++) {
            ObjectId objectId = new ObjectId();
            insertCustomerDetails(i, objectId);
            insertFacialFeatures(objectId);
            insertFingerPrintPattern(objectId);
            insertBankDetails(objectId);
        }

    }

    private void insertCustomerDetails(int index, ObjectId objectId) {
        MongoCollection<Document> customerDetailsCollection = getCollection(Constants.CUSTOMER_DETAILS_COLLECTION.getName());
        Document sampleCustomerDocument = prepareDocument.getSampleCustomerData(index%10, objectId);
        mongoDBDao.insertSingleDocumentToDB(customerDetailsCollection, sampleCustomerDocument);
    }
    private void insertFacialFeatures(ObjectId objectId) {
        MongoCollection<Document> facialFeaturesCollection = getCollection(Constants.FACIAL_FEATURES_COLLECTION.getName());
        Document sampleFacialFeaturesDocument = prepareDocument.getSampleFacialFeatures(objectId);
        mongoDBDao.insertSingleDocumentToDB(facialFeaturesCollection, sampleFacialFeaturesDocument);
    }
    private void insertFingerPrintPattern(ObjectId objectId) {
        MongoCollection<Document> fingerPrintPatternCollection = getCollection(Constants.FINGER_PRINT_PATTERN_COLLECTION.getName());
        Document sampleFingerPrintPatternDocument = prepareDocument.getSampleFingerPrintPattern(objectId);
        mongoDBDao.insertSingleDocumentToDB(fingerPrintPatternCollection, sampleFingerPrintPatternDocument);
    }

    private void insertBankDetails(ObjectId objectId) {
        MongoCollection<Document> bankDetailsCollection = getCollection(Constants.BANK_DETAILS_COLLECTION.getName());
        Document sampleBankDetailsDocument = prepareDocument.getSampleBankDetails(objectId);
        mongoDBDao.insertSingleDocumentToDB(bankDetailsCollection, sampleBankDetailsDocument);
    }

    public List<Document> performFindOperation(String collectionName) {

        MongoCollection<Document> mongoCollection = getCollection(collectionName);
        return mongoDBDao.fetchCompleteRecord(mongoCollection);
    }

    public Document performFindOperationWithFilter(String collectionName, Bson filter) {
        MongoCollection<Document> mongoCollection = getCollection(collectionName);
        return mongoDBDao.fetchRecordWithFilter(mongoCollection, filter);
    }

    public boolean performUpdateOperation(String customerId, BankDetails object, Document document, String collectionName) {

        MongoCollection<Document> mongoCollection = getCollection(collectionName);
        UpdateResult updateResult = mongoDBDao.performUpdateOperation(customerId, mongoCollection, object, document);
        System.out.println("Printing getModifiedCount: " + updateResult.getModifiedCount());

        return updateResult.getModifiedCount() > 0;
    }

    public void performDropOperation() {
        mongoDBDao.dropDatabase();
    }

}
