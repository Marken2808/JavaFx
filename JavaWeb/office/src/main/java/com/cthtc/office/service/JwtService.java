package com.cthtc.office.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {
	
	@Value("${SECRET_KEY}")
	private String secret;
	
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
                .expiration(new Date(System.currentTimeMillis() + 1000*60*24))
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
	
	

}
