package com.cthtc.office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cthtc.office.entity.TaskEntity;
import com.cthtc.office.service.GuestService;

@RestController
@RequestMapping("/api/guest")
@CrossOrigin("*")
public class GuestController {
	
	@Autowired
	private GuestService guestService;
	
	@GetMapping("/list/tasks")
	public ResponseEntity<List<TaskEntity>> getTaskByUserId(){
		List<TaskEntity> getAllTask = guestService.getTaskbyUserId();
		if(getAllTask == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(getAllTask);
	}
}
