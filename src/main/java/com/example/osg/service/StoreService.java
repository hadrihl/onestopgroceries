package com.example.osg.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.osg.entity.Store;
import com.example.osg.repository.StoreRepository;

@Service
@Transactional
public class StoreService {

	@Autowired
	private StoreRepository repo;
	
	public List<Store> getAllStores() {
		return repo.findAll();
	}
	
	public void saveStore(Store store) {
		store.setUpdatedOn(LocalDateTime.now());
		repo.save(store);
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
