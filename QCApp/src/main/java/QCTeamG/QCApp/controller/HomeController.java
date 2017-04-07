package QCTeamG.QCApp.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UserRolesEntity;
import QCTeamG.QCApp.entities.UsersEntity;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
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
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

/**
 * Home Controller manages user sign in, authentication, and account data rendering
 */
@Controller
public class HomeController {
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Autowired
	UsersDAO userDAO;
	
	@Autowired
	ReviewsDAO reviewsDAO;

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
		
		HttpSession session = request.getSession(true);
		
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		Integer uid = sco.getSessionUserId(principal);
		
		List<ReviewsEntity> user_questions = userDAO.getUserQuestions(uid);
		
		Gson gson = new Gson();
		 
		List<String> ls = new ArrayList<String>();
	
		for (ReviewsEntity t : user_questions) {
			String json = gson.toJson(t);
	        ls.add(json);
		}
	
		return ls.toString();
		
	}
	
	// Get The uid of the current user
	@Transactional
	@RequestMapping(value="/user/get-uid",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public @ResponseBody String getUserId(Principal principal, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		Integer uid = sco.getSessionUserId(principal);
		
		return uid.toString();
		
	}
	
	// Redirection depending on user authentication level
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(Principal principal) {
		
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		Integer uid = sco.getSessionUserId(principal);
		
		// If user isn't logged in, redirect to home
        if (uid == null)
        {
        		return "home";
        }
        
        // Check if user's account has been activated or not
        UsersEntity ue = userDAO.getUserById(uid);
        
        if (ue.getAccountActive() == true)
        {
        		return "user";
        }
		
        // If account isn't active, redirect to user account setup
		return "setup";
	}
	
	public String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@RequestMapping(value = "/new-user/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody String uinfo, HttpServletRequest request) {
		
		try
		{
			JSONObject ilogin = new JSONObject(uinfo);
			String hashPassword = getHashPassword(ilogin.getString("password1"));
			//if(hashPassword.length() == 0)System.out.println("failure to get password"); // 
			Login ue = new Login();
			ue.setEmail(ilogin.getString("email"));
			//if(ue.getEmail() == null) System.out.println("failure to get email"); // 
			ue.setFirstname(ilogin.getString("firstname"));
			//if(ue.getFirstname() == null) System.out.println("failure to get firstname"); //
			ue.setLastname(ilogin.getString("lastname"));
			//if(ue.getLastname() == null) System.out.println("failure to get lastname"); //
			
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
			System.out.println(uid);
			userDAO.setUserRole(ure);
			 
		}
		
		@RequestMapping(value = "/user/answer_questions", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<String> createUserReviews(@RequestBody String reviews, HttpServletRequest request, Principal principal) {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			JSONArray all_reviews = new JSONArray(reviews);
			ue.setAccountActive(true);
			userDAO.activateUser(ue);
			
			try
			{
				
				for (int i = 0; i < all_reviews.length(); i++)
				{
					UserAnswersEntity uae = new UserAnswersEntity();
					ReviewsEntity re = reviewsDAO.getReviewById(all_reviews.getJSONObject(i).getInt("id"));
					uae.setUid(ue);
					uae.setRId(re);
					uae.setLiked(all_reviews.getJSONObject(i).getBoolean("liked"));
					reviewsDAO.createUserAnswer(uae);
				}
			}
			catch (Exception e)
			{
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		@RequestMapping(value = "/user/model-recommendations", method = RequestMethod.POST, headers = {"Content-type=application/json"})
		public @ResponseBody String generateRecommendations(@RequestBody String model_selections, HttpServletRequest request, Principal principal) throws IOException {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Gson gson = new Gson();
			JSONArray all_selections = new JSONArray(model_selections);
			
			List<ItemsEntity> user_recommendations = new ArrayList<ItemsEntity>();
			
			List<List<String>> ls = new ArrayList<List<String>>();
			
			for (int i = 0; i < all_selections.length(); i++)
			{
				JSONArray selection = all_selections.getJSONArray(i);
				if (selection.getInt(0) == 1)
				{
					ItemsEntity ie = userDAO.getItemById(selection.getInt(1));
	
					List<ReviewsEntity> lre = reviewsDAO.getReviewsByItem(ie.getId());
					List<String> reviews_str = new ArrayList<String>();
					for (ReviewsEntity re : lre)
					{
						String review = gson.toJson(re);
						reviews_str.add(review);
					}
					String json = new Gson().toJson(ie);
					List<String> combined_item = new ArrayList<String>();
			        combined_item.add(json);
					combined_item.add(reviews_str.toString());
					ls.add(combined_item);
				}
			}

			return ls.toString();
			
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
