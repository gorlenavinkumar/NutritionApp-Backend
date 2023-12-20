package com.nutritionix.Authentication.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String message) {
		log.info(message);
		kafkaTemplate.send(KafkaConfig.TOPIC_NAME, message);
	}
}
