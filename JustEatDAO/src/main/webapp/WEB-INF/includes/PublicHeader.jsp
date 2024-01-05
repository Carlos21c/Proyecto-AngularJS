<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<header class="mainHeader">
    <div class="headerContainer">
        <div class="logoContainer">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="webPageLogo" >
           	<h1>Just Eat</h1>
        </div><!-- logo -->
        <input type="checkbox" id="menuToggle">
        <label for="menuToggle" class="menuToggleLabel">&#9776;</label>
        <nav class="menuContainer">
            <a href="<c:url value='SignUpServlet.do'/>">Sign Up</a>
            <a href="<c:url value='LoginServlet.do'/>">Login</a>
        </nav><!-- menu -->
    </div><!-- container -->
</header><!-- header -->