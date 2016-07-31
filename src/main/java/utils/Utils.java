package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final JsonFactory jsonFactory = objectMapper.getJsonFactory();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public static String asJson(Object object) throws IllegalArgumentException, IOException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> klass) throws IOException {
        return objectMapper.readValue(json,klass);
    }

    public static <T> T fromJson(String json, TypeReference<T> reference) throws IOException {
        return objectMapper.readValue(json,reference);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
