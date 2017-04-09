<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>QCApp</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
</head>
<body ng-app="home" ng-controller="homeController" ng-init="load()">
	<nav class="navigation navbar-fixed-top">
		<div>
			<div class="col-xs-2 nav-col">
				QCApp
			</div>
			<div class="col-xs-4">
				<div class="error-message">${auth_failed}</div>
			</div>
			<jsp:include page="partials/login.jsp"/>
		</div>			
	</nav>
	<div class="inner-frame">
		<jsp:include page="partials/create_account.jsp"/>
	</div>
</body>
</html>