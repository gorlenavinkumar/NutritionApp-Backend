package com.nutritionix.UserProfile.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

public static final String TOPIC_NAME= "nutrition1";
	
	@Bean
	public NewTopic nutritionTopic() {
		return TopicBuilder.name(TOPIC_NAME).build();
	}
}
