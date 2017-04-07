
package QCTeamG.QCApp.controller;



import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.junit.Test;


public class createUserTest {
	
	
	@Test
	public void testSuccessfulCreateUser() {
	HttpServletRequest request = mock(HttpServletRequest.class); 
	String uinfo = "{“firstname”:”test”, “lastname”: “user”, “email”: “test@columbia.edu”, “password1”: “123”, “password2”: “123”}";
	HomeController hc = new HomeController();
	ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	ResponseEntity<String> actual = hc.createUser(uinfo, request);
	boolean condition = true;
	if(!actual.equals(expected)) condition = false;
	assertTrue(condition);
	}
	
	/*
	@Test
	public void testFailedCreateUser() {
		
	}
	*/

}
