package com.nutritionix.UserProfile.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	@KafkaListener(topics= KafkaConfig.TOPIC_NAME, groupId="nutritionapp")
	  public void consume(String message) {
	    log.info(String.format("Message received -> %s", message));
	 
	  }
}
