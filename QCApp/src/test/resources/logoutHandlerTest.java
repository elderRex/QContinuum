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

public class logoutHandlerTest extends SimpleUrlLogoutSuccessHandler {
       
       @Autowired
	     UsersDAO userDAO;
       
       @Test
       public void testSuccessfulLogout() {
              String defaultTargetURL = "test";
              LogoutHandler(String defaultTargetURL);
              assertEquals(this.getDefaultTargetUrl(), "test");
       }
}
