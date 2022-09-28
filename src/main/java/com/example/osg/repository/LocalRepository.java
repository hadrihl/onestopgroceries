package com.example.osg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.osg.entity.Local;
import com.example.osg.entity.Store;

public interface LocalRepository extends JpaRepository<Local, Integer> {
	
	@Query("SELECT l FROM Local l WHERE l.store in :store_id")
	public List<Local> findByStoreId(@Param("store_id") Store store);
}
