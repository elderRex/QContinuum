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
	
}
