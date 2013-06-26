package ivan.davidov.profit.rssreader;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<JSONObject> {

	Activity activity = null;
	
	public RssListAdapter(Activity activity, List<JSONObject> imageAndTexts) {
		super(activity, 0, imageAndTexts);
		
		this.activity = activity;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate the views from XML
		View rowView = inflater.inflate(R.layout.image_text_layout, null);
		JSONObject jsonImageText = getItem(position);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		//The next section we update at runtime the text - as provided by the JSON from our REST call
		////////////////////////////////////////////////////////////////////////////////////////////////////
		TextView textView = (TextView) rowView.findViewById(R.id.job_text);
		
		try {
			Spanned text = (Spanned)jsonImageText.get("text");
			final String url = (String)jsonImageText.get("url");
			
			textView.setText(text);
			
			if (url != null && url.length() > 0 && activity != null)
			{
				textView.setOnClickListener(new OnClickListener() {				
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						
						RssListAdapter.this.activity.startActivity(intent);
					}});
			}

		} catch (JSONException e) {
			textView.setText("JSON Exception");
		}

		return rowView;
	} 
}