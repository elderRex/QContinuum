package QCTeamG.QCApp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.junit.Test;

public class userSetupQuestionTester {
       private Principal principal;
       
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              HomeController hc = new HomeController();
              String result = hc.getUserSetupQuestions(principal, request);
              assertNotNull(result);
              
       
       }
       
       @Test
       public void testFailedQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              HomeController hc = new HomeController();
              String result = hc.getUserSetupQuestions(principal, request);
              assertNull(result);
              
       
       }
}
