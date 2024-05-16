package com.cthtc.office.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cthtc.office.entity.TaskDTO;
import com.cthtc.office.entity.TaskEntity;
import com.cthtc.office.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(){
		return ResponseEntity.ok(adminService.getUsers());
	}
	
	@PostMapping("/create/task")
	public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
		TaskDTO createTask = adminService.createTask(taskDTO);
		if(createTask ==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(createTask);
	}
}
