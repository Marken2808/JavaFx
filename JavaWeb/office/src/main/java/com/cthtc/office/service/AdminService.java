package com.cthtc.office.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
//import com.cthtc.office.entity.TaskDTO;
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
	
	public TaskEntity createTask(TaskEntity taskEntity) {
		System.out.print(taskEntity);
		Optional<AccountEntity> optionalUser = accountRepository.findById(taskEntity.getUser().getId());
		
		if (optionalUser.isPresent()) {
			TaskEntity task = new TaskEntity(
				taskEntity.getTitle(), 
				taskEntity.getDescription(), 
				taskEntity.getPriority(), 
				taskEntity.getDueDate(), 
				TaskStatus.INPROGRESS,
				optionalUser.get()
			);
//			return taskRepository.save(task).getTaskDTO();
			return taskRepository.save(task);
		}
		return null;
	}
	
	public List<TaskEntity> getAllTasks() {
		return taskRepository.findAll().stream()
				.sorted(Comparator.comparing(TaskEntity::getDueDate).reversed())
				.collect(Collectors.toList());
	}

	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	
	
}
