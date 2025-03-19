package com.ervingorospe.grab_token.service.kafka;

import com.ervingorospe.grab_token.service.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;

    @Autowired
    public KafkaConsumerService(ObjectMapper objectMapper, TokenService tokenService) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
    }

    @KafkaListener(topics = "email-verification", groupId = "grab-group")
    public void processEmailVerification(String message) {
        try {
            Map<String, String> data = objectMapper.readValue(message, Map.class);
            UUID userId = UUID.fromString(data.get("userId"));
            String type = data.get("type");

            System.out.println("Received message: " + message);
            tokenService.saveToken(userId, type);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
