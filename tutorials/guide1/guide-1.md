# title

### Config Spring Boot Properties for MySQL

```conf
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_login
spring.datasource.username=root
spring.datasource.password=mynewpass
spring.datasource.driver=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLInnoDBDialect

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true

spring.mvc.view.prefix: /WEB-INF/jsp/
spring.mvc.view.suffix: .jsp
```

### Create an Entity Store

```java
package com.example.osg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Store")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String info;
	private String phone;
	private String address;
	private String img;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
}
```

### Create a Repository

```java
package com.example.osg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.osg.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
	
	@Query("SELECT s FROM Store s WHERE s.name = ?1")
	public Store findStoreByName(String store_name);
	
	@Query(value = "SELECT s FROM Store s WHERE s.name LIKE '%' || :keyword || '%'"
			+ " OR s.info LIKE '%' || :keyword || '%'"
			+ " OR s.address LIKE '%' || :keyword || '%'")
	public List<Store> search(@Param("keyword") String keyword);

}
```

### Create a Service Class

```java
package com.example.osg.service;

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
		repo.save(store);
	}
	
	public Store getStoreById(Integer store_id) {
		return repo.findById(store_id).get();
	}
	
	public void deleteStore(Integer store_id) {
		repo.deleteById(store_id);
	}
	
	public List<Store> search(String keyword) {
		return repo.search(keyword);
	}
}
```

### Create a Controller class

```java
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
```

### Create homepage, store page, and add new store page

First of all, create a directories (`webapp`, `WEB-INF`, `jsp`) in main directory by following this project directory structure.

```
src > main > webapp > WEB-INF > jsp
```

Then develop the homepage, stores page, and add new store page. 

```html
<!-- file: index.jsp -->
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>One Stop Groceries</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
  </head>
  <body>
  	<nav class="navbar navbar-expand-sm navbar-light bg-light border-bottom fixed-top">
  		<div class="container">
  			<span class="navbar-brand mt-1 h1">One<span class="text-warning">Stop</span>Groceries
  				<i class="fa-solid fa-truck"></i>
  			</span>

            <button type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" class="navbar-toggler" 
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle Navigation"><span class="navbar-toggler-icon"></span></button>
    
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a href="#" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a href="#" class="nav-link">About</a>
                    </li>
                    <li class="nav-item active">
                        <a href="/stores" class="nav-link">LocateMe</a>
                    </li>
                </ul>
  		</div>
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 mx-auto">
                    <img class="img-fluid mb-3" src="assets/img/store6_pexels-tim-mossholder-942320.jpg" alt="welcome">
                    <h2 class="fw-light mb-2">Welcome to One Stop Groceries!</h2>
                    <p class="lead text-muted">We are a digital store platform where you can choose 
                    from our wide variety of fresh and high-quality essentials for your everyday 
                    kicthen and household needs. Make it delivered to your doorstep!</p>
                   
                </div>
            </div>
        </section>
    </main>
  	
  	<!-- footer -->	
 	<div class="container">
 		<footer class="mb-5">&copy; 2022. Made with <i class="fa-solid fa-heart"></i> in Penang. </footer>
 	</div>
    
    <script src="https://kit.fontawesome.com/e19fcdf015.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
  </body>
</html>
```

