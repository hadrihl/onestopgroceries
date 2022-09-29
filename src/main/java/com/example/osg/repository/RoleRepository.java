package com.example.osg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.osg.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	@Query("SELECT r FROM Role r WHERE r.id = :role_id")
	public Role getRoleById(Integer role_id);
	
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	public Role getRoleByName(String name);
}
