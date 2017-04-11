<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>Password Reset</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
</head>
<body ng-app="app" ng-controller="loginController">	
	<div style="height: 100px;"></div>
	<div class="inner-frame">
		<div class="title-div-medium">
			<p>Enter your email address below to reset your password.</p>
		</div>
		<div>
			<div class="col-xs-8">
				<input type="email" class="form-control" name="email" id="lost_email" placeholder="Enter email">
			</div>
			<div class="col-xs-4">
				<button class="confirm-button" style="width: 90px; height: 33px"  ng-click="view_reset_password()">Send Email</button>
			</div>
		</div>
		<div class="fnt-red" ng-show="password_link_sent">Please check your email for a link on how to reset your password.</div>
		<div class="fnt-red" ng-show="no_email_found">We couldn't find your account with the email you provided.</div>
		<div class="center">
			
		</div>	
	</div>
</body>
</html>