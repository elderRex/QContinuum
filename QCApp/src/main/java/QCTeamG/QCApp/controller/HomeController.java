package QCTeamG.QCApp.controller;


import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home Controller the home page and access to user setup page
 */
@Controller
public class HomeController {

	/**
	 * Redirects users to home for authentication purposes
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String redirect_home() {
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
	
}
