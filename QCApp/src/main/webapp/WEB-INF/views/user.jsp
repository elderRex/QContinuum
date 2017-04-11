<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>Recommendations</title>
	<script type="text/javascript" src="<c:url value="/resources/js/users.js" />"> </script>
</head>
<div class="overlay" ng-hide="overlay_off">
	<div class="loader"></div>
	<div class="overlay-text">Almost there, we need about 3 minutes to get you good results...</div>
</div>
<body ng-app="app" scroll ng-controller="userController" ng-init="user_init()">
	<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div ng-repeat="r in user_recommendations" class="centered-box w-50 h-500">
				<div class="item-elem" style="text-align: center;">
					<span>Name: </span><span style="font-weight: none; color : grey;">{{r[0].name}}</span>
				</div>
				<div class="item-elem">
					<span>Description: </span><span class="item-values">{{r[0].description}}</span>
				</div>
				<div class="item-elem">
					Sample Reviews
				</div>
				<div class="scroller">
					<div class="item-values" style="margin : 10px;" ng-repeat="rev in r[1] | limitTo: 4">
						<div>Rating : {{rev.rating}}</div>
						<div class="inline-ellipsis">"{{rev.review_text}}"</div>
					</div>
				</div>
				<div class="item-elem">
					<span>Website: </span><span class="item-values"><a ng-href="http://www.{{r[0].website}}" target="_blank">{{r[0].website}}</a></span>
				</div>
		</div>
	</div>
</body>
</html>


