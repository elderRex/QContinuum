
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
	String uinfo = "123";
	assertEquals(HttpStatus.OK, HomeController.createUser(uinfo, request));
	}
	
	/*
	@Test
	public void testFailedCreateUser() {
		
	}
	*/

}
