package com.cthtc.office.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.model.AuthenticationRequest;
import com.cthtc.office.model.Role;
import com.cthtc.office.repository.AccountRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthenticationService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		Optional<AccountEntity> optionalUser = accountRepository.findUserByRole(Role.ADMIN);
		if(optionalUser.isEmpty()) {
			AccountEntity user = new AccountEntity("admin", new BCryptPasswordEncoder().encode("admin"), Role.ADMIN);
			accountRepository.save(user);
			System.out.println("Create Admin account successfully!");
		} else {
			System.out.println("Admin account already exist!");
		}
	}
	
	public void signupUser(AuthenticationRequest signupRequest) {
		AccountEntity user = new AccountEntity();
		user.setUsername(signupRequest.getUsername());
		user.setPassword( new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(Role.GUEST);
		accountRepository.save(user);
	}
	
	public boolean hasUserAlreadyExist(String username) {
		return accountRepository.findById(username).isPresent();
	}
	
	
}
