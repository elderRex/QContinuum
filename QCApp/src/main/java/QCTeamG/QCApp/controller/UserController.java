package QCTeamG.QCApp.controller;

import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UsersEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * User Controller manages account data rendering
 */
@Controller
@SessionAttributes("userEmail")
public class UserController {
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	UsersDAO userDAO;
	
	@Autowired
	ReviewsDAO reviewsDAO;

	@ModelAttribute("userEmail")
	@RequestMapping(value = "user/setup", method = RequestMethod.GET)
	public ModelAndView setup(HttpServletRequest request) {
		
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		sco.setSession(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("setup");
		return mav;
	}
	
	@RequestMapping(value = "user/account", method = RequestMethod.GET)
	public String account(HttpServletRequest request) {
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		sco.setSession(request);
		return "account";
	}
	
	@Transactional
	@RequestMapping(value="/user/get-questions",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
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
	//@Transactional
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
	public String user(Principal principal, HttpServletRequest request) {
		
//		List<Integer> idsList = new ArrayList<Integer>(Arrays.asList(4528,4647,8416,9018));
//		
//		Session sesh = sessionFactory.getCurrentSession();
//		
//		userDAO.getSpecificItemsById(idsList, sesh);
		
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		sco.setSession(request);
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
		@Transactional
		@RequestMapping(value = "/user/answer_questions", method = RequestMethod.POST)
		@ResponseBody
		public ResponseEntity<String> createUserReviews(@RequestBody String reviews, HttpServletRequest request, Principal principal) {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Session sesh = sessionFactory.getCurrentSession();
			
			JSONArray all_reviews = new JSONArray(reviews);
			ue.setAccountActive(true);
			userDAO.activateUser(ue,sesh);
			
			try
			{
				
				for (int i = 0; i < all_reviews.length(); i++)
				{
					UserAnswersEntity uae = new UserAnswersEntity();
					ReviewsEntity re = reviewsDAO.getReviewById(all_reviews.getJSONObject(i).getInt("id"),sesh);
					uae.setUid(ue);
					uae.setRId(re);
					uae.setLiked(all_reviews.getJSONObject(i).getBoolean("liked"));
					reviewsDAO.createUserAnswer(uae,sesh);
				}
			}
			catch (Exception e)
			{
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		@Transactional
		@RequestMapping(value = "/user/model-recommendations", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
		public @ResponseBody String generateRecommendations(@RequestBody String model_selections, HttpServletRequest request, Principal principal) throws IOException {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Gson gson = new Gson();
			JSONArray all_selections = new JSONArray(model_selections);
			
			List<ItemsEntity> user_recommendations = new ArrayList<ItemsEntity>();
			
			List<List<String>> ls = new ArrayList<List<String>>();
			
			Session sesh = sessionFactory.getCurrentSession();
			
			List<Integer> idsList = new ArrayList<Integer>();
			
			for (int i = 0; i < all_selections.length(); i++)
			{
				JSONArray arr = all_selections.getJSONArray(i);
				if (arr.getInt(0) == 1)
				{
					idsList.add(arr.getInt(1));
				}
			}

			List<ItemsEntity> lie = userDAO.getSpecificItemsById(idsList,sesh);

			for (ItemsEntity ie : lie)
			{
				List<ReviewsEntity> lre = reviewsDAO.getReviewsByItem(ie.getId(), sesh);
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

			return ls.toString();
			
		}
		
		@RequestMapping(value = "/user/update-account", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
		public void updateProfile(@RequestBody String rec_setting, HttpServletRequest request, Principal principal) throws IOException {
			
			JSONObject json = new JSONObject(rec_setting);
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			ue.setRecStrength(json.getInt("value"));
			
			userDAO.modifyUserProfile(ue);
			
		}
		
	
}