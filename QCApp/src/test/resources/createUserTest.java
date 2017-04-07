
package QCTeamG.QCApp.controller;


import org.json.simple.JSONObject;
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
	JSONObject json = new JSONObject();
	json.put("firstname", "test");
	json.put("lastname", "user");
	json.put("email", "test@columbia.edu");
	json.put("password1", "123");
	json.put("password2", "123");
	String uinfo = json.toString();
	HomeController hc = new HomeController();
	ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
	ResponseEntity<String> actual = hc.createUser(uinfo, request);
	assertEquals(expected.toString(),actual.toString());
	}
	
	
	@Test
	public void testFailedCreateUser() {
		HttpServletRequest request = mock(HttpServletRequest.class); 
		String uinfo = "{“firstname”:”test”, “lastname”: “user”, “email”: “test@columbia.edu”, “password1”: “123”, “password2”: “123”}";
		HomeController hc = new HomeController();
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		ResponseEntity<String> actual = hc.createUser(uinfo, request);
		assertEquals(expected.toString(),actual.toString());
	}
	

}
