package com.example.osg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.osg.entity.Role;
import com.example.osg.entity.User;
import com.example.osg.repository.RoleRepository;
import com.example.osg.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/signin")
	public String getSignInPage() {
		return "signin";
	}
	
	@GetMapping("/signup")
	public String getSignUpPage() {
		return "signup";
	}
	
	@PostMapping("/process_signup")
	public String register(@ModelAttribute("user") User user) {
		
		User existUser = userRepository.getUserByEmail(user.getEmail());
		
		if(existUser.getEmail() == user.getEmail()) {
			System.err.println("User exists! Use different email.");
			return "redirect:signup";
		} else {
		
		// set default role: VIEW_STORE
		Role defaultRole = roleRepository.getRoleByName("VIEW_STORE");
		user.setRoles(defaultRole);
		
		// hash the password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		// save user
		userRepository.save(user);
		
		return "redirect:signin";
		}
	}

}
