package com.example.osg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.osg.controller.StoreController;
import com.example.osg.repository.LocalRepository;
import com.example.osg.repository.StoreRepository;
import com.example.osg.repository.UserRepository;
import com.example.osg.security.SecurityConfig;
import com.example.osg.service.ImageService;
import com.example.osg.service.LocalService;
import com.example.osg.service.StoreService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StoreController.class)
@ContextConfiguration(classes = SecurityConfig.class)
public class AuthorizationTest {
	
	@Autowired private MockMvc mvc;
	@MockBean private StoreRepository storeRepository;
	@MockBean private LocalRepository localRepository;
	@MockBean private UserRepository userRepository;
	@SpyBean private StoreService storeService;
	@SpyBean private ImageService imageService;
	@SpyBean private LocalService localService;
	@InjectMocks private StoreController storeController;
	
	@Test
	@WithMockUser(authorities = "ADD_STORE")
	public void request_protected_url_with_user_new_store() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/new-store"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(authorities = "ADD_STORE")
	public void request_protected_url_with_user_delete_local() throws Exception {
		Integer store_id = 1;
		mvc.perform(MockMvcRequestBuilders.post("/delete-store", store_id))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(authorities = "VIEW_STORE")
	public void request_protected_url_with_user_view_store() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/stores"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
