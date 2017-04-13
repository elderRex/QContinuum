var app = angular.module('app', ['pathing_controller']);

app.controller('loginController', ['$scope', '$http','$location','pathingService', function($scope, $http, $location,pathingService) {	

	$scope.data = {};
	
	$scope.overlay_off = true;
	
	/*
	 * Password Change Functionality
	 */
	
	$scope.update_password = function()
	{
		$scope.password_email = $("#password-email").val();
		$scope.password = $("#new-password").val();
		$scope.password_confirmation = $("#new-password-confirmation").val();
		debugger
		$http({
	        method: 'POST',
	        url: pathingService.getCurrentPath('change-password'),
	        contentType: "application/json",
	        data: {
	        			'email' : $scope.password_email,
	        			'new-password' : $scope.password,
	        			'new-password-confirmation' : $scope.password_confirmation
	        		}
	    }).success(function(data, status, headers, config) {
	    		$scope.password_link_sent = true;
	    		$window.location.href = pathingService.getCurrentPath("home");
	    	
	    }).error(function(data, status, headers, config) {
	    		$scope.password_change_error = true;
	    });
	};
	
	$scope.init_pw_change = function()
	{
		$scope.password_change_error = false;
	}
	
	$scope.view_reset_password = function()
	{
		$scope.password_link_sent = false;
		$scope.email_to_reset = $("#lost_email").val();
		$http({
	        method: 'POST',
	        url: pathingService.getCurrentPath('send-password-reset'),
	        contentType: "application/json",
	        data: {
	        			'email' : $scope.email_to_reset	        			
	        		}
	    }).success(function(data, status, headers, config) {
	    		$scope.password_link_sent = true;
	    }).error(function(data,status)
	    		{
	    			$scope.no_email_found = true;
	    		})
        
		
	};
	
	$scope.change_password = function()
	{
		window.location.href = pathingService.getCurrentPath("password-reset");
	}
	
	$scope.input_new_password = function()
	{
		$scope.password_link_sent = false;
		$scope.email_to_reset = $("#lost_email").val();
		$http({
	        method: 'POST',
	        url: pathingService.getCurrentPath('reset-password'),
	        contentType: "application/json",
	        data: {
	        			'email' : $scope.email_to_reset	        			
	        		}
	    }).success(function(data, status, headers, config) {
	    		$scope.password_link_sent = true;
	    }).error(function(data,status)
	    		{
	    			$scope.no_email_found = true;
	    		})
        
		
	};
	
	$scope.logout_user = function()
	{
		window.location.href = pathingService.getCurrentPath("/logout");
	}
	
	// Register New User (Customer) (Just Email)
	$scope.register_user = function() {
		if ($scope.registration 
				&& $scope.registration.firstname
				&& $scope.registration.lastname
				&& $scope.registration.email
				&& $scope.registration.password1 == $scope.registration.password2)
		{
			$scope.overlay_off = false;
			$http.post(pathingService.getCurrentPath('new-user/register'), $scope.registration).then(function(data) {
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