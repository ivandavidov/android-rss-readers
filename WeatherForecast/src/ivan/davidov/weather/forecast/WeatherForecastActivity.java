package ivan.davidov.weather.forecast;

import ivan.davidov.weather.forecast.reader.RssReader;
import ivan.davidov.weather.forecast.util.CommonStringsHelper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


public class WeatherForecastActivity extends ListActivity
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
				WeatherForecastActivity.this.setTitle(R.string.default_text);
			};
			
			@Override
			protected void onPostExecute(List<JSONObject> jobs)
			{
				WeatherForecastActivity.this.setTitle(R.string.loaded_text);
			};
			
			@Override
			protected List<JSONObject> doInBackground(String... arg0)
			{
				List<JSONObject> jobs = new ArrayList<JSONObject>();
				
				try {
					CommonStringsHelper res = new CommonStringsHelper(WeatherForecastActivity.this.getResources());
					jobs = RssReader.getLatestRssFeed(res);			
				} catch (Exception e) {
					Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
				}

				final RssListAdapter adapter = new RssListAdapter(WeatherForecastActivity.this, jobs);

				WeatherForecastActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						WeatherForecastActivity.this.setListAdapter(adapter);
					}
				});
								
				return jobs;
			}
		};
		
		asyncTask.execute("");
	}	
}