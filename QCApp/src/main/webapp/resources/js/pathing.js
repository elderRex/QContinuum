var pathing_controller = angular.module('pathing_controller', []);

pathing_controller.service('pathingService', function($location) {
	
	return {
		
		getCurrentPath: function(request)
		{
			var url = request;
			if ($location.host() == "localhost")
    			{
    				url = "qc/" + request;
    			}
			
			return "/" + url;
		}
	}
});