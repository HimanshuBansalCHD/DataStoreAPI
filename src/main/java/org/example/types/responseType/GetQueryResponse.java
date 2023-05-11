package org.example.types.responseType;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@Getter
public class GetQueryResponse {
    @Autowired
    private Gson gson;

    boolean isDataAvailable;

    Object payloadDoc;

    @Override
    public String toString() {
        return gson.toJson(payloadDoc);
    }
}
