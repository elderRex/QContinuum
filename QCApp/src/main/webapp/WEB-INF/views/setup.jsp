<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>

	<title>Account Setup</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/resources/js/users.js" />"> </script>

</head>
<body ng-app="home" ng-controller="userController" ng-init="user_setup_init()">
<div class="overlay" ng-hide="overlay_off">
	<div class="loader">Loading...</div>
</div>
<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div ng-repeat="q in user_answers" class="centered-box w-50 h-500" ng-show="q.show">
				<div class="item-elem">
					{{q.iid.name}}
				</div>
				<div class="scroller">
					{{q.review_text}}
				</div>
				<div class="ctr well">
					<div class="title-div-medium">
						<span>Please read part of the review and tell us if you like it or agree with it</span>
					</div>
					<div ng-show="$last">
						<div class="button-option col-xs-4"></div>
						<div class="button-option col-xs-4">
							<button ng-click="submit_answers(true,$index)">Submit Answers</button>
						</div>
					</div>
					<div ng-show="!$last">
						<div class="button-option col-xs-4"></div>
						<div class="button-option col-xs-2">
							<button ng-click="review_liked(true,$index)">Like</button>
						</div>
						<div class="button-option col-xs-2">
							<button ng-click="review_liked(false,$index)">Dislike</button>
						</div>
					</div>
				</div>
		</div>
	</div>
</body>
</html>