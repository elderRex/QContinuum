<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<nav class="navigation navbar-fixed-top">
	<div>
		<div class="col-xs-7 nav-col">
			QCApp
		</div>
		<div class="col-xs-2 nav-col">
			<div>Welcome ${userEmail.getFirstname()}!</div>
		</div>
		<div class="col-xs-3 nav-col">
					
					<security:authorize access="hasRole('ROLE_USER')">
	   					<a href="<c:url value="/user/account" />" >
	   						<span class="glyphicon glyphicon-user"></span>
						</a>
					</security:authorize>
					<span style="margin-right: 10px"></span>
					<security:authorize access="hasRole('ROLE_USER')">
		   				<a href="<c:url value="logout" />" >
							Logout
						</a>
					</security:authorize>
		</div>
	</div>			
</nav>