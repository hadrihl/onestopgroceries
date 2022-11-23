package com.example.osg.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.example.osg.entity.Local;
import com.example.osg.entity.Store;
import com.example.osg.service.CustomUserDetails;
import com.example.osg.service.LocalService;
import com.example.osg.service.StoreService;

@Controller
public class StoreController {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private LocalService localService;

	// view homepage
	@GetMapping("/")
	public String getHomePage() {
		return "index";
	}
	
	@GetMapping("/about")
	public String getAboutPage() {
		return "about";
	}
	
	// view list of stores page
	@GetMapping("/stores")
	public String getStoresPage(Model model) {
		java.util.List<Store> stores = storeService.getAllStores();
		model.addAttribute("stores", stores);
		return "stores";
	}
	
	// view add new store page
	@GetMapping("/new-store")
	public String newStorePage() {
		return "new-store";
	}
	
	// add new store
	@PostMapping("/add-store")
	public String addNewStore(@ModelAttribute("store") Store store) {
		storeService.saveStore(store);
		
		return "redirect:stores";
	}
	
	// view edit/update store
	@GetMapping("/edit-store")
	public String viewEditStorePage(Model model, @RequestParam Integer store_id) {
		Store store = storeService.getStoreById(store_id);
		model.addAttribute("store", store);
		
		return "edit-store";
	}

	// save edit/update store
	@PostMapping("/update-store")
	public String saveStore(@ModelAttribute("store") Store store, @RequestParam("store_id") Integer store_id,
			@RequestParam("imgFile") MultipartFile file)
		throws IOException {
		store.setId(store_id);

		storeService.updateStoreInformation(store, file);
		return "redirect:stores";
	}
	
	// delete store
	@GetMapping("/delete-store")
	public String deleteStore(@RequestParam Integer store_id) {
		storeService.deleteStore(store_id);
		return "redirect:stores";
	}
	
	// view list of localities by store_id
	@GetMapping("/localities")
	public String getLocalities(@RequestParam Integer store_id, Model model) {
		Store store = storeService.getStoreById(store_id);
		model.addAttribute("store", store);
		
		List<Local> localities = localService.listLocalitiesByStoreId(store_id);
		model.addAttribute("localities", localities);
		
		return "localities";
	}
	
	// add local store by store_id
	@PostMapping("/add-local")
	public String addLocalStore(@ModelAttribute("local") Local local, @RequestParam Integer store_id) {
		
		Store store = storeService.getStoreById(store_id);
		local.setStore(store);
		localService.saveLocal(local);
		
		String path = "redirect:localities?store_id=" + store_id;
		return path;
	}
	
	// edit local store
	@GetMapping("/edit-local")
	public String viewLocalStorePage(Model model, @RequestParam Integer local_id) {
		Local local = localService.getLocalById(local_id);
		model.addAttribute("local", local);
		return "edit-local";
	}
	
	// update local by local id
	@PostMapping("/update-local")
	public String updateLocal(@ModelAttribute("local") Local local, @RequestParam Integer store_id) {
		local.setStore(storeService.getStoreById(store_id));
		localService.saveLocal(local);
		
		String path = "redirect:localities?store_id=" + store_id;
		return path;
	}
	
	// delete local by local id
	@GetMapping("/delete-local")
	public String deleteLocal(@RequestParam Integer local_id, @RequestParam Integer store_id) {
		localService.deleteLocal(local_id);
		String path = "redirect:localities?store_id=" + store_id;
		return path;
	}
	
	@GetMapping("/search")
	public String searchPage(Model model, String keyword) {
		List<Store> stores = storeService.search(keyword);
		model.addAttribute("stores", stores);
		return "search";
	}
	
}
