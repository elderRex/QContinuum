describe（'homeController', function(){

  beforeEach(module('home'));
  var $controller;
  
  beforeEach(inject(function(_$controller_){
    $controller = _$controller_;
 }));
    
describe('$register_user', function() {

  it('direct to the setup page', function(){
    var $scope = {};
    var controller = $controller("homeController",{$scope, $scope});
    $scope.registration.firstname = "a";
    $scope.registration.lasttname = "b";
    $scope.registration.email ="abc@gmail.com";
    $scope.registration.password1 = "abc";
    $scope.registration.password2 = "abc";
    $scope.register_user();
    var d = window.location.href;
    expect(d).toBe(pathingService.getCurrentPath('user/setup'));
    });
   });
});
