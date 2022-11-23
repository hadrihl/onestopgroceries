package com.example.osg.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageService {
	
	public final String storageDirectoryPath = 
			"C:\\Users\\hadrihl\\Desktop\\codemonkey__\\onestopgroceries\\src\\main\\resources\\static\\assets\\img";
	
	public String uploadProfileImage(MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path storageDirectory = Paths.get(storageDirectoryPath);
		
		if(!Files.exists(storageDirectory)) { // if file not exists, 
			try {
				Files.createDirectories(storageDirectory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Path destination = Paths.get(storageDirectory.toString() + "\\" + fileName);
		
		System.err.println("store profile image: " + destination);
		
		//copying all bytes from an input stream to a file
		try {
			Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileName;
	}

}
