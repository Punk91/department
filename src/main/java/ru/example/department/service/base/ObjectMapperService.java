package ru.example.department.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ObjectMapperService {

    @Autowired
    private ObjectMapper customObjectMapper;

    public <T> T readValue(String string, Class<T> tClass) {
        try {
            return customObjectMapper.readValue(string, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(InputStream inputStream, Class<T> tClass) {
        try {
            return customObjectMapper.readValue(inputStream, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(byte[] bytes, Class<T> tClass) {
        try {
            return customObjectMapper.readValue(new ByteArrayInputStream(bytes), tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readValue(String string, TypeReference<T> typeReference) {
        try {
            return customObjectMapper.readValue(string, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String writeValueAsString(T t) {
        try {
            return customObjectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> byte[] writeValueAsBytes(T t) {
        try {
            return customObjectMapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode readTree(String string) {
        try {
            return customObjectMapper.readTree(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
