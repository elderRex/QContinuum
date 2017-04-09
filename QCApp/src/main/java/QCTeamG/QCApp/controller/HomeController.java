package QCTeamG.QCApp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
}
