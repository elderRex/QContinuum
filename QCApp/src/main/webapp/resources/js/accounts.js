var app = angular.module('app', ['pathing_controller']);

// Update User Account
app.controller('accountsController', ['$scope', '$http','$location','pathingService', function($scope, $http, $location,pathingService) {	

	
	$scope.user_profile = {};
	
	// Update User Profile
	$scope.modify_profile = function() {
		var req = $http.post(
				(pathingService.getCurrentPath('user/update-account')), $scope.user_profile)
				req.success(function(data)
				{
					window.location.href = pathingService.getCurrentPath("user/account");
				}
		);
	}
	
	
	
}]);