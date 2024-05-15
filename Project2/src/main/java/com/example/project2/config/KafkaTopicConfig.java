package com.example.project2.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${application.kafka.eledevo-topic}")
    private String eledevoTopic;

    @Bean
    public NewTopic eledevoTopic() {
        return TopicBuilder
                .name(eledevoTopic)
                .build();
    }
}
