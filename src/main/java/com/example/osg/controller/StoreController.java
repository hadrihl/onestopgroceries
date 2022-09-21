package com.example.osg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.osg.entity.Store;
import com.example.osg.service.StoreService;

@Controller
public class StoreController {
	
	@Autowired
	private StoreService service;

	@GetMapping("/")
	public String getHomePage() {
		return "index";
	}
	
	@GetMapping("/stores")
	public String getStoresPage(Model model) {
		java.util.List<Store> stores = service.getAllStores();
		model.addAttribute("stores", stores);
		return "stores";
	}
	
	@GetMapping("/new-store")
	public String newStorePage() {
		return "new-store";
	}
	
	@PostMapping("/add-store")
	public String addNewStore(@ModelAttribute("store") Store store) {
		service.saveStore(store);
		
		return "redirect:stores";
	}
}
