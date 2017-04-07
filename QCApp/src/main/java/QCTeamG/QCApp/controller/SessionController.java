package QCTeamG.QCApp.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.UsersEntity;

@Controller
public class SessionController {
	
	@Autowired
	UsersDAO userDAO;
	
	public void setSession(HttpServletRequest httpServletRequest) {
		
		try {

			HttpSession session = httpServletRequest.getSession();
	        UsersEntity ue = (UsersEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			session.setAttribute("userProfile", ue);
		}
		catch (Exception e)
		{
			
			HttpSession session = httpServletRequest.getSession();
	        User cu = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        UsersEntity ue = userDAO.getUserByUsername(cu.getUsername());
			session.setAttribute("userProfile", ue);
		}
	}
	
	public Integer getSessionUserId(Principal principal) {
		if(principal == null) return null;
		Integer uid = 0;
		try 
		{
			String lo = principal.getName();		
			UsersEntity us = userDAO.getCurrentUser(lo);
			uid = us.getId();
		}
		catch (Exception e)
		{
			UsersEntity ue = (UsersEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			uid = ue.getId();
		}
		return uid;
	}
	
	
}
