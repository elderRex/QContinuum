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
	$scope.user_detail = {};
	
	$scope.account_init = function()
	{
		debugger
		$http.get(pathingService.getCurrentPath('user/get-user')).then(function(user)
		{
			$scope.user_detail = user.data;
			$scope.overlay_off = true;
		});
	}
	
	$scope.hide_update = true;
	
	// Update User Profile
	$scope.modify_profile = function() {
		$("#recStrength").html($scope.rec_strength["text"]);
		$scope.hide_update = false;
		var req = $http.post(
				(pathingService.getCurrentPath('user/update-account')), $scope.rec_strength)
				req.success(function(data)
				{
						$scope.hide_update = true; 		
				}
		);
	}
	
	
	
}]);