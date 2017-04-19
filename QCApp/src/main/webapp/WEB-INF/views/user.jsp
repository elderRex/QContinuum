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
		<div style="width: 80%;" class="centered-box" ng-show="overlay_off">
			<div style="text-align: center; font-weight: bold; margin-bottom: 10px;">Filter Results</div>
			<div>
				<span class="filter" ng-click="filter_results(fo)" ng-repeat="fo in filter_options" ng-style="fo.style">{{fo.text}}</span>
			</div>
		</div>
		<div ng-repeat="r in user_recommendations" class="centered-box w-80 h-600" ng-show="itemVisible(r) || all_results">
				<div class="col-xs-4">
					<img src="/qc/resources/img/{{r[0].name}}.jpg" onError="this.onerror=null;this.src='/qc/resources/img/no_image.jpg';" />
					<div class="item-elem" style="margin-top: 10px;">
						Subject :
						<span style="font-weight: lighter" ng-show="(r[0].subject.length > 2)">
							{{r[0].subject}}
						</span>
						<span style="font-weight: lighter" ng-show="!(r[0].subject.length > 2)">
							General
						</span>
					</div>
					<div class="item-elem" style="margin-top: 10px; height: 175px; overflow: auto;">
						Description : <span style="font-weight: lighter">{{r[0].description}}</span>
					</div>
				</div>
				<div class="col-xs-6">
					<div class="item-elem" style="text-align: center;">
						<div class="col-xs-10">
							<span>Name: </span><span style="font-weight: none; color : grey;">{{r[0].name}}</span>
						</div>
					</div>
					<div class="item-elem">
						Sample Reviews
					</div>
					<div>
						<div class="item-values" style="margin : 10px;" ng-repeat="rev in r[1] | limitTo: 6">
							<div>Rating : {{rev.rating}}</div>
							<div class="inline-ellipsis">"{{rev.review_text}}"</div>
						</div>
					</div>
				<!-- 	<div class="item-elem">
						<span>Website: </span><span class="item-values"><a ng-href="http://www.{{r[0].website}}" target="_blank">{{r[0].website}}</a></span>
					</div> -->
				</div>
				<div class="col-xs-2">
					<div ng-show="!isInFavs(r[0].id) && !r[0].fav" style="cursor: pointer">			
						<span class="glyphicon glyphicon-star-empty" style="color: gold; font-size: 25px" ng-click="add_favorite(r,r[0].id)"></span>
						Add Favorite
					</div>
					<div ng-show="isInFavs(r[0].id) || r[0].fav">			
						<span class="glyphicon glyphicon-star" style="color: green; font-size: 25px"></span>
						Your Favorite
					</div>
				</div>
		</div>
		<div id="data_loader" class="loader" style="width: 2em; height:2em; position: fixed; top: 95%;"></div>
	</div>
</body>
</html>


