package com.example.osg.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.osg.entity.Local;
import com.example.osg.entity.Store;
import com.example.osg.repository.LocalRepository;
import com.example.osg.repository.StoreRepository;

@Service
@Transactional
public class LocalService {
	
	@Autowired
	private LocalRepository repo;
	
	@Autowired
	private StoreRepository storeRepo;
	
	// create or update
	public void saveLocal(Local local) {
		repo.save(local);
	}
	
	// list all localities 
	public List<Local> listAll() {
		return (List<Local>) repo.findAll();
	}
	
	// get local by local id
	public Local getLocalById(Integer local_id) {
		Local local = repo.findById(local_id).get();
		return local;
	}
	
	// list localities by store id
	public List<Local> listLocalitiesByStoreId(Integer store_id) {
		Store store = storeRepo.findById(store_id).get();
		return (List<Local>) repo.findByStoreId(store);
	}
	
	// delete local by local id
	public void deleteLocal(Integer local_id) {
		repo.deleteById(local_id);
	}

}
