package org.example.util;

import org.bson.BsonBinary;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.types.*;
import org.springframework.beans.factory.annotation.Autowired;

public class PrepareDocument {
    @Autowired
    RandomArrayGenerator randomArrayGenerator;
    @Autowired
    ObjectSerializerAndDeserializer objectSerializerAndDeserializer;

    String[] names = {"Himanshu", "Bhanu", "Gagan", "Tanmay", "Sai", "Vaishnavi", "John", "Peter", "Harry", "Jack"};
    int[] age = {23, 22, 22, 21, 23, 24, 65, 34, 67, 70};

    public Document getSampleCustomerData(int index, ObjectId objectId) {
        return new Document("_id", objectId)
                .append("CustomerData", objectSerializerAndDeserializer.serializeObject(CustomerData.builder()
                        .age(age[index])
                        .name(names[index])
                        .build()));
    }
    public Document getSampleFacialFeatures(ObjectId objectId) {
        return new Document("_id", objectId)
                .append("FacialFeatures", objectSerializerAndDeserializer.serializeObject(FacialFeatures.builder()
                                .facialFeature(randomArrayGenerator.generateByteArray(128))
                                .build()));
    }

    public Document getSampleFingerPrintPattern(ObjectId objectId) {
        return new Document("_id", objectId)
                .append("FingerPrintPattern", new BsonBinary(randomArrayGenerator.generateByteArray(512)));
    }

    public Document getSampleBankDetails(ObjectId objectId) {
        return new Document("_id", objectId)
                .append("BankDetails", objectSerializerAndDeserializer.serializeObject(BankDetails.builder()
                        .accountNumber(randomArrayGenerator.generateRandomAccountNumber())
                        .accountBalance(200)
                        .build()));
    }

}
