package QCTeamG.QCApp.controller;

import java.io.IOException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import QCTeamG.QCApp.dao.ItemsDAO;
import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UserFavoritesEntity;
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
	
	@Autowired
	ItemsDAO itemsDAO;

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
	
	@RequestMapping(value = "user/favorites", method = RequestMethod.GET)
	public String favorites(HttpServletRequest request) {
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		sco.setSession(request);
		return "favorite";
	}
	
	@Transactional
	@RequestMapping(value="/user/get-questions",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
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
		// Data was inserted incorrectly into db, limited time to fix, so compensate for missing characters
		// with by far the most likely culprit,the accent aigu.
		String res = ls.toString();
		String clean = res.replaceAll("�","é");
		return clean;
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
	
	@RequestMapping(value="/user/get-user",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public @ResponseBody String getUser(Principal principal, HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		SessionController sco = new SessionController();
		beanFactory.autowireBean(sco);
		Integer uid = sco.getSessionUserId(principal);
		UsersEntity ue = userDAO.getUserById(uid);
		String json = new Gson().toJson(ue);
		return json.toString();
		
	}
	
	List<String> user_favs = new ArrayList<String>();
	
	@RequestMapping(value="/user/get-user-favorites",method=RequestMethod.GET,produces={"text/html;charset=UTF-8"})
	public @ResponseBody String getUserFavoritItems(Principal principal, HttpServletRequest request) {
		
		try
		{
			HttpSession session = request.getSession(true);
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			List<UserFavoritesEntity> favs = reviewsDAO.getUserFavorites(uid);
			
			Gson gson = new Gson();
			 
			List<String> ls = new ArrayList<String>();
		
			for (UserFavoritesEntity f : favs) {
				String json = gson.toJson(f.getItem());
		        ls.add(json);
			}
			
			String clean = ls.toString().replaceAll("�","é");
			return clean;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	@RequestMapping(value="/user/get-user-favs",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public @ResponseBody String getUserFavs(Principal principal, HttpServletRequest request) {
		
		try
		{
			HttpSession session = request.getSession(true);
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			List<UserFavoritesEntity> favs = reviewsDAO.getUserFavorites(uid);
			
			Gson gson = new Gson();
			 
			List<String> ls = new ArrayList<String>();
		
			for (UserFavoritesEntity f : favs) {
				String json = gson.toJson(f.getItem().getId());
		        ls.add(json);
			}
			
			String json = new Gson().toJson(ls);
			return json.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	// Redirection depending on user authentication level
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(Principal principal, HttpServletRequest request) {
		
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
		@RequestMapping(value = "/user/model-recommendations", method = RequestMethod.POST, headers = {"Content-type=application/json"})
		public @ResponseBody String generateRecommendations(@RequestBody String model_selections, HttpServletRequest request, Principal principal) throws IOException {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Gson gson = new Gson();
			
			// Forced to do this parse because some people can't be bothered JSONifying output
			String mod = model_selections.substring(1, model_selections.length() - 1);
			
			
			JSONArray all_selections = new JSONArray(model_selections);
			
			List<ItemsEntity> user_recommendations = new ArrayList<ItemsEntity>();
			
			List<List<String>> ls = new ArrayList<List<String>>();
			
			Session sesh = sessionFactory.getCurrentSession();
			
			List<Integer> idsList = new ArrayList<Integer>();
			
			for (int i = 0; i < all_selections.length(); i++)
			{
				try
				{
					// Account for non-JSONified model output
					JSONArray arr = all_selections.getJSONArray(i);
					
					// Get ID
					Integer iid = arr.getInt(1);
					// True or false in terms if match
					Integer isMatch = Integer.parseInt(arr.getString(0).substring(1,2));
					if (isMatch== 1)
					{
						idsList.add(iid);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			List<ItemsEntity> lie = userDAO.getSpecificItemsById(idsList,sesh);
			try
			{
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "";
			}
			
			

			// Data was inserted incorrectly into db, limited time to fix, so compensate for missing characters
			// with by far the most likely culprit,the accent aigu.
			String res = ls.toString();
			String clean = res.replaceAll("�","é");
			return clean;
		}
		
		
		@RequestMapping(value = "/user/update-account", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
		public @ResponseBody String updateProfile(@RequestBody String rec_setting, HttpServletRequest request, Principal principal)
		{
			
			JSONObject json = new JSONObject(rec_setting);
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			ue.setRecStrength(json.getInt("value"));
			
			userDAO.modifyUserProfile(ue);
			return "";
		}
		
		
		@Transactional
		@RequestMapping(value = "/user/create-favorite", method = RequestMethod.POST)
		@ResponseBody
		public String createUserFavorite(@RequestBody String iid, HttpServletRequest request, Principal principal) {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Session sesh = sessionFactory.getCurrentSession();
			
			UserFavoritesEntity ufe = new UserFavoritesEntity();
			ItemsEntity ie = (ItemsEntity)itemsDAO.getItemById(Integer.parseInt(iid));
			ufe.setItem(ie);
			ufe.setUser(ue);
			
			reviewsDAO.createUserFavorite(ufe, sesh);
			return "";
		}
		
		@Transactional
		@RequestMapping(value = "/user/delete-favorite", method = RequestMethod.POST)
		@ResponseBody
		public String deleteUserFavorite(@RequestBody String ufid, HttpServletRequest request, Principal principal) {
			
			SessionController sco = new SessionController();
			beanFactory.autowireBean(sco);
			Integer uid = sco.getSessionUserId(principal);
			UsersEntity ue = userDAO.getUserById(uid);
			
			Session sesh = sessionFactory.getCurrentSession();
			UserFavoritesEntity uf = reviewsDAO.getSpecificUserFavorite(Integer.parseInt(ufid));
			reviewsDAO.removeUserFavorite(uf, sesh);
			return "";
		}
		
		@Transactional
		@RequestMapping(value="/user/get-all-favorites",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
		public @ResponseBody String getUserFavorites(Principal principal, HttpServletRequest request) {
			
			try
			{
				HttpSession session = request.getSession(true);
				
				SessionController sco = new SessionController();
				beanFactory.autowireBean(sco);
				Integer uid = sco.getSessionUserId(principal);
				
				List<UserFavoritesEntity> user_favs = reviewsDAO.getUserFavorites(uid);
				
				Gson gson = new Gson();
				 
				List<String> ls = new ArrayList<String>();
			
				for (UserFavoritesEntity uf : user_favs) {
					String json = gson.toJson(uf);
			        ls.add(json);
				}
				// Data was inserted incorrectly into db, limited time to fix, so compensate for missing characters
				// with by far the most likely culprit,the accent aigu.
				
				String res = ls.toString();
				String clean = res.replaceAll("�","é");
				return clean;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return "";
		}
		
	
}