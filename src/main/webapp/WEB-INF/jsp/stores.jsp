<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>One Stop Groceries | Stores</title>
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
                        <a href="/about" class="nav-link">About</a>
                    </li>
                    <li class="nav-item active">
                        <a href="/stores" class="nav-link">Stores</a>
                    </li>
                </ul>
  		</div>
  		
  		<c:if test="${empty pageContext.request.remoteUser}">
        	<a href="/signin" class="btn btn-primary">Sign In</a>
		</c:if>
		<c:if test="${not empty pageContext.request.remoteUser}">
			<button type="button" class="btn btn-outline-primary me-2" style="border: none;">
            	@<c:out value="${pageContext.request.remoteUser}"/>
        	</button>
            
        	<form:form action="/logout" method="post">
            	<button type="submit" class="btn btn-danger">Logout</button>
        	</form:form>
		</c:if>
  	</nav>
 
    <!-- main section -->
    <main>
        <section class="py-4 text-center container mt-5">
            <div class="row py-lg-5">
                <div class="col-lg-9 mx-auto">
                    <h2 class="fw-light">Locate your nearest store!</h2>
                    <p class="lead text-muted">We are a digital learning and talents platform with the mission to deliver growth for talents and enterprises in the digital economy.</p>
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
                  <a href="/localities?store_id=${store.id}" class="btn btn-light"><i class="fa-solid fa-store"></i> View Localities</a>
                </div>
                <div class="card-footer">
                  <small class="text-muted">Last updated <time class="timeago" datetime="${store.updatedOn }"></time>
                  </small>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.7/jquery.timeago.min.js" type="text/javascript"></script>
    <script>
    	jQuery(document).ready(function() {
    	  jQuery("time.timeago").timeago();
    	});
    </script>
  </body>
</html>