```html
<!-- file: stores.jsp -->
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>One Stop Groceries</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
  </head>
  <body>
  	<nav class="navbar navbar-expand-sm navbar-light bg-light border-bottom fixed-top">
  		<div class="container">
  			<span class="navbar-brand mt-1 h1">One<span class="text-warning">Stop</span>Groceries
  			<i class="fa-solid fa-truck"></i>
  			</span>

            <button type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" class="navbar-toggler" 
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle Navigation"><span class="navbar-toggler-icon"></span></button>
    
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav col-lg-9">
                    <li class="nav-item active">
                        <a href="/" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a href="#" class="nav-link">About</a>
                    </li>
                    <li class="nav-item active">
                        <a href="/stores" class="nav-link">LocateMe</a>
                    </li>
                </ul>
        
  		</div>
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 text-center container mt-5">
            <div class="row py-lg-5">
                <div class="col-lg-9 mx-auto">
                    <h2 class="fw-light">Locate your nearest store!</h2>
                    <p class="lead text-muted">We are a digital learing and talents platform with the mission to deliver growth for talents and enterprises in the digital economy.</p>
                	<a href="/stores"><i class="fa-solid fa-shop"></i> View Stores</a></t>
                    <a href="/new-store"><i class="fa-solid fa-plus"></i> Add Store</a>
                </div>
            </div>

            <div class="input-group mt-5 mb-5">
            
            	<c:url var="get_search_url" value="search" />
            	<form action="${get_search_url}" method="get" class="input-group mb-3">
            		<input class="form-control" type="search" name="keyword"
                		placeholder="Type location or address to find your nearest store here" aria-label="Search">
                	<button type="button" class="btn btn-secondary">Search</button>	
                </form>
                
            </div>
        </section>

        <section class="container">

        </section>
    </main>

    <div class="container mb-5">
        <div class="row row-cols-1 row-cols-md-3 g-4">
        
        	<c:if test="${empty stores}">
        		<div class="container">
        			<h4 class="text-danger text-center"> No store Found. </h4>	
        		</div>
        	</c:if>
        
        	<c:if test="${not empty stores}">
        	<c:forEach var="store" items="${stores}">
        	<div class="col">
              <div class="card h-100">
                <img src="assets/img/${store.img}" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">${store.name}</h5>
                  <p class="card-text"><i class="fa-solid fa-tag"></i> ${store.info}</p>
                  <p class="card-text"><i class="fa-solid fa-phone"></i> ${store.phone}</p>
                  <p class="card-text"><i class="fa-solid fa-location-dot"></i> ${store.address}</p>
                  <a href="#" class="btn btn-light"><i class="fa-solid fa-store"></i> View Shop</a>
                </div>
                <div class="card-footer">
                  <small class="text-muted">Last updated 3 mins ago</small>
                  <a href="/edit-store?store_id=${store.id}"><i class="fa-solid fa-pen-to-square"style="float: right; display: inline-block;"></i></a>
                </div>
              </div>
            </div>
            </c:forEach>
            </c:if>

        </div>
    </div>
  	
  	<!-- footer -->	
 	<div class="container">
 		<footer class="mb-5">&copy; 2022. Made with <i class="fa-solid fa-heart"></i> in Penang. </footer>
 	</div>
    
    <script src="https://kit.fontawesome.com/e19fcdf015.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
  </body>
</html>
```

```html
<!-- file: new-store.jsp -->
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>One Stop Groceries</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
  </head>
  <body>
  	<nav class="navbar navbar-expand-sm navbar-light bg-light border-bottom fixed-top">
  		<div class="container">
  			<span class="navbar-brand mt-1 h1">One<span class="text-warning">Stop</span>Groceries
  			<i class="fa-solid fa-truck"></i>
  			</span>

            <button type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" class="navbar-toggler" 
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle Navigation"><span class="navbar-toggler-icon"></span></button>
    
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a href="#" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a href="#" class="nav-link">About</a>
                    </li>
                    <li class="nav-item active">
                        <a href="/stores" class="nav-link">LocateMe</a>
                    </li>
                </ul>
  		</div>
 
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 text-center container">
            <div class="row mt-5">
                <div class="col-lg-9 mx-auto">
                    <h2 class="fw-light">Add New Store</h2>
                </div>
            </div>
        </section>
    </main>

    <div class="container" style="width: 40rem; margin: auto;">
      <div class="mb-4">
        <h5>Basic Information</h5>
      </div>
      
      <form action="/add-store" method="post">

        <div class="mb-3">
          <label class="form-label">*Store name: </label>
          <input type="text" class="form-control" name="name" id="name" aria-label="Store name" required />
        </div>

        <div class="mb-3">
          <label class="form-label">*Store description: </label>
          <input type="text" class="form-control" name="info" id="info" aria-label="Store description" required />
        </div>

        <div class="mb-3">
          <label class="form-label">*Phone: </label>
          <input type="tel" class="form-control" name="phone" id="phone" aria-label="Store phone number" required />
        </div>

        <div class="mb-3">
          <label class="form-label">*Address: </label>
          <input type="text" class="form-control" name="address" id="address" aria-label="Store address" required />
        </div>
        
      <div class="mb-3">
        <label class="form-label" for="customFile">Store photo:</label>
        <input type="file" name="img" class="form-control" id="customFile" required />
      </div>

      <div class="py-5">
        <a href="/stores" class="btn btn-danger">Cancel</a>
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>
     </form>
      
    </div>

    
  	
  	<!-- footer -->	
 	<div class="container py-lg-5">
 		<footer class="mb-5">&copy; 2022. Made with <i class="fa-solid fa-heart"></i> in Penang. </footer>
 	</div>
    
    <script src="https://kit.fontawesome.com/e19fcdf015.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
  </body>
</html>
```

### Setup database

```sql
create table `store` (
    id integer not null auto_increment,
    name varchar(255) not null,
    info varchar(255) not null,
    phone varchar(20),
    address varchar(255),
    img varchar(255),
    primary key (id)
);
```

### Demo

![Demo](/src/main/resources/static/assets/gif/demo1.gif)