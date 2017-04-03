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

		//extractReviewsHTML();
		downloadHTMLTrainPos();

	}
	
	public static void extractReviewsHTML() throws IOException
	{
		String uhome = System.getProperty("user.home");
		File file = new File(uhome + "/Dropbox/ASE/Extracted/pos_extracted.txt");
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);


		int count = 0;

		
		for (int i = 0; i >= 0; i--)
		{
			Document doc = Jsoup.parse(new File(uhome+"/Dropbox/ASE/HTML/pos_movie_"+count+".html"), "UTF-8");
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
				bw.write("\n\n");
				bw.write("-rev-:"+review);
				if (review.length() == 0 || review.contains("Add another review"))
					break;
				iterator += 4;
			}
			bw.write("\n\n\n");

			
		}
		bw.close();
		fw.close();
		
	}
	
	// Downloads HTML from IMDB Training Set
	public static void downloadHTMLTrainPos() throws IOException, InterruptedException
	{
		String uhome = System.getProperty("user.home");
		
		// Read file containing urls
		File file = new File(uhome + "/Dropbox/ASE/aclImdb/train/urls_pos.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		StringBuffer stringBuffer = new StringBuffer();
		String url;
		int count = 0;
		
		while ((url = bufferedReader.readLine()) != null) {
			Response response = Jsoup.connect(url).execute();
	        Document doc = response.parse();

	        File f = new File(uhome+"/Dropbox/ASE/HTML/pos_movie_"+count+".html");
	        count++;
	        f.createNewFile();
	        Random rand = new Random(); 
	        int value = rand.nextInt(8); 
	        TimeUnit.SECONDS.sleep(value);
	        FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
		}
	}
	
	
	

}
