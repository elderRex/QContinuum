<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>

	<title>Find Stuff</title>
	<script type="text/javascript" src="<c:url value="/resources/js/users.js" />"> </script>
</head>
<body ng-app="home" ng-controller="userController" ng-init="user_init()">
	<!-- <div class="overlay" ng-hide="overlay_off">
		<div class="loader">Loading...</div>
	</div> -->
	<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div ng-repeat="r in user_recommendations" class="centered-box w-50 h-500">
				<div class="item-elem">
					<span>Name: </span><span style="font-weight: none; color : grey;">{{r.name}}</span>
				</div>
				<div class="scroller">
					{{r.description}}
				</div>
				<div class="scroller">
					{{r}}
				</div>
				<div>
					{{r.website}}
				</div>
		</div>
	</div>
</body>
</html>


