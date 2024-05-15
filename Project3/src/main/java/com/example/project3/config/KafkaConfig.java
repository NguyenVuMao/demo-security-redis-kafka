package com.example.project3.config;//package vn.eledevo.kafka_consumer.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.KafkaException;
//import org.springframework.kafka.retrytopic.ListenerContainerFactoryConfigurer;
//import org.springframework.kafka.retrytopic.RetryTopicConfigurationSupport;
//
//@Configuration
//public class KafkaConfig extends ListenerContainerFactoryConfigurer {
//
//    @Override
//    protected void configureCustomizers(RetryTopicConfigurationSupport.CustomizersConfigurer customizersConfigurer) {
//        customizersConfigurer.customizeErrorHandler(defaultErrorHandler ->
//                defaultErrorHandler.setLogLevel(KafkaException.Level.WARN));
//    }
//}
