
package QCTeamG.QCApp.controller;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;

import org.junit.Test;


public class createUserTest {
	
	
	@Test
	public void testSuccessfulCreateUser() {
	HttpServletRequest request = mock(HttpServletRequest.class); 
	String uinfo = "{“firstname”:”test”, “lastname”: “user”, “email”: “test@columbia.edu”, “password1”: “123”, “password2”: “123”}";
	HomeController hc = new HomeController();
	assertEquals(HttpStatus.OK, hc.createUser(uinfo, request));
	}
	
	/*
	@Test
	public void testFailedCreateUser() {
		
	}
	*/

}
