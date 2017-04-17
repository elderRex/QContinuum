package QCTeamG.QCApp.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import QCTeamG.QCApp.dao.ItemsDAO;
import QCTeamG.QCApp.dao.ReviewsDAO;
import QCTeamG.QCApp.dao.UsersDAO;
import QCTeamG.QCApp.entities.ItemsEntity;
import QCTeamG.QCApp.entities.ReviewsEntity;
import QCTeamG.QCApp.entities.UsersEntity;

/* Handles insertion and deletion of data */
@Controller
@RequestMapping(value="data/")
@EnableTransactionManagement
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
	public void addReviewTesting(String review_text, String review_score, UsersEntity ue, ItemsEntity ie, Session sesh)
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
			e.printStackTrace();
		}

	}
	
	@Transactional
	public void addToDB(Session sesh, String data,ItemsEntity ie, BufferedReader bufferedReader,UsersEntity ue)
	{

		
	}
	
	//Did Neg Test
	@RequestMapping(value="/items/add-to-db",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public String addItemsToDb(Principal principal, HttpServletRequest request) throws NumberFormatException, IOException
	{		
		
		Session sesh = sessionFactory.openSession();
		
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/neg_test_extracted.txt");

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String data;
		ItemsEntity ie = new ItemsEntity();
		UsersEntity ue = userDAO.getUserById(0);
		
		while ((data = bufferedReader.readLine()) != null) {
			try
			{
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
					addReviewTesting(review_text,review_score,ue,ie,sesh);
				}
				else
				{
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		bufferedReader.close();
		sesh.close();
		return "home";
		
	}
	
	/* Add Movie Subject and Photos Neg Test */
	@Transactional
	@RequestMapping(value="/items/add-md"
			+ "ata-to-db",method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public String extractMDataFromHTML(Principal principal, HttpServletRequest request) throws NumberFormatException, IOException
	{
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/neg_test_extracted.txt");
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		int count = 0;
		long prevFileSize = 0;
		
		for (int i = 3015; i >= 0; i--)
		{
			try
			{
			
				String pg_ref = uhome+"/Dropbox/ASE/mHTML_test_neg/neg_movie_"+i+".html";
	
				File fl = new File(pg_ref);
	
				Document doc = Jsoup.parse(fl, "UTF-8");
				Elements metaTags = doc.getElementsByTag("meta");
				
				// Get title and save to file
				Elements elem = doc.select("#title-overview-widget>div.vital>div.title_block>div>div.titleBar>div.title_wrapper>h1");
				String movie_title = elem.get(0).childNodes().get(0).toString().replace("&nbsp;","");
				String subj = doc.select("#title-overview-widget>div.vital>div.title_block>div>div.titleBar>div.title_wrapper>div>a:nth-child(5)>span").text();
				String description = doc.select("#title-overview-widget>div.minPosterWithPlotSummaryHeight>div.plot_summary_wrapper>div.plot_summary.minPlotHeightWithPoster>div.summary_text").text();
				
				// Save Image to filesystem
				String img = doc.select("#title-overview-widget>div.minPosterWithPlotSummaryHeight>div.poster>a>img").first().attr("src");
				URL url_image = new URL(img);
				
				BufferedImage image = null;
		        try
		        {
		            image = ImageIO.read(url_image);
		            ImageIO.write(image, "jpg",new File("/Users/fyodorminakov/Dropbox/ASE/app/QCApp/src/main/webapp/resources/"+ movie_title + ".jpg"));
		        }
		        catch (IOException e)
		        {
		        		e.printStackTrace();
		        }
		        
		        ItemsEntity ie = itemDAO.getItemByName(movie_title);
		        
		        ie.setSubject(subj);
		        ie.setDescription(description);
		        
		        Session sesh = sessionFactory.getCurrentSession();
								
				itemDAO.updateItem(ie, sesh);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		bw.close();
		fw.close();
		return "";
	}
	
//	@RequestMapping(value = "/img/{imageId}")
//	@ResponseBody
//	public byte[] displayImage(@PathVariable String imgName, HttpServletRequest request) throws IOException  {
//	Path path = Paths.get("/resources/images/" + imgName);
//	byte[] data = Files.readAllBytes(path); 
//	return data;
//	}


}
