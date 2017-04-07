var app = angular.module('home', ['pathing_controller']);

app.controller('homeController', ['$scope', '$http','$location','pathingService', function($scope, $http, $location,pathingService) {	

	$scope.data = {};
	
	$scope.logout_user = function()
	{
		window.location.href = pathingService.getCurrentPath("logout");
	}
	
	// Register New User (Customer) (Just Email)
	$scope.register_user = function() {
		if ($scope.registration 
				&& $scope.registration.firstname
				&& $scope.registration.lastname
				&& $scope.registration.email
				&& $scope.registration.password1 == $scope.registration.password2)
		{
			$http.post(pathingService.getCurrentPath('new-user/register'), $scope.registration).then(function(data) {
					debugger;
				window.location.href = pathingService.getCurrentPath('user/setup');
			}, function errorCallback(response) {
				// TODO Error on callback
			  });
		}
		else
		{
			//$( "#warning-more-information" ).dialog( "open" );
		}
	}
	
}]);