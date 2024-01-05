<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign Up</title>
    <meta charset="utf-8" >
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
</head>
<body>
	<jsp:include page="/WEB-INF/includes/PublicHeader.jsp" />
    <div class="simpleContainer">
		<h2>Sign up</h2>
		<form method="post" action="SignUpServlet.do">
            <input class="inputStyle" type="text" id="name" name="name" placeholder="Introduce your name..." required>
            <input class="inputStyle" type="text" id="surname" name="surname" placeholder="Introduce your surname..." required>
			<input class="inputStyle" type="email" id="email" name="email" placeholder="Introduce your email..." required>
			<input class="inputStyle" type="password" id="password" name="password" placeholder="Introduce your password..." required>
			<input class="submitButton" type="submit" value="Sign up">
				<p>${message}</p>
			<p>Already have an account? <a class="linkStyle" href="<c:url value='LoginServlet.do'/>">Login</a></p>
		</form>
	</div><!-- simpleContainer -->
    <jsp:include page="/WEB-INF/includes/Footer.jsp"/>
</body>
</html>