package com.mykafka.microservices.user_service.controller;

import com.mykafka.microservices.user_service.dto.CreateUserDto;
import com.mykafka.microservices.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final String kafkaUserTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;

    public UserController(
            @Value("${kafka.topic.user-random-topic}") String kafkaUserTopic,
            KafkaTemplate<String, String> kafkaTemplate,
            UserService userService
    ) {
        this.kafkaUserTopic = kafkaUserTopic;
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.ok("User is created");
    }


    @PostMapping("/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send(kafkaUserTopic, ""+i%3,message + i);
        }

        return ResponseEntity.ok("Message queued");
    }
}
