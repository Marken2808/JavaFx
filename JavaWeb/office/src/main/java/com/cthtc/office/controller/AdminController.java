package com.cthtc.office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cthtc.office.entity.AccountEntity;
//import com.cthtc.office.entity.TaskDTO;
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
		List<AccountEntity> getAllGuestUser = adminService.getGuestUsers();
		if(getAllGuestUser == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(getAllGuestUser);
	}
	
	@PostMapping("/create/task")
	public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity taskEntity){
		TaskEntity createTask = adminService.createTask(taskEntity);
		if(createTask ==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(createTask);
	}
	
	@GetMapping("/list/tasks")
	public ResponseEntity<?> getAllTasks(){
		List<TaskEntity> getAllTask = adminService.getAllTasks();
		if(getAllTask == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(getAllTask);
	}
	
	@DeleteMapping("/delete/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		adminService.deleteTask(id);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/get/task")
	public ResponseEntity<?> getTaskById(@RequestParam Long id){
		TaskEntity getTask = adminService.getTaskById(id);
		if(getTask == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(getTask);
	}
	
	@GetMapping("/search/tasks")
	public ResponseEntity<List<TaskEntity>> searchTaskByTitle(@RequestParam String title){
		List<TaskEntity> searchTask = adminService.searchTaskByTitle(title);
		if(searchTask == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(searchTask);
	}
	
	@PutMapping("/update/task")
	public ResponseEntity<?> updateTaskById(@RequestParam Long id, @RequestBody TaskEntity taskEntity){
		TaskEntity updateTask = adminService.updateTaskById(id, taskEntity);
		if(updateTask == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(updateTask);
	}
}
