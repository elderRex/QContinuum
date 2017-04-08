<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<nav class="navigation navbar-fixed-top">
	<div>
		<div class="col-xs-10 nav-col">
			QCApp
			<div>${userEmail.getEmail()}</div>
		</div>
		<div class="col-xs-2 nav-col">
					<security:authorize access="hasRole('ROLE_USER')">
		   				<a href="<c:url value="logout" />" >
							Logout
						</a>
					</security:authorize>
		</div>
	</div>			
</nav>