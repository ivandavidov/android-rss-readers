package ivan.davidov.dnes.rssreader.widget;

import ivan.davidov.dnes.rssreader.R;
import ivan.davidov.dnes.rssreader.RssActivity;
import ivan.davidov.dnes.rssreader.reader.RssReader;
import ivan.davidov.dnes.rssreader.util.CommonStringsHelper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.RemoteViews;

public class RssWidget extends AppWidgetProvider
{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgets)
	{		
		List<JSONObject> jobs = new ArrayList<JSONObject>();
		
		try {
			CommonStringsHelper res = new CommonStringsHelper(context);
			jobs = RssReader.getLatestRssFeed(res);			
		} catch (Exception e) {
			Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
		}		
		
		RssWidget.updateWidget(context, appWidgetManager, jobs);
    }
	
	public static void updateWidget(Context context, AppWidgetManager appWidgetManager, List<JSONObject> jobs)
	{
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.image_text_layout);
        		
        try {
        	Spanned text = null;
        	
        	if(jobs.size() == 1)
        	{
	        	text = (Spanned)jobs.get(0).get("text");
        	}
        	else
        	{
            	StringBuffer sb = new StringBuffer();
            	
            	for(int i = 0; i < jobs.size(); i++)            		
            	{        		
            		if(i > 0)
            		{
            			sb.append(RssReader.PARAGRAPH);
            		}
            		
            		sb.append(jobs.get(i).get("title"));            		
            	}
            	
            	text = Html.fromHtml(sb.toString());
        	}
        	
			remoteViews.setTextViewText(R.id.job_text, text);
			
			Intent intent = new Intent(context, RssActivity.class);
			
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			
			remoteViews.setOnClickPendingIntent(R.id.job_text, pendingIntent);
		}
        catch (JSONException e)
        {
			e.printStackTrace();
		}
        
		ComponentName rssWidget = new ComponentName(context, RssWidget.class );

		appWidgetManager.updateAppWidget(rssWidget, remoteViews);
	}
}
