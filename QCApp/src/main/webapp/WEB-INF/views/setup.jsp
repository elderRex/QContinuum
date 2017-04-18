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
		<div class="centered-box w-50 h-600" ng-show="!ready" ng-hide="!overlay_off">
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
			<div ng-repeat="q in user_answers">
				<div class="centered-box w-80 h-600" ng-show="q.show">
					<div class="col-xs-4">
						<img src="/qc/resources/img/{{q.iid.name}}.jpg" onError="this.onerror=null;this.src='/qc/resources/img/no_image.jpg';" />
					</div>
					<div class="col-xs-8">
						<div style="text-align: right">{{$index}} of 10 Reviews Answered</div>
						<div class="item-elem">
							{{q.iid.name}}
						</div>
						<div class="scroller" style="font-size: 16px; line-height: 180%">
							{{q.review_text | limitTo: 600 }}...
						</div>
						<div class="ctr">
							<div class="title-div-medium">
								<span>Please read part of the review and tell us if you like it or agree with it</span>
							</div>
							<div ng-show="!questions_answered">
								<div class="button-option col-xs-4"></div>
								<div class="button-option col-xs-2" >
									<img ng-click="review_liked(true,$index)" style="width: 75%; height: 75%; cursor: pointer" src="<c:url value="/resources/img/like_btn.jpg" />" alt="" />
								</div>
								<div class="button-option col-xs-2">
									<img ng-click="review_liked(false,$index)" style="width: 75%; height: 75%; cursor: pointer" src="<c:url value="/resources/img/dislike_btn.jpg" />" alt="" />
								</div>
							</div>
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