package com.cthtc.office.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.entity.TaskEntity;
import com.cthtc.office.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GuestService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private JwtService jwtService;
	
	public List<TaskEntity> getTaskbyUserId() {
		AccountEntity user = jwtService.getLoggedInUser();
		if(user != null) {
			return taskRepository.findAllByUserId(user.getId())
			.stream()
			.sorted(Comparator.comparing(TaskEntity::getDueDate))
			.collect(Collectors.toList());
		}
		throw new EntityNotFoundException("User not found");
	}
}
