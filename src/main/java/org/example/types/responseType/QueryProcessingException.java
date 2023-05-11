package org.example.types.responseType;

public class QueryProcessingException extends Exception{

    String message;

    String payload;

    public QueryProcessingException(String message,String object) {
        this.message = message;
        this.payload = object;
    }

    public QueryProcessingException(String message) {
        this.message = message;
    }

}
