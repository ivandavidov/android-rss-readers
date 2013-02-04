/**
 * 
 */
package ivan.davidov.investor.rssreader.reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Html;
import android.util.Log;

import ivan.davidov.investor.rssreader.R;
import ivan.davidov.investor.rssreader.util.Article;
import ivan.davidov.investor.rssreader.util.RSSHandler;

/**
 * @author Ivan Davidov
 *
 */
public class RssReader
{	
	public final static String BOLD_OPEN = "<B>";
	public final static String BOLD_CLOSE = "</B>";
	public final static String PARAGRAPH = "<P/>";
	public final static String ITALIC_OPEN = "<I>";
	public final static String ITALIC_CLOSE = "</I>";
	public final static String SMALL_OPEN = "<SMALL>";
	public final static String SMALL_CLOSE = "</SMALL>";

	/**
	 * This method defines a feed URL and then calles our SAX Handler to read the article list
	 * from the stream
	 * 
	 * @return List<JSONObject> - suitable for the List View activity
	 */
	public static List<JSONObject> getLatestRssFeed(Object source)
	{
		String feed = null;
		
		if(source instanceof String)
		{
			feed = (String)source;
		}
		else
		{
			Activity activity = (Activity)source;
			
			Resources res = activity.getResources();
			
			feed = res.getString(R.string.rss_url);
		}
 		
		RSSHandler rh = new RSSHandler();
		List<Article> articles =  rh.getLatestArticles(feed);
		
		if(articles == null || articles.size() <= 0)
		{
			Article article = new Article();
			
			article.setArticleId(-9999);
			article.setTitle("� ������� �������� �� �� ��������");
			article.setDescription("�������� � �� ������ �������� ������.");
			article.setPubDate("�� ��������: davidov.i@gmail.com");
			article.setUrl(null);
			
			articles.add(article);
		}
		else
		{
			Article article = new Article();
			
			article.setArticleId(-9999);
			article.setTitle("���� ������� - ��������� �������");
			article.setDescription("�������� ������� �� ���� ������� � ��������������� ����� LinkedIn");
			article.setPubDate("�� ��������: davidov.i@gmail.com");
			
			try {
				article.setUrl(new URL("http://bg.linkedin.com/in/ivandavidov"));
			} catch (MalformedURLException e) {
				// Oops... fallback to default.
				article.setUrl(null);
			}
			
			articles.add(article);
		}

		Log.e("RSS ERROR", "Number of articles " + articles.size());
		return fillData(articles);
	}
	
	
	/**
	 * This method takes a list of Article objects and converts them in to the 
	 * correct JSON format so the info can be processed by our list view
	 * 
	 * @param articles - list<Article>
	 * @return List<JSONObject> - suitable for the List View activity
	 */
	private static List<JSONObject> fillData(List<Article> articles) {

        List<JSONObject> items = new ArrayList<JSONObject>();
        for (Article article : articles) {
            JSONObject current = new JSONObject();
            try {
            	buildJsonObject(article, current);
			} catch (JSONException e) {
				Log.e("RSS ERROR", "Error creating JSON Object from RSS feed");
			}
			items.add(current);
        }
        
        return items;
	}


	/**
	 * This method takes a single Article Object and converts it in to a single JSON object
	 * including some additional HTML formating so they can be displayed nicely
	 * 
	 * @param article
	 * @param current
	 * @throws JSONException
	 */
	private static void buildJsonObject(Article article, JSONObject current) throws JSONException {
		String title = article.getTitle();
		String description = article.getDescription();
		String date = article.getPubDate();		
		
		if(date != null && date.length() > 0)
		{
			DateFormat rssFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.UK);
			DateFormat bulFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
			
			try {
				Date realDate = rssFormatter.parse(date);
				
				String bulDate = bulFormatter.format(realDate);
				
				date = bulDate;
			} catch (ParseException e) {
				// Can't parse, stick to the original.
				Log.e("DATE PARSER", e.getMessage());
			}
		}
		
		int tagPos = description.indexOf("<");
		
		if(tagPos > 0)
		{
			description = description.substring(0, tagPos) + '.';
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(BOLD_OPEN).append(title).append(BOLD_CLOSE);
		sb.append(PARAGRAPH);
		sb.append(description);
		sb.append(PARAGRAPH);
		sb.append(SMALL_OPEN).append(ITALIC_OPEN).append(date).append(ITALIC_CLOSE).append(SMALL_CLOSE);
		
		current.put("text", Html.fromHtml(sb.toString()));
		current.put("title", BOLD_OPEN + title + BOLD_CLOSE);
		
		String urlString = null;
		
		if(article.getUrl() == null)
		{
			urlString = "";
		}
		else
		{
			urlString = article.getUrl().toString();
		}
		
		current.put("url", urlString);
	}
}
