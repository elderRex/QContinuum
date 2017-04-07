package QCTeamG.QCApp.controller;
//package QCTeamG.QCApp.entities.UsersEntity;

import QCTeamG.QCApp.entities.UsersEntity;
import QCTeamG.QCApp.dao.UsersDAO;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.*;

import org.junit.Test;

public class userSetupQuestionTest {
       //private Principal principal;
       UsersDAO userDAO;
       
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              //Authentication authentication = mock(Authentication.class);
              //MockHttpServletRequest request = new MockHttpServletRequest();
              String email = "test1@gmail.com";
              int id = 0;
              String firstname = "test1";
              String lastname = "test1";
              String password = "123456";
              boolean enabled = true;
              boolean account_active = true;
              //UsersEntity us = userDAO.getCurrentUser(email);
              UsersEntity us = new UsersEntity();
              us.setEmail(email);
              us.setFirstname(firstname);
              us.setLastname(lastname);
              us.setId(id);
              us.setPassword(password);
              us.setEnabled(enabled);
              us.setAccountActive(account_active);
              
              Authentication authentication = new UsernamePasswordAuthenticationToken(us, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
              SecurityContextHolder.getContext().setAuthentication(authentication);
              //if(!authentication.isAuthenticated()) System.out.println("haven't been authenticated !!!!!!!!!!!");
              //else System.out.println("authenticated successfully !!!!!!!!!!!");
              //principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              //Principal principal = request.getUserPrincipal();
              //Principal principal = (Principal)authentication.getName();
              //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              //System.out.println(principal.toString());
              //Principal p = (Principal) principal;
              HomeController hc = new HomeController();
              assertNotNull(hc.getUserSetupQuestions(null, request));
              
       
       }
       /*
       @Test
       public void testFailedQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              Authentication authentication = mock(Authentication.class);
              SecurityContext securityContext = mock(SecurityContext.class);
              Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
              SecurityContextHolder.setContext(securityContext);
              principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              HomeController hc = new HomeController();
              System.out.println(principal);
              assertNull(hc.getUserSetupQuestions(principal, request));
              
       
       }
       */
}
