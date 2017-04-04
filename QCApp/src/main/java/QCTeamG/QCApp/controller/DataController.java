//package QCTeamG.QCApp.controller;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.security.Principal;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.io.FileUtils;
//import org.jsoup.Connection.Response;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
//import org.jsoup.parser.Parser;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import QCTeamG.QCApp.dao.ItemsDAO;
//import QCTeamG.QCApp.dao.ReviewsDAO;
//import QCTeamG.QCApp.dao.UsersDAO;
//import QCTeamG.QCApp.entities.ItemsEntity;
//
///* Handles insertion and deletion of data */
//@Controller
//public class DataController {
//	
//	private @Autowired AutowireCapableBeanFactory beanFactory;
//	
//	@Autowired
//	ItemsDAO itemDAO;
//	
//	@Autowired
//	UsersDAO userDAO;
//		
//	// Return 0 if item isn't in the db	
//	public int itemExistsInDb(String ie)
//	{
//		ItemsEntity ii = itemDAO.getItemByName(ie);
//		if (ii == null)
//		{
//			return 0;
//		}
//		return ii.getId();
//		
//	}
//
//	@Transactional
//	@RequestMapping(value="/items/add-to-db",method=RequestMethod.GET,produces={"application/xml", "application/json"})
//	public @ResponseBody String addItemsToDb(Principal principal, HttpServletRequest request) {
//		
//		String uhome = System.getProperty("user.home");
//		File file = new File(uhome + "/Dropbox/ASE/Extracted/pos_extracted.txt");
//
//		FileReader fileReader = new FileReader(file);
//		BufferedReader bufferedReader = new BufferedReader(fileReader);
//		
//		StringBuffer stringBuffer = new StringBuffer();
//		String data;
//		
//		while ((data = bufferedReader.readLine()) != null) {
//			
//			// If the line of text contains a title.
//			if (data.contains("-ttl-:"))
//			{
//				// Remove title definition
//				String title = data.replace("-ttl-:", "");
//				if (itemExistsInDb(title))
//				{
//				}
//				ItemsEntity ie = new ItemsEntity();
//				ie.setName(t);
//				ie.setDescription(""); // TODO Get the description of each item from IMDB API
//				ie.setType("movie");
//				ie.setWebsite("");
//				
//			}
//			else if (data == "/n" || data == "")
//			{
//				break;
//			}
//	        f.createNewFile();
//	        Random rand = new Random(); 
//	        int value = rand.nextInt(8); 
//	        TimeUnit.SECONDS.sleep(value);
//	        FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
//			
//		}
//		
//		
//		
//		return 0;
//	}
////	
////	public static int addReviewtoDB(String t)
////	{
////		return 0;
////	}
////
////			
////			for (int i = 12087; i >= 0; i--)
////			{
////				Document doc = Jsoup.parse(new File(uhome+"/Dropbox/ASE/HTML/pos_movie_"+i+".html"), "UTF-8");
////				Elements metaTags = doc.getElementsByTag("meta");
////				
////				// Get title and save to file
////				String title = doc.select("#tn15title>h1>a").text();
////				bw.write("-ttl-:" + title);
////				bw.write("\n\n");
////				
////				int iterator = 9;
////				// Get each review and save to file
////				while (true)
////				{
////					String review = doc.select("#tn15content > p:nth-child(" + iterator + ")").text();
////					bw.write("\n\n");
////					bw.write("-rev-:"+review);
////					if (review.length() == 0 || review.contains("Add another review"))
////						break;
////					iterator += 4;
////				}
////				bw.write("\n\n\n");
////
////				
////			}
////			bw.close();
////			fw.close();
////			
////		}
////		
////		// Downloads HTML from IMDB Training Set
////		public static void downloadHTMLTrainPos() throws IOException, InterruptedException
////		{
////			String uhome = System.getProperty("user.home");
////			
////			// Read file containing urls
////			File file = new File(uhome + "/Dropbox/ASE/aclImdb/train/urls_pos.txt");
////			FileReader fileReader = new FileReader(file);
////			BufferedReader bufferedReader = new BufferedReader(fileReader);
////			
////			StringBuffer stringBuffer = new StringBuffer();
////			String url;
////			int count = 0;
////			
////			while ((url = bufferedReader.readLine()) != null) {
////				Response response = Jsoup.connect(url).execute();
////		        Document doc = response.parse();
////
////		        File f = new File(uhome+"/Dropbox/ASE/HTML/pos_movie_"+count+".html");
////		        count++;
////		        f.createNewFile();
////		        Random rand = new Random(); 
////		        int value = rand.nextInt(8); 
////		        TimeUnit.SECONDS.sleep(value);
////		        FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
////			}
////		}
//
//	
//	
//	
//
//}
