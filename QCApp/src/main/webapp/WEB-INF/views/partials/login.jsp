<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
<form method="post" action="<c:url value='/j_spring_security_check' />">
	<div class="col-xs-2 nav-col">
		<div >
 				Email: <input type="email" name="email" placeholder="Enter email">
		</div>
		<label>
 			<a style="cursor: pointer" ng-click="change_password()"/>Forgot Password?</a>
		</label>
	</div>
	<div class="col-xs-2 nav-col">
 		<input type="password" name="password" id="inputPassword" placeholder="Password">
 		<span>
			${messageIncorrectPassword}
		</span>
	</div>
	<div class="col-xs-2 nav-col">
		<button type="submit"  value="Login">Sign In</button>
	</div>
</form>