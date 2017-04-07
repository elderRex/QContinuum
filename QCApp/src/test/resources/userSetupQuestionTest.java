package QCTeamG.QCApp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.*;

import org.junit.Test;

public class userSetupQuestionTest {
       private Principal principal;
       
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              Authentication authentication = Mockito.mock(Authentication.class);
              SecurityContext securityContext = Mockito.mock(SecurityContext.class);
              Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
              SecurityContextHolder.setContext(securityContext);
              principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              HomeController hc = new HomeController();
              System.out.println(principal);
              assertNotNull(hc.getUserSetupQuestions(principal, request));
              
       
       }
       
       @Test
       public void testFailedQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              Authentication authentication = Mockito.mock(Authentication.class);
              SecurityContext securityContext = Mockito.mock(SecurityContext.class);
              Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
              SecurityContextHolder.setContext(securityContext);
              principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              HomeController hc = new HomeController();
              System.out.println(principal);
              assertNull(hc.getUserSetupQuestions(principal, request));
              
       
       }
}
