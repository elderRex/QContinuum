package QCTeamG.QCApp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import QCTeamG.QCApp.dao.UsersDAO;
 
public class LogoutHandler extends SimpleUrlLogoutSuccessHandler {

	   @Autowired
	   UsersDAO userDAO;
	   
	   boolean condition = true;
	
	   // Just for setting the default target URL
	   public LogoutHandler(String defaultTargetURL) {
	        this.setDefaultTargetUrl(defaultTargetURL);
	   }

	   @Override
	   public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		   try{	
		   super.onLogoutSuccess(request, response, authentication);
		   condition = false;
		   } catch (Exception e){
			   //e.printStackTrace();
			   condition = true;
		   }
	   }
	
	   public boolean getCondition(){
		   return condition;
	   }
	}
