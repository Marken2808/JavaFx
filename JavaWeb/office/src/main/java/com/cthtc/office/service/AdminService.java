package com.cthtc.office.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.entity.TaskEntity;
import com.cthtc.office.model.Role;
import com.cthtc.office.repository.AccountRepository;

@Service
public class AdminService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<AccountEntity> getUsers() {
		return accountRepository.findAll().stream().filter(user -> user.getRole() == Role.GUEST)
				.collect(Collectors.toList());
	}
	
//	public TaskEntity createTask(TaskEntity taskEntity) {
//		
//		Optional<AccountEntity> optionalUser = accountRepository.findById(taskEntity.getUser().getUsername());
//		return null;
//	}
	
}
