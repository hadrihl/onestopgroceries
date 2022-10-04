package com.example.osg.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.osg.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService());
		auth.setPasswordEncoder(passwordEncoder());
		
		return auth;
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.GET, "/about").permitAll()
				.antMatchers(HttpMethod.GET, "/signup").permitAll()
				.antMatchers(HttpMethod.POST, "/process_signup").permitAll()
				.antMatchers(HttpMethod.GET, "/signin").permitAll()
				.antMatchers(HttpMethod.GET, "/assets/**").permitAll()
				.antMatchers(HttpMethod.GET, "/stores").hasAnyAuthority("VIEW_STORE", "ADD_STORE")
				.antMatchers(HttpMethod.GET, "/localities").hasAnyAuthority("VIEW_STORE", "ADD_STORE")
				.antMatchers(HttpMethod.GET, "/new-store").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/add-store").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.GET, "/edit-store").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/update-store").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/delete-store").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/add-local").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.GET, "/edit-local").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/update-local").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.POST, "/delete-local").hasAuthority("ADD_STORE")
				.antMatchers(HttpMethod.GET, "/search").hasAnyAuthority("VIEW_STORE", "ADD_STORE")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/signin")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/stores")
				.permitAll()
				.and()
			.logout()
				.invalidateHttpSession(true)
				.permitAll();
		
		return http.build();
	}
}
