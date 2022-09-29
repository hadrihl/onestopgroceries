package com.example.osg;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.osg.entity.Role;
import com.example.osg.entity.User;
import com.example.osg.repository.RoleRepository;
import com.example.osg.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUser() {
		User admin = new User();
		admin.setEmail("admin@example.com");
		admin.setUsername("admin");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");
		admin.setPassword(encodedPassword);
		
		User savedUser = userRepository.save(admin);
		User existUser = entityManager.find(User.class, savedUser.getId());
		
		assertThat(existUser.getEmail()).isEqualTo(savedUser.getEmail());
	}
	
	@Test
	public void testAddRoleToNewUser() {
		User user = new User();
		user.setEmail("hadrihl@example.com");
		user.setUsername("hadrihl");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("password");
		user.setPassword(encodedPassword);
		
		Role role = roleRepository.getRoleById(1);
		user.setRoles(role);
		
		User savedUser = userRepository.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
	
	@Test
	public void testAddRoleToExistingUser() {
		User admin = userRepository.getUserByEmail("admin@example.com");
		
		Role view = roleRepository.getRoleById(1);
		Role add = roleRepository.getRoleById(2);
		
		admin.setRoles(view);
		admin.setRoles(add);
		
		User savedUser = userRepository.save(admin);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(2);
	}
}
