<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>New Password</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
</head>
<body ng-app="app" ng-controller="loginController" ng-init="init_pw_change">	
	<div class="centered-box w-50 h-500">
		<div style="text-align: center; height: 50px">
			<span>Reset Your Password</span>
		</div>
		<div style="color: red" ng-show="password_change_error">
			Something went wrong. Please make sure that your password reset link hasn't expired, and that your email is correct.
		</div>
		<div style="text-align: center; color: grey;">
			<p>Passwords must be at least 7 characters long, and include one numeric character</p>
		</div>
		<div class="item-elem">
			<div class="col-xs-6">
				<p>Please enter your email.</p>
			</div>
			<div class="col-xs-6">
				<input type="email" id="password-email" class="form-control" name="email_confirmation" id="lost_email" placeholder="Enter Existing Email">
			</div>
		</div>
		<div class="item-elem">
			<div class="col-xs-6">
				<p>Please enter a new password.</p>
			</div>
			<div class="col-xs-6">
				<input type="password" id="new-password" class="form-control" name="email" id="lost_email" placeholder="Enter New Password">
			</div>
		</div>
		<div class="item-elem">
			<div class="col-xs-6">
				<p>Please confirm your new password.</p>
			</div>
			<div class="col-xs-6">
				<input type="password" id="new-password-confirmation" class="form-control" name="password" id="new_password" placeholder="Confirm New Password">
			</div>
		</div>
		<div style="text-align: center">
			<button class="confirm-button" ng-click="update_password()">Set Password</button>
		</div>
	</div>
</body>
</html>