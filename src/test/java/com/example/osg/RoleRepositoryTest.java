package com.example.osg;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.osg.entity.Role;
import com.example.osg.repository.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateRole() {
		Role view_store = new Role("VIEW_STORE");
		Role add_store = new Role("ADD_STORE");
		
		roleRepository.saveAll(List.of(view_store, add_store));
		List<Role> list = roleRepository.findAll();
		
		assertThat(list.size()).isEqualTo(2);
	}
}
