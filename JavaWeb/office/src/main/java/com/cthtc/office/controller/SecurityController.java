package com.cthtc.office.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cthtc.office.model.AuthenticationRequest;
import com.cthtc.office.model.AuthenticationResponse;
import com.cthtc.office.service.AccountDetailsService;
import com.cthtc.office.service.AuthenticationService;
import com.cthtc.office.service.JwtService;

@RestController
public class SecurityController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountDetailsService accountDetailsService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
    private JwtService jwtService;

	@GetMapping("info")
	public String info() {return "The application is up ...";}
	
	@GetMapping("hello")
	public String hello() {return "The application said HELLO ...";}
	
	@PostMapping("/api/authenticate/signin")
	public AuthenticationResponse signinUser(@RequestBody AuthenticationRequest authRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(),	authRequest.getPassword())
			);
			
		} catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or password...", e);
        }
		
		UserDetails userDetails = accountDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
	}
	
	@PostMapping("/api/authenticate/signup")
	public ResponseEntity signupUser(@RequestBody AuthenticationRequest signupRequest) {
		if(authenticationService.hasUserAlreadyExist(signupRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist");
			
		}
		
		authenticationService.signupUser(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
	}
	
}
