package QCTeamG.QCApp.controller;


import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.sql.Timestamp;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ResetPasswordEntity;
import QCTeamG.QCApp.entities.UsersEntity;

/**
 * Home Controller controls logged out functionality
 */
@Controller
public class HomeController {
	
	@Autowired
	UsersDAO userDAO;

	/**
	 * Redirects users to home for authentication purposes
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String redirect_home() {
		return "home";
	}
	
	@RequestMapping(value = "errors/authfailed", method = RequestMethod.GET)
	public ModelAndView authfailed() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("auth_failed", "Login failed, please make sure the email and password are correct");
		mav.setViewName("home");
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "home";
	}
	
	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getQuestions() {
		
		return "setup";
	}
	
	@RequestMapping(value="errors/not-found")
	public String Error400()
	{
		return "error_404";
	}
	
	@RequestMapping(value="errors/something-went-wrong", method=RequestMethod.GET)
	public String Error500()
	{
		return "error_500";
	}
	
	@RequestMapping(value="password-reset", method=RequestMethod.GET)
	public String ForgotPassword()
	{
		return "password_reset";
	}
	
	@RequestMapping(value="password-reset/{token}", method=RequestMethod.GET)
	public ModelAndView viewResetPassword(@PathVariable String token)
	{
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("tempCode", token);
		mav.setViewName("new_password");
		return mav;
		
	}
	
	// Sends a password reset to the user
	@RequestMapping(value = "send-password-reset", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	@Transactional
	@ResponseBody 
	public ResponseEntity<String> generateResetPassword(@RequestBody String data,HttpServletRequest request) throws MessagingException, IOException {
		
		ApplicationContext appContext = null;
		
		try
		{
			appContext = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		}
		catch (Exception e)
		{
			int x = 2;
		}
		
		JSONObject jObject  = new JSONObject(data);
		String email = jObject.getString("email");
		UsersEntity us = userDAO.getUserByEmail(email);
		
		ResponseEntity<String> res = new ResponseEntity<String>(HttpStatus.ACCEPTED);
		
		// If user doesn't exist
		if (us == null)
		{
			res = new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
		}
		else
		{
			// Create new time stamp record to ensure 24 hours password change
			Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
			
			ResetPasswordEntity pre = new ResetPasswordEntity();
			
			SecureRandom random = new SecureRandom();
			String token = new BigInteger(130, random).toString(32);
			
			pre.setRandomKey(token);
			pre.setWasReset(false);
			pre.setTime(time_stamp);
			pre.setUserEmail(email);
			pre.setUId(us.getId());
			
			String authentication_token = "http://sample-env-1.jevngzemth.us-west-2.elasticbeanstalk.com/password-reset/" + token;
			String req = request.getRequestURL().toString();
			
			if (req.toLowerCase().contains("localhost".toLowerCase()))
			{
				authentication_token = "http://localhost:8080/qc/password-reset/" + token;
			}
			
			userDAO.createNewTimestamp(time_stamp, pre);
			UserMail um = (UserMail)appContext.getBean("userMail");
			um.changePasswordRequest(email,us.getFirstname(),us.getLastname(), authentication_token);
		
		}
		
		return res;
	}
	
}
