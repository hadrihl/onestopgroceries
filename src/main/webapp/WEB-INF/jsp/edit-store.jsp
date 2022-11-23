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
  		
  		<button type="button" class="btn btn-outline-warning me-2" style="border: none;">
            	@<c:out value="${pageContext.request.remoteUser}"/>
        </button>
        
        <form action="/logout" method="post">
        	<button type="submit" class="btn btn-secondary">Sign out</button>
        </form>
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 text-center container">
            <div class="row mt-5">
                <div class="col-lg-9 mx-auto">
                    <h2 class="fw-light">Edit Store</h2>
                </div>
            </div>
        </section>
    </main>

    <div class="container" style="width: 40rem; margin: auto;">
      <div class="mb-4">
      	<div class="row">
      		<div class="col-lg-10">
        	<h5>Basic Information</h5>
        	</div>
       	
       		<div class="col-lg-2 me-auto">

<button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#myModal">
    Delete
  </button>

<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">${store.name}</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
        Are you sure you want delete the store?
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
        <a href="/delete-store?store_id=${store.id}" class="btn btn-danger">Delete</a>
      </div>

    </div>
  </div>
</div>

       		</div>
      	</div>
        
      </div>
      
      <form class="form-group" action="/update-store?store_id=${store.id}" method="post" modelAttribute="store" enctype="multipart/form-data">
      <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
				
      	<div class="mb-3">
      		<label class="form-label">Store ID: </label>
      		<input class="form-control" name="store_id" value="${store.id}" aria-label="Store ID" readonly="true" />
      	</div>

        <div class="mb-3">
          <label class="form-label">*Store name: </label>
          <input class="form-control" name="name" id="store_name" value="${store.name}" aria-label="Store name" required />
        </div>

        <div class="mb-3">
          <label class="form-label">*Store description: </label>
          <input class="form-control" name="info" id="store_desc" value="${store.info}" aria-label="Store description" required />
        </div>
        
        <div class="mb-3">
        	<label class="form-label" for="customFile">Store photo</label>
        	<input type="file" name="imgFile" class="form-control" id="imgFile" value="${store.img}" />
      	</div>
      	
      	<div class="py-5">
        	<a href="/stores" class="btn btn-danger">Cancel</a>
        	<button type="submit" class="btn btn-success">Update</button>
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