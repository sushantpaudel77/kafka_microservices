package com.mykafka.microservices.user_service.service;

import com.mykafka.microservices.user_service.dto.CreateUserDto;
import com.mykafka.microservices.user_service.entity.User;
import com.mykafka.microservices.user_service.event.UserCreatedEvent;
import com.mykafka.microservices.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${kafka.topic.user-created-topic}")
    private String kafkaUserCreatedTopic;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public UserService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createUser(CreateUserDto userDto) {

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = modelMapper.map(savedUser, UserCreatedEvent.class);
        kafkaTemplate.send(kafkaUserCreatedTopic, userCreatedEvent.getId(), userCreatedEvent);
    }
}
