package com.example.osg.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.osg.entity.Store;
import com.example.osg.repository.StoreRepository;

@Service
@Transactional
public class StoreService {

	@Autowired
	private StoreRepository repo;
	
	@Autowired
	private ImageService imageService;
	
	public List<Store> getAllStores() {
		return repo.findAll();
	}
	
	public void saveStore(Store store) {
		store.setUpdatedOn(LocalDateTime.now());
		repo.save(store);
	}
	
	public void updateStoreInformation(Store store, MultipartFile file) throws IOException {
		Store existedStore = repo.getReferenceById(store.getId());
		
		existedStore.setName(store.getName());
		existedStore.setInfo(store.getInfo());
		existedStore.setUpdatedOn(LocalDateTime.now());
		
		if(file.isEmpty()) {
			System.err.println("no need to upload store profile image.");
		} else {
			String fileName = imageService.uploadProfileImage(file);
			existedStore.setImg(fileName);
			System.err.println("upload store profile image completed.");
		}
		
		repo.save(existedStore);
	}
	
	public Store getStoreById(Integer store_id) {
		return repo.findById(store_id).get();
	}
	
	public String getStoreName(Integer store_id) {
		Store store = repo.findById(store_id).get();
		return store.getName();
	}
	
	public void deleteStore(Integer store_id) {
		repo.deleteById(store_id);
	}
	
	public List<Store> search(String keyword) {
		return repo.search(keyword);
	}
}
