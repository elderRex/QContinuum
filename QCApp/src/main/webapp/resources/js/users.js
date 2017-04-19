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
        		if (scope.allow_loading)
        		{
        			var loadMore = $(window).scrollTop() + $(window).height() == $(document).height();
	        		if(loadMore) {
	        			if (scope.model_ids.length > 0)
	        				scope.invoke_next_load();
	        		}
        		}
        });
    };
});


app.controller('userController', ['$scope', '$http','$location','pathingService','questionsService', function($scope, $http, $location,pathingService,questionsService) {	

	$scope.user_answers = {};
	$scope.user_recommendations = [];
	$scope.overlay_off = false;
	$scope.allow_loading = false;
	$scope.questions_answered = false;
	$scope.slice_size = 0;
	$scope.slice_index = 0;
	$scope.model_ids = [];
	$scope.load_more_on = false;
	
	$scope.active_filters = []
	
	$scope.all_results = true;
	
	$scope.itemVisible = function(item)
	{
		if (($scope.active_filters).includes(item[0].subject))
		{
			return true;
		}
		return false;
	}
	
	
	$scope.invoke_next_load = function()
	{
		$("#data_loader").show();
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
						$scope.load_more_on = false;
						$("#data_loader").hide();
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
		return $scope.favs.includes(id.toString());
	}

	$scope.filter_results = function(filter)
	{	
		// If filter is active, change style
		
		// If filter is disabled, add it to list, else remove it
		if (filter.disabled)
		{
			$scope.active_filters.push(filter.text);
		}
		else
		{
			var i = $scope.active_filters.indexOf(filter.text);
			if (i !== -1) {
				$scope.active_filters.splice(i, 1);
			}
		}
		
		filter.disabled = !filter.disabled;
		
		// If Undefined, change to active style filter
		if (filter.style == undefined)
		{
			filter.style= {"background-color": "#337ab7", "color" : "white"}
		}
		else if (!filter.disabled)
		{
			filter.style= {"background-color": "#337ab7", "color" : "white"}
		}
		else
		{
			filter.style= {"background-color": "white", "color": "#337ab7"}
		}
		
		if ($scope.active_filters.length == 0)
		{
			$scope.all_results = true;
		}
		else
		{
			$scope.all_results = false;
		}
	}
	
	$scope.remove_filter = function(filter)
	{
		
	}
	
	$scope.filter_options = [
	   		{ value: 0, text: "Action", disabled : true},
	   		{ value: 1, text: "Comedy", disabled : true},
	   		{ value: 2, text: "Drama", disabled : true},
	   		{ value: 3, text: "War", disabled : true},
	   		{ value: 4, text: "Adventure", disabled : true},
	   		{ value: 5, text: "Horror", disabled : true},
	   		{ value: 6, text: "Documentary", disabled : true},
	   		{ value: 7, text: "Crime", disabled : true},
	   		{ value: 8, text: "Musical", disabled : true},
	   		{ value: 9, text: "Family", disabled : true},
	   		{ value: 10, text: "Mystery", disabled : true},
	   		{ value: 11, text: "Thriller", disabled : true},
	   		{ value: 12, text: "Sci-Fi", disabled : true},
	   		{ value: 13, text: "Romance", disabled : true},
	   		{ value: 14, text: "Western", disabled : true}
	   		];
	
	// Initialize Users Main Recommendations page
	$scope.user_init = function() {
		$("#data_loader").hide();
		
		// Get Item Recommendations ID's from model API for a given user
		questionsService.getRecommendations().then(function(promise) {
			
			$http.get(pathingService.getCurrentPath('user/get-user-favs')).then(function(favs)
			{
				$scope.favs = favs.data;
			});
			
			$scope.model_ids = promise;
			$scope.slice_size = Math.round(promise.length/50);
			if ($scope.slice_size > 10)
				$scope.slice_size = 10;
			
			// Get the first_slice
			var sub_arr = $scope.model_ids.slice(0,$scope.slice_size);
			var model_recommendations = JSON.stringify(sub_arr);
			$scope.get_rec_slice(model_recommendations);
			$scope.allow_loading = true;
		 });
	}
	
	$scope.favorites_init = function()
	{
		$scope.fav_overlay_off = false;
		$http.get(pathingService.getCurrentPath('user/get-user-favorites')).then(function(favorites)
		{
			$scope.user_favorites = favorites.data;
			$scope.fav_overlay_off = true;
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