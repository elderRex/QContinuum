package QCTeamG.QCApp.controller;
//package QCTeamG.QCApp.entities.UsersEntity;

import QCTeamG.QCApp.entities.UsersEntity;
import QCTeamG.QCApp.dao.UsersDAO;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.*;

import org.junit.Test;

public class userSetupQuestionTest {
       private Principal principal;
       UsersDAO userDAO;
       
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              //Authentication authentication = mock(Authentication.class);
              String email = "firstlast@columbia.edu";
              UsersEntity us = userDAO.getCurrentUser(email);	
              Authentication authentication = new UsernamePasswordAuthenticationToken(us, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
              SecurityContextHolder.getContext().setAuthentication(authentication);
              //principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              principal = request.getUserPrincipal();
              System.out.println(principal);
              HomeController hc = new HomeController();
              assertNotNull(hc.getUserSetupQuestions(principal, request));
              
       
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
