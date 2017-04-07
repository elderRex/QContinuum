package QCTeamG.QCApp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.junit.Test;

public class accountStatusTest {
  
  @Test
	public void testAccountFailedLogin() {
		HomeController hc = new HomeController();
		Principal principal = null;
		String actualstatus = hc.user(principal);
		String expectedstauts = "home";
		assertEquals(expectedstatus,actualstatus);
  
  }
/*	
  @Test
	public void testAccountSuccessfulSetUp() {
  
  }
	
  @Test
	public void testAccountFailedSetUp() {
  
  }
  */
}
