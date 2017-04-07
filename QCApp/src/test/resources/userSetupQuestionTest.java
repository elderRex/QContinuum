package QCTeamG.QCApp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.junit.Test;

public class userSetupQuestionTest {
       private Principal principal;
       
       @Test
       public void testSuccessfulQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              HomeController hc = new HomeController();
              assertNotNull(hc.getUserSetupQuestions(principal, request));
              
       
       }
       
       @Test
       public void testFailedQuestion() {
              HttpServletRequest request = mock(HttpServletRequest.class);
              HomeController hc = new HomeController();
              assertNull(hc.getUserSetupQuestions(principal, request));
              
       
       }
}
