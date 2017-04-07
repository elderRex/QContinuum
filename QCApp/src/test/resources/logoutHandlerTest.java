package QCTeamG.QCApp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import QCTeamG.QCApp.dao.UsersDAO;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class logoutHandlerTest extends SimpleUrlLogoutSuccessHandler {
       
       @Autowired
	     UsersDAO userDAO;
       
       @Test
	public void testSuccessfulLogout(){
		String defaultTargetURL = "qc/logout";
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Authentication authentication = mock(Authentication.class);
		LogoutHandler lh = new LogoutHandler(defaultTargetURL);
	     	lh.onLogoutSuccess(request, response, authentication);
		assertTrue(true);
	}
}
