package org.example.constants;

import lombok.Getter;

@Getter
public enum Constants {

    CUSTOMER_DATA("CustomerData"),
    BANK_DETAILS("BankDetails"),
    FACIAL_FEATURES("FacialFeatures"),
    FINGER_PRINT_PATTERN("FingerPrintPattern"),
    UNIQUE_ID("_id"),
    DATABASE_NAME("Himanshu"),
    COLLECTION_NAME("UserRecords"),

    BANK_DETAILS_COLLECTION("BankDetailsCollection"),
    CUSTOMER_DETAILS_COLLECTION("CustomerDataCollection"),
    FACIAL_FEATURES_COLLECTION("FacialFeaturesCollection"),
    FINGER_PRINT_PATTERN_COLLECTION("FingerPrintPatternCollection");

    public final String name;

    private Constants(String name) {
        this.name = name;
    }
}
