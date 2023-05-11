package org.example.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;

public class ObjectSerializerAndDeserializer<T> {

    @Autowired
    Gson gson;
    public String serializeObject(T object) {
            return gson.toJson(object);
    }

    public Object deSerializeObject(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }

}
