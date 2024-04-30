package com.example.testtwitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter {
    public static String convertToJson(String errorMessage) {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.disable(SerializationFeature.WRITE_TYPING);
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("Error", errorMessage);
        try {
            return mapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ResponseEntity<String> convertToJsonResponse(HttpStatus status, String message) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Error", message);
        try {
            String jsonBody = mapper.writeValueAsString(responseBody);
            return ResponseEntity.status(status).body(jsonBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting to JSON");
        }
    }

}
