var app = angular.module('app', ['pathing_controller']);

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
					    method: "GET",
					    contentType: "text/html; charset=utf-8",
					 }).then(function(questions) { 
						 debugger
						 user_questions = questions; 
					});
	    		return result;
	    }
	  };

	});

/* Detect a scroll and invokes load of additional recs */
app.directive("scroll", function ($window) {
    return function(scope, element, attrs) {
        angular.element($window).bind("scroll", function() {
        		if($(window).scrollTop() + $(window).height() > $(document).height() - 1600) {
        			if (scope.model_ids.length > 0)
        				scope.invoke_next_load();
        		}
        });
    };
});


app.controller('userController', ['$scope', '$http','$location','pathingService','questionsService', function($scope, $http, $location,pathingService,questionsService) {	

	$scope.user_answers = {};
	$scope.user_recommendations = [];
	$scope.overlay_off = false;
	$scope.questions_answered = false;
	$scope.slice_size = 0;
	$scope.slice_index = 0;
	$scope.model_ids = [];
	
	$scope.invoke_next_load = function()
	{
		$scope.slice_index += 1;
		var sub_arr = $scope.model_ids.slice($scope.slice_index*$scope.slice_size,$scope.slice_size+($scope.slice_index*$scope.slice_size));
		var model_recommendations = JSON.stringify(sub_arr);
		$scope.get_rec_slice(model_recommendations);
	}
	
	$scope.get_rec_slice = function(recs)
	{
		// Retrieve ItemsEntity for every recommendation
		$.ajax({
		  type: "POST",
		  contentType:'application/json',
		  url: pathingService.getCurrentPath("user/model-recommendations"),
		  data: recs
		}).then(function(data) {
			$scope.$apply(function() {
					if (data.length > 0)
					{
						var parsed = JSON.parse(data);
						for(var iii = 0; iii < parsed.length; iii++)
						{
							$scope.user_recommendations.push(parsed[iii]);
						}
						$scope.overlay_off = true;
					}
				});
			
		});
	}
	
	$scope.favs = {};
	
	$scope.add_favorite = function(r, iid)
	{
		r[0].fav = true;
		var req = $http.post(
				(pathingService.getCurrentPath('user/create-favorite')), iid.toString())
				req.success(function(data)
				{
					
				}
		);
	}
	
	$scope.isInFavs = function(id)
	{
		debugger
		return $scope.favs.includes(id.toString());
	}
	
	// Initialize Users Main Recommendations page
	$scope.user_init = function() {
		// Get Item Recommendations ID's from model API for a given user
		questionsService.getRecommendations().then(function(promise) {
			
			$http.get(pathingService.getCurrentPath('user/get-user-favs')).then(function(favs)
			{
				$scope.favs = favs.data;
			});
			
			$scope.model_ids = promise;
			$scope.slice_size = Math.round(promise.length/50);
			if ($scope.slice_size > 5)
				$scope.slice_size = 5;
			
			// Get the first_slice
			var sub_arr = $scope.model_ids.slice(0,$scope.slice_size);
			var model_recommendations = JSON.stringify(sub_arr);
			$scope.get_rec_slice(model_recommendations);
		 });
	}
	
	// Get a random selection of review from the database
	$scope.user_setup_init = function() {
		questionsService.getQuestions().then(function(promise) {
			$scope.user_answers = questionsService.fetchQuestions().data;
			$scope.user_answers[0].show = true;
			$scope.overlay_off = true;
		});
	}
	
	
	// Submit Review Responses
	$scope.submit_answers = function() {
		$scope.overlay_off = false;
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