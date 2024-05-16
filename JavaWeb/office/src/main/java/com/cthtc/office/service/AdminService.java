package com.cthtc.office.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.entity.TaskDTO;
import com.cthtc.office.entity.TaskEntity;
import com.cthtc.office.model.Role;
import com.cthtc.office.model.TaskStatus;
import com.cthtc.office.repository.AccountRepository;
import com.cthtc.office.repository.TaskRepository;

@Service
public class AdminService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	public List<AccountEntity> getUsers() {
		return accountRepository.findAll().stream().filter(user -> user.getRole() == Role.GUEST)
				.collect(Collectors.toList());
	}
	
	public TaskDTO createTask(TaskDTO taskDTO) {
		System.out.print(taskDTO);
		Optional<AccountEntity> optionalUser = accountRepository.findById(taskDTO.getGuestID());
		
		if (optionalUser.isPresent()) {
			TaskEntity task = new TaskEntity(
				taskDTO.getTitle(), 
				taskDTO.getDescription(), 
				taskDTO.getPriority(), 
				taskDTO.getDueDate(), 
				TaskStatus.INPROGRESS,
				optionalUser.get()
			);
			return taskRepository.save(task).getTaskDTO();
		}
		return null;
	}
	
}
