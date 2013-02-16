package ivan.davidov.dnes.rssreader;

import ivan.davidov.dnes.rssreader.reader.RssReader;
import ivan.davidov.dnes.rssreader.util.CommonStringsHelper;
import ivan.davidov.dnes.rssreader.widget.RssWidget;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.util.Log;


public class RssActivity extends ListActivity {

	private RssListAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<JSONObject> jobs = new ArrayList<JSONObject>();
		
		try {
			CommonStringsHelper res = new CommonStringsHelper(this.getResources());
			jobs = RssReader.getLatestRssFeed(res);			
		} catch (Exception e) {
			Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
		}

		adapter = new RssListAdapter(this, jobs);

		setListAdapter(adapter);
		
		setTitle(R.string.loaded_text);
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
		
		RssWidget.updateWidget(this, appWidgetManager, jobs);
	}
}