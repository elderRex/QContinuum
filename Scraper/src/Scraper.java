import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Scraper {

	public static void main(String[] args) throws IOException, InterruptedException {

		//downloadHTMLTestPos();
		//downloadHTMLTrainPos();
		
		//extractTrainPosReviewsHTML();
		//downloadHTMLTrainPos();
		
		extractTestPosReviewsHTML();

	}
	
	public static void extractTestPosReviewsHTML() throws IOException
	{
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/pos_test_extracted.txt");
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		int count = 0;
		long prevFileSize = 0;
		
		for (int i = 1350; i >= 0; i--)
		{
			try
			{
			
				String pg_ref = uhome+"/Dropbox/ASE/HTML_test_pos/pos_movie_"+i+".html";
	
				File fl = new File(pg_ref);
	
				Document doc = Jsoup.parse(fl, "UTF-8");
				Elements metaTags = doc.getElementsByTag("meta");
				
				// Get title and save to file
				String title = doc.select("#tn15title>h1>a").text();
						
				bw.write("-ttl-:" + title);
				bw.write("\n\n");
				
				int iterator = 9;
				// Get each review and save to file
				while (true)
				{
					String review = doc.select("#tn15content > p:nth-child(" + iterator + ")").text();
					
					// If blank review, increment iterator by 1 because we haven't found it.
					if (review.length() == 0)
					{
						iterator++;
						continue;
					}
					
					// Last node on page always has "Add another review" so skip
					if (review.contains("Add another review"))
						break;
					
					bw.write("\n\n");
					bw.write("-rev-:"+ review);
					// Try to find a review score, and if not, report average of 7.5 for positive, and 2.5 for negative
					String score = "";
					try
					{
						score = doc.select("#tn15content > div:nth-child(" + (iterator-1) + ") >img").attr("alt").split("/")[0];
						if (score.length() == 0)
							score = "7.5";
					}
					catch (Exception e)
					{
						
					}
					
					bw.write("\n" + score);
					iterator += 4;
				}
				bw.write("\n\n\n");
			}
			catch (Exception e)
			{
				
			}
			
		}
		bw.close();
		fw.close();
		
	}
	
	/* 
	 * Download Movie (non-review) HTML Files 
	 * This will be automated in future versions
	 */
	public static void downloadMHTMLTrainNeg() throws IOException, InterruptedException
	{
		String uhome = System.getProperty("user.home");
		
		// Read file containing urls
		File file = new File(uhome + "/Dropbox/ASE/aclImdb/train/urls_neg.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		StringBuffer stringBuffer = new StringBuffer();
		String url;
		int count = 0;
		String prevUrl = "";
		
		while ((url = bufferedReader.readLine()) != null) {
			if (prevUrl.equals(url))
				continue;
			
			prevUrl = url;
			try
			{
				String m_page = url.split("/usercomments")[0];
				Response response = Jsoup.connect(m_page).execute();
		        Document doc = response.parse();
		        String fname = uhome+"/Dropbox/ASE/mHTML_train_neg/neg_movie_"+count+".html";
		        File f = new File(fname);
		        f.createNewFile();
		        Random rand = new Random(); 
		        FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
			}
			catch (Exception e)
			{
				
			}
			
	        count++;
		}
	}

}
