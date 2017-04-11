var app = angular.module('app', ['pathing_controller']);

// Update User Account
app.controller('accountsController', ['$scope', '$http','$location','pathingService', function($scope, $http, $location,pathingService) {	

	$scope.rec_strength = {};
	var rso = [
   		{ value: 0, text: "All Recommendations"},
   		{ value: 1, text: "Items we think you might like"},
   		{ value: 2, text: "Only our most recommended"}
   		];
	
	var get_rec_options = function()
	{
		return rso;
	}
	
	$scope.rec_strength_options = get_rec_options();
	
	// Update User Profile
	$scope.modify_profile = function() {
		var req = $http.post(
				(pathingService.getCurrentPath('user/update-account')), $scope.rec_strength)
				req.success(function(data)
				{
					window.location.href = pathingService.getCurrentPath("user/account");
				}
		);
	}
	
	
	
}]);