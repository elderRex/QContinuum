<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>
	<title>Recommendations</title>
	<script type="text/javascript" src="<c:url value="/resources/js/users.js" />"> </script>
</head>
<div class="overlay" ng-hide="fav_overlay_off">
	<div class="loader"></div>
	<div class="overlay-text">Almost there, we need about 3 minutes to get you good results...</div>
</div>
<body ng-app="app" scroll ng-controller="userController" ng-init="favorites_init()">
	<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div style="height: 400px" ng-repeat="r in user_favorites" class="centered-box w-80">
				<div class="col-xs-4">
					<img src="/qc/resources/img/{{r.name}}.jpg" onError="this.onerror=null;this.src='/qc/resources/img/no_image.jpg';" />
				</div>
				<div class="col-xs-8">
					<div class="item-elem" style="text-align: center;">
						<div class="col-xs-10">
							<span>Name: </span><span style="font-weight: none; color : grey;">{{r.name}}</span>
						</div>
					</div>
					<div class="item-elem" style="margin-top: 10px;">
						Subject :
						<span style="font-weight: lighter" ng-show="(r.subject.length > 2)">
							{{r.subject}}
						</span>
						<span style="font-weight: lighter" ng-show="!(r.subject.length > 2)">
							General
						</span>
					</div>
					<div class="item-elem" style="margin-top: 10px; height: 175px; overflow: auto;">
						Description : <span style="font-weight: lighter">{{r.description}}</span>
					</div>
				</div>
		</div>
	</div>
</body>
</html>


