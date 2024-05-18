package com.cthtc.office.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.AccountEntity;
import com.cthtc.office.repository.AccountRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;


@Component
public class JwtService {
	
	@Value("${SECRET_KEY}")
	private String secret;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public String generateToken(UserDetails userDetails) {
		return getJWT(userDetails);
	}
	
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
			
	public String getJWT(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        
        String jwt = Jwts.builder()
        		.claims(claims)
        		.subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(getSigningKey()).compact();
        return jwt;
    }

    public Boolean isValidToken(String token, UserDetails userDetails){
        final String userName = extractClaim(token, Claims::getSubject);
        return (userName.equals(userDetails.getUsername()) && !extractClaim(token, Claims::getExpiration).before(new Date()));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = Jwts.parser()
        		.verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        claimsResolver.apply(claims);
        return claimsResolver.apply(claims);
    }
	
	public AccountEntity getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		System.out.println(authentication);
		if(authentication != null && authentication.isAuthenticated()) {
			
//			AccountEntity details = (AccountEntity) authentication.getPrincipal();
//			System.out.println(">>>" + details.getId());
			
			String username = (String) authentication.getName();
			Optional<AccountEntity> optionalUser  = accountRepository.findUserByUsername(username);
			return optionalUser.orElse(null);
		}
		return null;
	}

}
