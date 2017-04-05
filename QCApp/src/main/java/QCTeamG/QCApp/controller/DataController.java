package QCTeamG.QCApp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

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

	@Transactional
	@RequestMapping(value="/items/add-to-db",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public void addItemsToDb(Principal principal, HttpServletRequest request) throws NumberFormatException, IOException
	{
		
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/pos_extracted.txt");

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String data;
		ItemsEntity ie = null;
		UsersEntity ue = userDAO.getUserById(53);
		
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
					ie = new ItemsEntity();
					ie.setName(title);
					ie.setDescription(""); // TODO Get the description of each item from IMDB API
					ie.setType("movie");
					ie.setWebsite("www.imdb.com");
					itemDAO.createItem(ie);
				}
				
				
			}
			// Line is a review
			else if (data.contains("-rev-:"))
			{
				String review_text = data.replace("-rev-:", "");
				String review_score = bufferedReader.readLine();
				ReviewsEntity re = reviewDAO.getReviewByReviewText(review_text);
				// If review doesn't exist in db, add it
				if (re == null)
				{
					re = new ReviewsEntity();
					Date dt = new Date(0);
					re.setDate(dt);
					re.setIId(ie);
					re.setText(review_text);
					re.setUid(ue);
					re.setRating(Integer.parseInt(review_score));
					reviewDAO.createReview(re);
				}
			}
			else
			{
				break;
			}

		}
		
		bufferedReader.close();
		
	}


}
