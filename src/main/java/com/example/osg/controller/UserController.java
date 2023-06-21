package com.example.osg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.osg.entity.Role;
import com.example.osg.entity.User;
import com.example.osg.repository.RoleRepository;
import com.example.osg.repository.UserRepository;
import com.example.osg.service.FileStorageService;

@Controller
public class UserController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileStorageService storageService;
	
	@GetMapping("/signin")
	public String getSignInPage() {
		return "signin";
	}
	
	@GetMapping("/signup")
	public String getSignUpPage() {
		return "signup";
	}
	
	@PostMapping("/process_signup")
	public String register(Model model, @ModelAttribute("user") User user) {
		
		User existUser = userRepository.getUserByEmail(user.getEmail());
		
		if(existUser == null) {
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
			
		} else {
			String str = "User (" + user.getEmail() + ") exists! Use different email.";
			model.addAttribute("error_string", str);
			System.err.println(str);
			
			return "signup";
		}
	}
	
	
	@GetMapping("/image/new")
	public String newProfileImage() {
		return "upload";
	}
	
	@PostMapping("/image-upload")
	public String uploadProfileImage(@RequestParam("file") MultipartFile file, Model model) {
		String message = "";
		
		try {
			storageService.save(file);
			message = "Upload image successfully: " + file.getOriginalFilename();
			model.addAttribute("message", message);
		} catch (Exception e) {
			message = "Could not upload image:" + file.getOriginalFilename();
			model.addAttribute("message", message);
		}
		
		return "upload";
	}
}
