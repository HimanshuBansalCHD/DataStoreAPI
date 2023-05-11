package org.example.Processor;

import com.google.gson.reflect.TypeToken;
import com.mongodb.client.model.Filters;
import lombok.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.constants.Constants;
import org.example.module.Executor;
import org.example.types.BankDetails;
import org.example.types.responseType.GetQueryResponse;
import org.example.types.responseType.QueryProcessingException;
import org.example.types.responseType.UpdateQueryResponse;
import org.example.util.ObjectSerializerAndDeserializer;
import org.example.util.SnappyUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class QueryProcessor {

    @Autowired
    Executor executor;

    @Autowired
    ObjectSerializerAndDeserializer objectSerializerAndDeserializer;

    public List<Document> getDataFromCollection(String collectionName) {
        return executor.performFindOperation(collectionName);
    }

    public boolean createTestData() {
        try {
            executor.performInsertOperation();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public GetQueryResponse getFacialFeaturesForAllCustomers() {
        List<Document> documentList = getDataFromCollection(Constants.FACIAL_FEATURES_COLLECTION.getName());
        return GetQueryResponse.builder()
                .isDataAvailable(documentList.isEmpty())
                .payloadDoc(documentList)
                .build();
    }

    public GetQueryResponse getAllCustomerDetails() {
        List<Document> documentList = getDataFromCollection(Constants.CUSTOMER_DETAILS_COLLECTION.getName());
        return GetQueryResponse.builder()
                .isDataAvailable(documentList.isEmpty())
                .payloadDoc(documentList)
                .build();
    }

    public GetQueryResponse getBankDetailsForAllCustomers() {
        List<Document> documentList = getDataFromCollection(Constants.BANK_DETAILS_COLLECTION.getName());
        return GetQueryResponse.builder()
                .isDataAvailable(documentList.isEmpty())
                .payloadDoc(documentList)
                .build();
    }

    public GetQueryResponse getFingerPrintPatternForAllUsers() throws IOException {
        List<Document> documentList = getDataFromCollection(Constants.FINGER_PRINT_PATTERN_COLLECTION.getName());
        return getQueryResponse(documentList);
    }

    private GetQueryResponse getQueryResponse(List<Document> documentList) throws IOException {
        byte[] compressedDocument = SnappyUtil.compressDocuments(documentList);
        return GetQueryResponse.builder()
                .isDataAvailable(compressedDocument.length > 0 )
                .payloadDoc(compressedDocument)
                .build();
    }

    public GetQueryResponse getCustomerDetailsByCustomerId(String customerId) {
        List<Document> documentList = getDataFromCollection(Constants.CUSTOMER_DETAILS_COLLECTION.getName());

        for (Document document : documentList) {
            if(customerId.equals(document.get(Constants.UNIQUE_ID.getName()).toString())) {
                return GetQueryResponse.builder()
                        .isDataAvailable(true)
                        .payloadDoc(document)
                        .build();
            }
        }

        return GetQueryResponse.builder()
                .isDataAvailable(false)
                .payloadDoc(null)
                .build();
    }

    public GetQueryResponse getBankDetailsByCustomerId(String customerId) {
        List<Document> documentList = getDataFromCollection(Constants.BANK_DETAILS_COLLECTION.getName());

        for (Document document : documentList) {
            if(customerId.equals(document.get(Constants.UNIQUE_ID.getName()).toString())) {
                return GetQueryResponse.builder()
                        .isDataAvailable(true)
                        .payloadDoc(document)
                        .build();
            }
        }

        return GetQueryResponse.builder()
                .isDataAvailable(false)
                .payloadDoc(null)
                .build();
    }

    public GetQueryResponse getFacialFeaturesByCustomerId(@NonNull String customerId) {
        List<Document> documentList = getDataFromCollection(Constants.FACIAL_FEATURES_COLLECTION.getName());

        for (Document document : documentList) {
            if(customerId.equals(document.get(Constants.UNIQUE_ID.getName()).toString())) {
                return GetQueryResponse.builder()
                        .isDataAvailable(true)
                        .payloadDoc(document)
                        .build();
            }
        }

        return GetQueryResponse.builder()
                .isDataAvailable(false)
                .payloadDoc(null)
                .build();
    }

    public GetQueryResponse getFingerPrintPatternByCustomerId(@NonNull String customerId) {
        List<Document> documentList = getDataFromCollection(Constants.FINGER_PRINT_PATTERN_COLLECTION.getName());

        for (Document document : documentList) {
            if(customerId.equals(document.get(Constants.UNIQUE_ID.getName()).toString())) {
                return GetQueryResponse.builder()
                        .isDataAvailable(true)
                        .payloadDoc(document)
                        .build();
            }
        }

        return GetQueryResponse.builder()
                .isDataAvailable(false)
                .payloadDoc(null)
                .build();
    }
    public UpdateQueryResponse updateBalanceForCustomerById(@NonNull String customerId, @NonNull int amountToBeCharged) {
        if (amountToBeCharged < 0) {
            return createErrorResponse(customerId, new QueryProcessingException("Amount cannot be less than Zero", customerId));
        }
        GetQueryResponse getQueryResponse = getBankDetailsByCustomerId(customerId);
        if (getQueryResponse.isDataAvailable()) {
            Document document = (Document) getQueryResponse.getPayloadDoc();
            try {
                return processPayment(customerId, document, amountToBeCharged)
                        .orElse(createErrorResponse(customerId,
                                new QueryProcessingException("Unknown Error Occurred", customerId)));
            } catch (QueryProcessingException queryProcessingException) {
                return createErrorResponse(customerId, queryProcessingException);
            }
        } else {
            return createErrorResponse(customerId, new QueryProcessingException("Data not found", customerId));
        }
    }

    private UpdateQueryResponse createErrorResponse(String customerId, QueryProcessingException exception) {
        return UpdateQueryResponse.builder()
                .IsSuccessful(false)
                .Id(customerId)
                .payload(exception)
                .build();
    }

    private Optional<UpdateQueryResponse> processPayment(String customerId, Document document, int amountToBeCharged) throws
            QueryProcessingException {

        BankDetails bankDetails = (BankDetails) objectSerializerAndDeserializer.deSerializeObject((String) document.get(Constants.BANK_DETAILS.getName()),
                (new TypeToken<BankDetails>() {
                }).getType());
        int existingBalance = bankDetails.getAccountBalance();
        if (existingBalance < amountToBeCharged) {
            throw new QueryProcessingException("Your Balance is low.");
        }

        int newBalance = existingBalance - amountToBeCharged;
        bankDetails.setAccountBalance(newBalance);

        if (!executor.performUpdateOperation(customerId, bankDetails, document, Constants.BANK_DETAILS_COLLECTION.getName())) {
            throw new QueryProcessingException("Operation failed to update the balance");
        }

        GetQueryResponse getQueryResponse = getBankDetailsByCustomerId(customerId);

        return Optional.of(UpdateQueryResponse.builder()
                .Id(customerId)
                .IsSuccessful(true)
                .oldValue(String.valueOf(existingBalance))
                .newValue(String.valueOf(newBalance))
                .payload(getQueryResponse.getPayloadDoc())
                .build());
    }

    public void dropDatabaseContent() {
        executor.performDropOperation();
    }

}
