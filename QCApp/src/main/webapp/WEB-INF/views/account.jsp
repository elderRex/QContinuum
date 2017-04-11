<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>Your Account</title>
	<script type="text/javascript" src="<c:url value="/resources/js/accounts.js" />"> </script>
</head>
<body ng-app="app" ng-controller="accountsController" ng-init="overlay_off=true">
	<div class="overlay" ng-hide="overlay_off">
		<div class="loader">Loading...</div>
	</div>
	<div class="update-overlay" ng-hide="hide_update">
		<div class="loader">Loading...</div>
		<div class="updated-message">Profile Updating</div>
	</div>
	<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div class="centered-box w-50 h-500">
			<div class="title-div-medium" style="text-align: center;">
				<span>Your Account Details</span>
			</div>
			<div class="item-elem">
				<span>Name : </span>
				<span style="font-weight: none; color : grey;">${userEmail.getFirstname()} ${userEmail.getLastname()}</span>
			</div>
			<div class="item-elem">
				<span>Email: </span>
				<span style="font-weight: none; color : grey;">${userEmail.getEmail()}</span>
			</div>
			<div class="item-elem">
				<span>Return recommendations that are : </span>
				<span id="recStrength" style="font-weight: none; color : grey;">${userEmail.getRecStrengthString()}</span>
			</div>
			<div class="item-elem">
					<span>Quality of recommendations : </span>
					<select class="form-control" id="rec_str" ng-model="rec_strength" ng-options="item as item.text for item in rec_strength_options">
						<option value=""> Select Which Recommendations We Should Display </option>
					</select>
			</div>
			<div class="item-elem" style="margin-top : 40px;">
				<button ng-click="modify_profile()">
       					Save Options
     			</button>
			</div>
		</div>
	</div>
</body>
</html>