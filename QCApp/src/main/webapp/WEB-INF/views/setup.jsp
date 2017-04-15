<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<jsp:include page="partials/head.jsp"/>
<head>

	<title>Account Setup</title>
	<script type="text/javascript" src="<c:url value="/resources/js/login.js" />"> </script>
	<script type="text/javascript" src="<c:url value="/resources/js/users.js" />"> </script>

</head>
<body ng-app="app" ng-controller="userController" ng-init="user_setup_init()">
<div class="overlay" ng-hide="overlay_off">
	<div class="loader">Loading...</div>
	<!-- <div class="overlay-text">Recording your answers...</div> -->
</div>
<jsp:include page="partials/navigation.jsp"/>
	<div class="inner-frame">
		<div class="centered-box w-50 h-500" ng-show="!ready" ng-hide="!overlay_off">
			<div class="title-div-medium">
				<span class="itxt">Hey ${userEmail.getFirstname()},</span>
			</div>
			<div class="title-div-lg">
				<span class="itxt">
					We're about to show you snippets from some movie reviews.
					After reading each snippet, click 'like' or 'dislike' if you liked the review itself, even if
					 you haven't seen the movie.
				</span>
			</div>
			<div class="title-div-medium" style="text-align: center">
				<button ng-click="ready=true">Get Started</button>
			</div>
		</div>
		<div ng-show="ready">
			<div ng-repeat="q in user_answers" class="centered-box w-50 h-500" ng-show="q.show">
					<div style="text-align: right;">{{$index}} of 20 Reviews Answered</div>
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
						<div ng-show="!questions_answered">
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
		<div ng-show="questions_answered" class="ctr well">
			<div class="title-div-medium">
				<span>You're all set! Click Submit and please wait until your recommendations are ready.</span>
			</div>
			<div>
				<div class="button-option col-xs-4"></div>
				<div class="button-option col-xs-4">
					<button ng-click="submit_answers()">Submit Answers</button>
				</div>
			</div>
	</div>
</div>
</body>
</html>