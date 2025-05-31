package com.mykafka.microservices.user_service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${kafka.topic.user-random-topic}")
    private String kafkaRandomUserTopic;


    @Value("${kafka.topic.user-created-topic}")
    private String kafkaUserCreatedTopic;

    @Bean
    public NewTopic userRandomTopic() {
        return new NewTopic("kafkaRandomUserTopic", 3, (short) 1);
    }

    @Bean
    public NewTopic userCreatedTopic() {
        return new NewTopic(kafkaUserCreatedTopic, 3, (short) 1);
    }
}
