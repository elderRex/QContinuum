<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Registration</title>
<div class="centered-box w-25">
	<div class="error-message" ng-bind='msg' ng-hide='hide'></div>
	<div class="title">Create Account</div>
	<form >
	    <div class="row">
  			<div class="col-xs-6">
		    		<label for="inputEmail">First name</label>
		    		<input type="text" name="firstname" id="inputFirst" ng-model="registration.firstname" placeholder="First name" required autofocus />
	    		</div>
		    <div class="col-xs-6">	
		    		<label for="inputEmail">Last name</label>
		    		<input type="text" name="lastname" id="inputLast" ng-model="registration.lastname" placeholder="Last name" required autofocus />
		    	</div>
    		</div>
	    <div class="row">
	    		<div class="col-xs-6">	
			    	<label class="row-title" for="inputEmail">Email address</label>
	    		</div>
	    		<div class="col-xs-6">	
			    	<input type="email" id="inputEmail" name="email" ng-model="registration.email" placeholder="Email address" required autofocus>
	    		</div>
	    </div>
	    <div class="row">
	    		<div class="col-xs-6">	
			    	<label for="inputPassword" >Password</label>
	    		</div>
	    		<div class="col-xs-6">
	    			<input type="password" id="inputPassword" name="password1" ng-model="registration.password1" placeholder="Password" required>
	    		</div>
	    </div>
	    <div class="row">
	    		<div class="col-xs-6">	
			      <label for="inputPassword2" >Confirm Password</label>
	    		</div>
	    		<div class="col-xs-6">
	    			 <input type="password" id="inputPassword" name="password2" ng-model="registration.password2" placeholder="Password Confirmation" required>
	    		</div>
	    </div>
	    <div class="row">
	    		<div style="text-align: center; margin-top: 15px">	
	    			<button ng-click="register_user()">Get Started</button>
	    		</div>
	    	</div>
	</form>
</div>