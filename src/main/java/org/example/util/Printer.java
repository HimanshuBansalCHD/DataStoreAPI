package org.example.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.constants.Constants;
import org.example.types.BankDetails;
import org.example.types.CustomerData;
import org.example.types.FacialFeatures;
import org.example.types.FingerPrintPattern;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

public class Printer {
    @Autowired
    Gson gson;

    @Autowired
    ObjectSerializerAndDeserializer objectSerializerAndDeserializer;

    public void printCollection(Document document) {
        CustomerData customerData = (CustomerData) objectSerializerAndDeserializer.deSerializeObject((String)document.get(Constants.CUSTOMER_DATA.getName()),
                (new TypeToken<CustomerData>(){}).getType());
        System.out.println(customerData.getAge() + " " + customerData.getName());

        BankDetails bankDetails = (BankDetails) objectSerializerAndDeserializer.deSerializeObject((String)document.get(Constants.BANK_DETAILS.getName()),
                (new TypeToken<BankDetails>(){}).getType());

        System.out.println(bankDetails.getAccountBalance() + " " + bankDetails.getAccountNumber());

        FacialFeatures facialFeatures = (FacialFeatures) objectSerializerAndDeserializer.deSerializeObject((String)document.get(Constants.FACIAL_FEATURES.getName()),
                (new TypeToken<FacialFeatures>(){}).getType());
        System.out.println(facialFeatures.getFacialFeature()[0]);

        FingerPrintPattern fingerPrintPattern = (FingerPrintPattern) objectSerializerAndDeserializer.deSerializeObject((String)document.get(Constants.FINGER_PRINT_PATTERN.getName()),
                (new TypeToken<FingerPrintPattern>(){}).getType());
        System.out.println(fingerPrintPattern.getFingerPrintPattern()[0]);

        System.out.println(document.get("_id"));
    }

}
