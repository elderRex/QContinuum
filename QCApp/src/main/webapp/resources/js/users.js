var app = angular.module('home', ['pathing_controller']);

//Retrieve questions from the db
app.service('questionsService', function($http,$location,pathingService) {
	
	var user_questions;
	var user_recommendations;
	
	return {
		fetchQuestions: function() { return user_questions; },
		fetchRecommendations: function() { return user_recommendations; },
		getRecommendations:     	
	    	function () {
			url = pathingService.getCurrentPath("user/get-recommendations");

    			var result = 
    			$http({
				    url: url, 
				    method: "GET"
				 }).then(function(recommendations) { 
					 user_recommendations = recommendations;
//					 user_recommendations.forEach(function(occ){
//						 
//						 
//						 
//					 });
					 // Get Reviews From This Item
					 
					 
				});
    			return result;
			
//			$.ajax({
//			    type: 'POST',
//			    url: 'http://35.184.109.108:5000/recommend/query',
//			    crossDomain: true,
//			    data: { "uid": 52},
//			    dataType: 'json',
//			    success: function(responseData, textStatus, jqXHR) {
//			    debugger;    
//			    	var value = responseData.someKey;
//			    },
//			    error: function (responseData, textStatus, errorThrown) {
//			        alert('POST failed.');
//			    }
//			});
//			$.ajax({
//				  type: "POST",
//				  contentType: "text/html",
//				  url: "http://35.184.109.108:5000/recommend/query",
//				  data: { param: 52}
//				}).then(function(data) {
//				   debugger
//				});
		    		 
	    },
	    getQuestions:     	
	    	function () {
	    	
	    		url = pathingService.getCurrentPath("user/get-questions");

	    		var result = 
	    			$http({
					    url: url, 
					    method: "GET"
					 }).then(function(questions) { 
						 user_questions = questions; 
					});
	    		return result;
	    }
	  };

	});

app.controller('userController', ['$scope', '$http','$location','pathingService','questionsService', function($scope, $http, $location,pathingService,questionsService) {	

	$scope.user_answers = {};
	$scope.user_recommendations = {};
	$scope.overlay_off = false;
	
	// Initialize Users Main Recommendations page
	$scope.user_init = function() {
		questionsService.getRecommendations().then(function(promise) {
			$scope.user_recommendations = questionsService.fetchRecommendations().data;
			$scope.overlay_off = true;
		 });
	}
	
	// Get a random selection of review from the database
	$scope.user_setup_init = function() {
		questionsService.getQuestions().then(function(promise) {
			$scope.user_answers = questionsService.fetchQuestions().data;
			$scope.user_answers[0].show = true;
//			.forEach(function(ua) {
//				ua.position = 
//			});
			$scope.overlay_off = true;
		});
	}
	
	
	// Submit Review Responses
	$scope.submit_answers = function() {
		var req = $http.post(
				(pathingService.getCurrentPath('user/answer_questions')), $scope.user_answers)
				req.success(function(data)
				{
					window.location.href = pathingService.getCurrentPath("user");
				}
		);
		
		
	}
	
	$scope.review_liked = function(is_liked,indx) {
		$scope.user_answers[indx].show = false;
		$scope.user_answers[indx+1].show = true;
		
		if (is_liked)
		{
			$scope.user_answers[indx].liked = true;
		}
		else
		{
			$scope.user_answers[indx].liked = false;
		}
		
	}
	
}]);