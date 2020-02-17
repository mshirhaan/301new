package com.eatza.notification.listener;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eatza.notification.model.Notification;
import com.eatza.notification.repository.NotificationRepository;

@Service
public class KafkaConsumer {

	@Autowired NotificationRepository notificationRepository;
	
    @KafkaListener(topics = "testtopic", groupId = "group_id")
    public void consume(String message) {
    	Notification notification = new Notification();
    	notification.setCreateDateTime(LocalDateTime.now());
    	notification.setType("CUSTOMER CREATION");
    	notification.setMessage(message);
    	notificationRepository.save(notification);
    }

}
