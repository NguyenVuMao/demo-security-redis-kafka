package com.example.project2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @KafkaListener(topics = "eledevo", groupId = "myGroup")
    public void consumeMsg(String msg) {
        log.info(format("Consuming the message from eledevo Topic:: %s", msg));
    }

}
