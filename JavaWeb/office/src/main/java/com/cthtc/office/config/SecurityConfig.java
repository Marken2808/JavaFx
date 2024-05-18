package com.cthtc.office.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cthtc.office.filter.JwtFilter;
import com.cthtc.office.model.Role;
import com.cthtc.office.service.AccountDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true)
public class SecurityConfig{
	
	@Autowired
	private AccountDetailsService accountDetailsService;
	
	@Autowired
    private JwtFilter jwtFilter;
	
    public SecurityConfig(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(accountDetailsService);
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
	       .cors()
	       .and()
	       .csrf().disable()
		   .authorizeHttpRequests((auth) -> auth
		       .requestMatchers(HttpMethod.GET,"/info").permitAll()
		       .requestMatchers(HttpMethod.POST,"/api/authenticate/**").permitAll()
		       .requestMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.name())
		       .requestMatchers("/api/guest/**").hasAnyAuthority(Role.GUEST.name())
		       .requestMatchers(HttpMethod.GET,"/hello").hasAnyAuthority(Role.ADMIN.name())
		       .anyRequest()
		       .authenticated()
		   )		   
   			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}


}
