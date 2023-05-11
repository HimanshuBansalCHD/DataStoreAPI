package org.example.module;

import com.google.gson.Gson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.example.Processor.QueryProcessor;
import org.example.dao.MongoDBDao;
import org.example.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ServiceModule {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Printer printer() {
        return new Printer();
    }

    @Bean
    public MongoUtil mongoUtil() {
        return new MongoUtil();
    }

    @Bean
    public PrepareDocument prepareDocument() {
        return new PrepareDocument();
    }

    @Bean
    public ObjectSerializerAndDeserializer objectSerializerAndDeserializer() {
        return new ObjectSerializerAndDeserializer();
    }

    @Bean
    public MongoDBDao mongoDBDao() {
        return new MongoDBDao();
    }

    @Bean
    public Executor executorBean() {
        return new Executor();
    }

    @Bean
    public RandomArrayGenerator randomArrayGenerator() {
        return new RandomArrayGenerator();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public QueryProcessor queryProcessor() {
        return new QueryProcessor();
    }

}
