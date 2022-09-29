<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>One Stop Groceries | Localities</title>
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
                        <a href="/stores" class="nav-link">Stores</a>
                    </li>
                </ul>
        
  		</div>
  		
  		<button type="button" class="btn btn-outline-primary me-2" style="border: none;">
            @<c:out value="${pageContext.request.remoteUser}"/>
        </button>
            
        <form action="/logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
            <button type="submit" class="btn btn-danger">Logout</button>
        </form>
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 container mt-5">
            <div class="row py-lg-5">
                <div class="col-lg-4 mx-auto">
                    <h2 class="fw-light text-center">${store.name}</h2>
                    
                    <div class="col">
              <div class="card h-100">
                <img src="assets/img/${store.img}" class="card-img-top" alt="...">
                <div class="card-body">
                  <p class="card-text"><i class="fa-solid fa-tag"></i> ${store.info}</p>
                  <a class="btn btn-light" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                  <i class="fa-solid fa-plus"></i>Add New Localities</a>
                  
                </div>
                <div class="card-footer">
                  <small class="text-muted">Last updated 3 mins ago</small>
                  <a href="/edit-store?store_id=${store.id}"><i class="fa-solid fa-pen-to-square"style="float: right; display: inline-block;"></i></a>
                </div>
              </div>
            </div>
                    
                </div>
                
              
            </div>
        </section>

        <section class="container">

        </section>
    </main>

    <div class="container mb-5 col-lg-6 mx-auto">

<div class="collapse" id="collapseExample">
  <div class="card card-body mb-3" style="width: 40rem;">
  
  	<form action="/add-local?store_id=${store.id}" method="post">
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
				
  		<div class="mb-3">
          <label class="form-label">*Local branch name: </label>
          <input type="text" class="form-control" name="name" id="name" value="${store.name} @ " aria-label="Store name" required />
        </div>

        <div class="mb-3">
          <label class="form-label">*Local address: </label>
          <input type="text" class="form-control" name="address" id="address" aria-label="Store address" required />
        </div>
        
        <div class="mb-3">
          <label class="form-label">*Phone: </label>
          <input type="tel" class="form-control" name="phone" id="phone" aria-label="Store phone number" required />
        </div>
        
        <div class="mb-2">
        	<a class="btn btn-danger" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="true" aria-controls="collapseExample">Cancel</a>
        	<button type="submit" class="btn btn-success">Add</button>
      	</div>
  	</form>
  </div>
</div>

        <div class="row row-cols-1 row-cols-md-3 g-4">
<!--         	<c:if test="${empty localities}">
        		<div class="container">
        			<h4 class="text-danger text-center"> No store Found. </h4>	
        		</div>
        	</c:if>
        
        	<c:if test="${not empty localities}">
        	<c:forEach var="store" items="${localities}">
        	<div class="col">
              <div class="card h-100">
                <img src="assets/img/${local.img}" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">${local.name}</h5>
                  <p class="card-text"><i class="fa-solid fa-tag"></i> ${local.info}</p>
                </div>
                <div class="card-footer">
                  <small class="text-muted">Last updated 3 mins ago</small>
                  <a href="/edit-store?store_id=${local.id}"><i class="fa-solid fa-pen-to-square"style="float: right; display: inline-block;"></i></a>
                </div>
              </div>
            </div>
            </c:forEach>
            </c:if> -->

        </div>
       
    </div>

	<div class="container mb-5">
		<h3>Localities</h3>
		
		<c:if test="${empty localities}">
			<div class="row py-5">
				<p> No local store found. </p>
			</div>
		</c:if>
		
		<c:if test="${not empty localities}">
		<c:forEach var="local" items="${localities}">
		<div class="col py-2">
        	<div class="card h-100">
                <div class="card-body">
                  <h5 class="card-title">${local.name}</h5>
                  <p class="card-text"><i class="fa-solid fa-phone"></i> ${local.phone}</p>
                  <p class="card-text"><i class="fa-solid fa-location-dot"></i> ${local.address}</p>
                </div>
                <div class="card-footer">
                  <small class="text-muted"></small>
                  <a href="/edit-local?local_id=${local.id}"><i class="fa-solid fa-pen-to-square"style="float: right; display: inline-block;"></i></a>
                </div>
           	</div>
        </div>
		</c:forEach>
		</c:if>
		
    </div>
  	
  	<!-- footer -->	
 	<div class="container">
 		<footer class="mb-5">&copy; 2022. Made with <i class="fa-solid fa-heart"></i> in Penang. </footer>
 	</div>
    
    <script src="https://kit.fontawesome.com/e19fcdf015.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
  </body>
</html>