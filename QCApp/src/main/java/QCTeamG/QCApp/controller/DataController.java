package QCTeamG.QCApp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import QCTeamG.QCApp.dao.ItemsDAO;
import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UsersEntity;

/* Handles insertion and deletion of data */
@Controller
@RequestMapping(value="data/")
public class DataController {
	
	@Autowired
	ItemsDAO itemDAO;
	
	@Autowired
	UsersDAO userDAO;
	
	@Autowired
	ReviewsDAO reviewDAO;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Transactional
	public ItemsEntity addItem(String title)
	{
		try
		{
			ItemsEntity ite = new ItemsEntity();
			ite.setName(title);
			ite.setDescription(""); // TODO Get the description of each item from IMDB API
			ite.setType("movie");
			ite.setWebsite("www.imdb.com");
			itemDAO.createItem(ite);
			return ite;
		}
		catch (Exception e)
		{
			
		}
		return null;
	}
	
	@Transactional
	public void addReview(String review_text, String review_score, UsersEntity ue, ItemsEntity ie, Session sesh)
	{
		try
		{
			ReviewsEntity re = new ReviewsEntity();
			re.setIId(ie);
			re.setText(review_text);
			re.setUid(ue);
			re.setIsTraining(false);
			re.setRevKey(review_text.substring(0,600));
			re.setRating(Float.parseFloat(review_score));
			reviewDAO.createReview(re, sesh);
		}
		catch (Exception e)
		{
			
		}

	}

	@RequestMapping(value="/items/add-to-db",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public String addItemsToDb(Principal principal, HttpServletRequest request) throws NumberFormatException, IOException
	{
		
		Session sesh = sessionFactory.getCurrentSession();
		
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/pos_test_extracted.txt");

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String data;
		ItemsEntity ie = new ItemsEntity();
		UsersEntity ue = userDAO.getUserById(0);
		
		while ((data = bufferedReader.readLine()) != null) {
			// If the line of text contains a title.
			if (data.contains("-ttl-:"))
			{
				// Remove title definition
				String title = data.replace("-ttl-:", "");
				
				ie = itemDAO.getItemByName(title);
				
				// If the item doesn't exist, add it
				if (ie == null)
				{
					ie = addItem(title);
				}
			}
			// Line is a review
			else if (data.contains("-rev-:"))
			{
				String review_text = data.replace("-rev-:", "");
				String review_score = bufferedReader.readLine();
				//ReviewsEntity re = reviewDAO.getReviewByReviewText(review_text);
				
				// If review doesn't exist in db, add it
				addReview(review_text,review_score,ue,ie,sesh);
			}
			else
			{
				continue;
			}

		}
		
		bufferedReader.close();
		return "home";
		
	}


}
