package QCTeamG.QCApp.controller;

import java.io.IOException;


import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class UserMailer
{
	//private JavaMailSender mailSender;
 
//	public void setMailSender(JavaMailSender mailSender) {
//		this.mailSender = mailSender;
//	}
	

	public void changePasswordRequest(String emaila, String firstname, String lastname, String url) throws MessagingException, IOException {
		
		SendGrid sg = new SendGrid(System.getenv("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"));
		 
		SendGrid.Email email = new SendGrid.Email();

	    email.addTo(emaila);
	    email.setFrom("f.minakov@gmail.com");
	    email.setSubject("Password Change");
	    
	    email.setHtml("<html>"
	    		+ "<body>"						
				+ "<div style='font-size: 12px'>"
					+ "<div>"
						+ " Dear " + firstname + ","
					+ "</div>"
					
					+ "<br>"
					
					+ "<div>"
					+ "You requested a password reset. Please click on the link below to reset your password."
					+ "</div>"
					+ "<br>"
					+ "<div style='font-size: 16px;margin-top: 30px;'><a href='" + url + "'>" + url + "</a></div>"
	    			+ "</div>"							
				+ "<body"
			+ "</html>");

	    try {
			SendGrid.Response response = sg.send(email);
		} catch (SendGridException e) {
			e.printStackTrace();
		}
		
		
		
		
	
		

	}
 
	
}
