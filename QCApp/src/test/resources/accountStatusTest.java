package QCTeamG.QCApp.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.http.*;
import java.security.Principal;

import QCTeamG.QCApp.entities.UsersEntity;
import QCTeamG.QCApp.dao.UsersDAO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.junit.Test;

public class accountStatusTest {

  
  @Test
	public void testAccountFailedLogin() {
	      
              	UsersEntity ue = new UsersEntity();
              
		
		HomeController hc = new HomeController();
		//Principal principal = null;
		Integer uid = null;
		String actualstatus = hc.user(uid,ue);
		String expectedstatus = "home";
		assertEquals(expectedstatus,actualstatus);
  
  }
	/*
  @Test
	public void testAccountSuccessfulSetUp() {
		HomeController hc = new HomeController();
		Integer uid = 0;
		String actualstatus = hc.user(uid);
		String expectedstatus = "user";
		assertEquals(expectedstatus,actualstatus);
		
  
  }
  */
	
/*
  @Test
	public void testAccountFailedSetUp() {
  
  }
  */
}
