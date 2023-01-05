package com.egorkin.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PojoJsonConverter {
    private static PojoJsonConverter ourInstance = new PojoJsonConverter();

    public static PojoJsonConverter getInstance() {
        return ourInstance;
    }

    private static ObjectMapper mapper;

    private PojoJsonConverter() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }


    public static String toJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T toPojo(String file, Class<T> valueType) throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(file);
        T result = (T) mapper.readValue(fileInputStream, valueType);
        fileInputStream.close();
        return result;
    }

    public static <T> T toPojoValue(String value, Class<T> valueType) throws IOException {
        return (T) mapper.readValue(value, valueType);
    }
}
