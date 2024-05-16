package com.cthtc.office.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.repository.AccountRepository;

@Service
public class AccountDetailsService implements UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;
	  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AccountEntity> user = accountRepository.findUserByUsername(username);
		System.out.println(user);
		if(user.isPresent()){
            return new User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
        }else {
            throw new UsernameNotFoundException("User " + username + " does not exist...");
        }
	}

}
