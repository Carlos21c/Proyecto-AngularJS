<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<header class="mainHeader">
    <div class="headerContainer">
        <div class="logoContainer">
            <a href="<c:url value='/ShowIndexServlet.do'/>"><img src="${pageContext.request.contextPath}/images/logo.png" alt="webPageLogo" ></a>
            <a href="<c:url value='/ShowIndexServlet.do'/>"><h1>Just Eat</h1></a>
        </div><!-- logo -->
        <input type="checkbox" id="menuToggle">
        <label for="menuToggle" class="menuToggleLabel">&#9776;</label>
        <nav class="menuContainer">
        	<a href="<c:url value='/p/ShowCartServlet.do'/>">Cart: ${cart.price}€</a>
        	<a href="<c:url value='/p/ShowFavoritesServlet.do'/>">Favorites</a>
            <a href="<c:url value='/p/ListUserRestaurantsServlet.do'/>">My Restaurants</a>
            <a href="<c:url value='/p/ListUserOrdersServlet.do'/>">My Orders</a>
            <a href="<c:url value='/p/ListCategoriesServlet.do'/>">Categories</a>
            <a href="<c:url value='/p/ShowProfileServlet.do'/>">Profile</a>
            <a href="<c:url value='/p/LogoutServlet.do'/>">Logout</a>
        </nav><!-- menu -->
    </div><!-- container -->
</header><!-- header -->