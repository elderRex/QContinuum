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
       
       /*
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              
              HomeController hc = new HomeController();
              assertNull(hc.getUserSetupQuestions(null, request));
              
       
       }*/
       
       
       @Test
       public void testFailedQuestion() {
               HttpServletRequest request = mock(HttpServletRequest.class);
              
               UserController uc = new UserController();
               assertNull(uc.getUserSetupQuestions(null, request));
              
       
       }
       
}
