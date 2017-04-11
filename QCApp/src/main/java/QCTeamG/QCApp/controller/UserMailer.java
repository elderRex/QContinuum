package QCTeamG.QCApp.controller;

import java.io.IOException;


import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;

import com.sendgrid.SendGrid;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;

public class UserMailer
{
	//private JavaMailSender mailSender;
 
//	public void setMailSender(JavaMailSender mailSender) {
//		this.mailSender = mailSender;
//	}
	

	public void changePasswordRequest(String email, String firstname, String lastname, String url) throws MessagingException, IOException {
		
		Email from = new Email("f.minakov@gmail.com");
		String subject = "Password Change";
		Email to = new Email(email);
		Content content = new Content("text/html", 
				  "<html>"
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
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(System.getenv("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"));
		Request request = new Request();
		try {
		  request.method = Method.POST;
		  request.endpoint = "mail/send";
		  request.body = mail.build();
		  Response response = sg.api(request);
		} 
		catch (IOException ex)
		{
		  throw ex;
		}

	}
 
	
}
