<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>

	<title>Find Stuff</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
</head>
<body ng-app="app" ng-controller="homeController" ng-init="user_init()">
	<!-- <div class="overlay" ng-hide="overlay_off">
		<div class="loader">Loading...</div>
	</div> -->
	<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		
	</div>
</body>
</html>


