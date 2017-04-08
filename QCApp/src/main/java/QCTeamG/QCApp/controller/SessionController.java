package QCTeamG.QCApp.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.UsersEntity;

@Controller
@SessionAttributes("userEmail")
public class SessionController {
	
	@Autowired
	UsersDAO userDAO;
	
	@Transactional
	public void setSession(HttpServletRequest httpServletRequest) {
		
		HttpSession session = httpServletRequest.getSession();
		UsersEntity ue = null;
		
		try {

	        ue = (UsersEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			session.setAttribute("userEmail", ue);
		}
		catch (Exception e)
		{
	        User cu = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String email = cu.getUsername();
	        ue = userDAO.getUserByEmail(email);
		}
		session.setAttribute("userEmail", ue);
	}
	
	public Integer getSessionUserId(Principal principal) {
		//if(principal == null) return null;
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
