var app = angular.module('home', ['pathing_controller']);

//Retrieve questions from the db
app.service('questionsService', function($http,$location,pathingService) {
	
	var user_questions;
	var user_recommendations;
	var model_recommendations;
	
	return {
		fetchQuestions: function() { return user_questions; },
		fetchModelRecommendations: function() { return model_recommendations; },
		fetchRecommendations: function() { return user_recommendations; },
		getRecommendations:     	
	    	function () {
			var result = 
			$http.get(pathingService.getCurrentPath('user/get-uid')).then(function(uid)
			{
				url = pathingService.getCurrentPath("user/get-recommendations");
				var res = $.ajax(
				{
				    type: 'POST',
				    url: 'http://35.184.109.108:5000/recommend/query',
				    crossDomain: true,
				    data: { "uid": uid.data},
				    dataType: 'json',
				    success: function(responseData, textStatus, jqXHR)
				    { 
				    		debugger
				    }
				})
				return res;

		});
		return result    		 
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
	$scope.questions_answered = false;
	
	// Initialize Users Main Recommendations page
	$scope.user_init = function() {
		// Get Item Recommendations ID's from model API for a given user
		questionsService.getRecommendations().then(function(promise) {
			var model_recommendations = JSON.stringify(promise);
			debugger
			// Retrieve ItemsEntity for every recommendation
			$.ajax({
			  type: "POST",
			  contentType:'application/json',
			  url: pathingService.getCurrentPath("user/model-recommendations"),
			  data: model_recommendations
			}).then(function(data) {
				$scope.user_recommendations = JSON.parse(data);
				$scope.$apply(function() {
					$scope.overlay_off = true;
					});
				
			});
			
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
		
		if (indx == $scope.user_answers.length-1)
		{
				$scope.questions_answered = true;
		}

		$scope.user_answers[indx].show = false;
		
		// Fails if falls outside the bounds of array
		if (indx < $scope.user_answers.length-1)
		{
			$scope.user_answers[indx+1].show = true;
		}
		
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