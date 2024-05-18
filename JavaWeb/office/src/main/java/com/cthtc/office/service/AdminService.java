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
	
	//USER
	
	public List<AccountEntity> getGuestUsers() {
		return accountRepository.findAll().stream().filter(user -> user.getRole() == Role.GUEST)
				.collect(Collectors.toList());
	}
	
	// TASK
	
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
	
	public List<TaskEntity> searchTaskByTitle(String title) {
		return taskRepository.findAllByTitleContaining(title)
				.stream()
				.sorted(Comparator.comparing(TaskEntity::getDueDate).reversed())
				.collect(Collectors.toList());
	}
	
	public List<TaskEntity> getAllTasks() {
		return taskRepository.findAll().stream()
				.sorted(Comparator.comparing(TaskEntity::getDueDate).reversed())
				.collect(Collectors.toList());
	}

	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	
	public TaskEntity getTaskById(Long id) {
		Optional<TaskEntity> optionalTask = taskRepository.findById(id);
		return optionalTask.orElse(null);
	}
	
	public TaskEntity updateTaskById(Long id, TaskEntity newTask) {
		Optional<TaskEntity> optionalTask = taskRepository.findById(id);
		Optional<AccountEntity> optionalUser = accountRepository.findById(newTask.getUser().getId());
		if(optionalTask.isPresent() && optionalUser.isPresent()) {
			TaskEntity existTask = optionalTask.get();
			existTask.setTitle(newTask.getTitle());
			existTask.setDescription(newTask.getDescription());
			existTask.setPriority(newTask.getPriority());
			existTask.setDueDate(newTask.getDueDate());
			existTask.setTaskStatus(mapToTaskStatus(newTask.getTaskStatus().toString()));
			existTask.setUser(optionalUser.get()); // not update user
			
			return taskRepository.save(existTask);
		}
		return null;
	}
	
	private TaskStatus mapToTaskStatus(String status) {
		return switch(status) {
			case "PENDING" -> TaskStatus.PENDING;
			case "INPROGRESS" -> TaskStatus.INPROGRESS;
			case "COMPLETED" -> TaskStatus.COMPLETED;
			default -> TaskStatus.CANCELLED;
		};
	}
}
