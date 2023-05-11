package org.example.Controller;

import com.google.gson.Gson;
import org.example.Processor.QueryProcessor;
import org.example.types.responseType.GetQueryResponse;
import org.example.types.responseType.UpdateQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("")
public class DatabaseController {

    @Autowired
    Gson gson;

    @Autowired
    QueryProcessor queryProcessor;

    @GetMapping("/createTestData/")
    public String createTestData() {
        boolean val = queryProcessor.createTestData();
        return gson.toJson(val);
    }

    @DeleteMapping("/dropDatabaseContent")
    public void dropDatabaseContent() {
        queryProcessor.dropDatabaseContent();
    }

    @GetMapping("/getFacialFeaturesForAllCustomers")
    public String getFacialFeaturesForAllCustomers() {
        GetQueryResponse getQueryResponse = queryProcessor.getFacialFeaturesForAllCustomers();
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getFacialFeaturesByCustomerId/{customerId}")
    public String getFacialFeatures(@PathVariable String customerId) {
        GetQueryResponse getQueryResponse = queryProcessor.getFacialFeaturesByCustomerId(customerId);
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getFingerPrintPatternForAllCustomers")
    public String getFingerPrintPatternForAllCustomers() throws IOException {
        GetQueryResponse getQueryResponse = queryProcessor.getFingerPrintPatternForAllUsers();
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getFingerPrintPatternByCustomerId/{customerId}")
    public String getFingerPrintPatternByCustomerId(@PathVariable String customerId) {
        GetQueryResponse getQueryResponse = queryProcessor.getFingerPrintPatternByCustomerId(customerId);
        return gson.toJson(getQueryResponse);
    }

    @PutMapping("/updateBalanceByCustomerId/{customerId}/{amountToBeCharged}")
    public String updateBalanceByCustomerId(@PathVariable String customerId, @PathVariable int amountToBeCharged) {
        UpdateQueryResponse updateQueryResponse = queryProcessor.updateBalanceForCustomerById(customerId, amountToBeCharged);
        return gson.toJson(updateQueryResponse);
    }

    @GetMapping("/getAllCustomerDetails")
    public String getAllCustomerDetails() {
        GetQueryResponse getQueryResponse = queryProcessor.getAllCustomerDetails();
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getCustomerDetailsByCustomerId/{customerId}")
    public String getCustomerDetailsByCustomerId(@PathVariable String customerId) {
        GetQueryResponse getQueryResponse = queryProcessor.getCustomerDetailsByCustomerId(customerId);
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getBankDetailsForAllCustomer")
    public String getBankDetailsForAllCustomer() {
        GetQueryResponse getQueryResponse = queryProcessor.getBankDetailsForAllCustomers();
        return gson.toJson(getQueryResponse);
    }

    @GetMapping("/getBankDetailsByCustomerId/{customerId}")
    public String getBankDetailsByCustomerId(@PathVariable String customerId) {
        GetQueryResponse getQueryResponse = queryProcessor.getBankDetailsByCustomerId(customerId);
        return gson.toJson(getQueryResponse);
    }

}
