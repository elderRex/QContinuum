var app = angular.module('home', ['pathing_controller']);

//Retrieve questions from the db
app.service('questionsService', function($http,$location,pathingService) {
	
	var user_questions;
	
	return {
		fetchQuestions: function() { return user_questions; },
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
	$scope.overlay_off = false;
	
	// Get a random selection of review from the database
	$scope.user_setup_init = function() {
		questionsService.getQuestions().then(function(promise) {
			$scope.user_answers = questionsService.fetchQuestions().data;
			$scope.overlay_off = true;
		});
	}
	
	
	// Submit Review Responses
	$scope.submit_questions = function() {

		
	}
	
}]);