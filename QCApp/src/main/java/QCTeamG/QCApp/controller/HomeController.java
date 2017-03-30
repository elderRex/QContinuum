package QCTeamG.QCApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserRolesEntity;
import QCTeamG.QCApp.entities.UsersEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Autowired
	UsersDAO userDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String redirect_home() {
		return "home";
	}
	
	@RequestMapping(value = "user/setup", method = RequestMethod.GET)
	public String setup() {
		
		return "setup";
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getQuestions() {
		
		return "setup";
	}
	
	@Transactional
	@RequestMapping(value="/user/get-questions",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public @ResponseBody String getUserSetupQuestions(Principal principal, HttpServletRequest request) {
		
		String prin = principal.getName();		
		UsersEntity ue = userDAO.getCurrentUser(prin);
		
		List<ReviewsEntity> user_questions = userDAO.getUserQuestions(ue.getId());
		
		Gson gson = new Gson();
		 
		List<String> ls = new ArrayList<String>();
	
		for (ReviewsEntity t : user_questions) {
			String json = gson.toJson(t);
	        ls.add(json);
		}
	
		return ls.toString();
		
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(Principal principal) {
		
		String prin = principal.getName();		
		UsersEntity ue = userDAO.getCurrentUser(prin);
        if (ue == null)
        {
        		return "home";
        }
        else if (ue.getAccountActive() == true)
        {
        		return "user";
        }
		
		return "setup";
	}
	
	public String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

		System.out.println(hashedPassword);
		return hashedPassword;
	}
	
	
	@RequestMapping(value = "/new-user/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody String uinfo, HttpServletRequest request) {
		
		try
		{
			JSONObject ilogin = new JSONObject(uinfo);
			String hashPassword = getHashPassword(ilogin.getString("password"));
			Login ue = new Login();
			ue.setEmail(ilogin.getString("email"));
			ue.setFirstname(ilogin.getString("firstname"));
			ue.setLastname(ilogin.getString("lastname"));
			
			ue.setPassword(hashPassword);
			Registration(ue);
			String email = ue.getEmail();		
			UsersEntity us = userDAO.getCurrentUser(email);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(us, null,
				    AuthorityUtils.createAuthorityList("ROLE_USER"));
				SecurityContextHolder.getContext().setAuthentication(authentication);
		
		}
		catch (Exception e)
		{
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
		
		@Transactional
		public void Registration(Login login)
		{
			UsersEntity ue = new UsersEntity();
			ue.setEmail(login.getEmail());
			ue.setFirstname(login.getFirstname());
			ue.setLastname(login.getLastname());
			ue.setPassword(login.getPassword());
			UserRolesEntity ure = new UserRolesEntity();
			ure.setEmail(login.getEmail());
			ure.setRole("ROLE_USER");
			ue.setEnabled(true);
			
			Integer uid = userDAO.createUser(ue);
			userDAO.setUserRole(ure);
			 
		}
	
	
	
	@Transactional
	@RequestMapping(value = "get_all_user_data", method = RequestMethod.GET,produces={"application/xml", "application/json"})
	public @ResponseBody String getAllUserData(Locale locale, Model model) {
		
		List<UsersEntity> all_user_data = (List<UsersEntity>)userDAO.getAllUsers();
		Gson gson = new Gson();
		String user_data_list = gson.toJson(all_user_data);
		
//		for (UsersEntity ue : all_user_data) {
//			//List<UserReviewsList> revs =
//			//String user_reviews_list = gson.toJson(luse);
//			
//			JSONObject jo = new JSONObject();
//			jo.put("all_reviews", reviews_list);
//
//			ls.add(jo.toString());
//		}

		return user_data_list.toString();
	

	}
}