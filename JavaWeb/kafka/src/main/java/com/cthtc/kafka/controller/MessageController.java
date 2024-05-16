package com.cthtc.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cthtc.kafka.producer.Producer;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {

	private Producer kafkaProducer;

	
	public MessageController(Producer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}
	
	
	@GetMapping("/publish")
	public ResponseEntity<String> publish(@RequestParam("message") String message){
		
		kafkaProducer.sendMessage(message);
		
		return ResponseEntity.ok("Message sent to topic");
		
	}

}
