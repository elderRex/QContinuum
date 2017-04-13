package QCTeamG.QCApp.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ResetPasswordEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UserAnswersEntity;
import QCTeamG.QCApp.entities.UserRolesEntity;
import QCTeamG.QCApp.entities.UsersEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Registration Controller manages user registration and authentication
 */
@Controller
@SessionAttributes("tempCode")
public class RegistrationController {
	
	
	@Autowired
	UsersDAO userDAO;
	
	@Autowired
	ReviewsDAO reviewsDAO;
	
	int ONE_DAY = 86400000;
	
	public String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	/*
	 * Password Changes
	 */
	
	// Resets the password after link is clicked, and new password is input
	@RequestMapping(value = "change-password", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	@Transactional
	public ResponseEntity<String> changePassword(@ModelAttribute("tempCode") String token, @RequestBody String data, 
			HttpServletResponse response, Principal principal) throws MessagingException, IOException {		
		
		JSONObject jObject  = new JSONObject(data);
		String email = jObject.getString("email");
		String new_password = jObject.getString("new-password");
		String new_password_confirmation = jObject.getString("new-password-confirmation");
		
		Boolean success = true;
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("tempCode", token);
		mv.setViewName("home");
		
		String reset_token = token;
		ResetPasswordEntity pre = userDAO.getResetByToken(reset_token);
		
		Timestamp day_later = pre.getTime();
		
		ResponseEntity<String> res = new ResponseEntity<String>(HttpStatus.OK);
		
		day_later.setTime(day_later.getTime() + ONE_DAY);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (now.after(day_later) || (!pre.getUserEmail().equals(email)) || 
				(!pre.getRandomKey().equals(token)) || pre.getWasReset())
		{
			mv.setViewName("password_reset");
			mv.addObject("message", "This link isn't active");
			success = false;
			res = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}	
		else	
		{
			try {
						
				UsersEntity us = userDAO.getUserByEmail(email);
				
				String hashPassword = getHashPassword(new_password);
			    userDAO.updateTimestamp(token);
				userDAO.changePassword(email, hashPassword);
				
				ApplicationContext appContext = new ClassPathXmlApplicationContext("Spring-Mail.xml");
				
				UserMail mm = (UserMail)appContext.getBean("userMail");
				mm.informUserPWChange(email,us.getFirstname(),us.getLastname());
			}
			catch (Exception e)
			{
				 System.out.println(e);
			}
		}
		
	return res;
		
	}
	
	/*
	 * New User Registration
	 */
	
	@RequestMapping(value = "/new-user/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestBody String uinfo, HttpServletRequest request) {
		
		try
		{
			JSONObject ilogin = new JSONObject(uinfo);
			String hashPassword = getHashPassword(ilogin.getString("password1"));
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
			ue.setRecStrength(0);
			ue.setPassword(login.getPassword());
			UserRolesEntity ure = new UserRolesEntity();
			ure.setEmail(login.getEmail());
			ure.setRole("ROLE_USER");
			ue.setEnabled(true);
			
			Integer uid = userDAO.createUser(ue);
			userDAO.setUserRole(ure);
			 
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