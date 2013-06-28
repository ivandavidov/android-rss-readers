package ivan.davidov.woman.rssreader;

import ivan.davidov.woman.rssreader.reader.RssReader;
import ivan.davidov.woman.rssreader.util.CommonStringsHelper;
import ivan.davidov.woman.rssreader.widget.RssWidget;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


public class RssActivity extends ListActivity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		AsyncTask<String, Integer, List<JSONObject>> asyncTask = new AsyncTask<String, Integer, List<JSONObject>>()
		{
			@Override
			protected void onPreExecute()
			{
				RssActivity.this.setTitle(R.string.default_text);
			};
			
			@Override
			protected void onPostExecute(List<JSONObject> jobs)
			{
				RssActivity.this.setTitle(R.string.loaded_text);
			};
			
			@Override
			protected List<JSONObject> doInBackground(String... arg0)
			{
				List<JSONObject> jobs = new ArrayList<JSONObject>();
				
				try {
					CommonStringsHelper res = new CommonStringsHelper(RssActivity.this.getResources());
					jobs = RssReader.getLatestRssFeed(res);			
				} catch (Exception e) {
					Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
				}

				final RssListAdapter adapter = new RssListAdapter(RssActivity.this, jobs);

				RssActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						RssActivity.this.setListAdapter(adapter);
					}
				});
				
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(RssActivity.this);
				
				RssWidget.updateWidget(RssActivity.this, appWidgetManager, jobs);
				
				return jobs;
			}
		};
		
		asyncTask.execute("");
	}	
}