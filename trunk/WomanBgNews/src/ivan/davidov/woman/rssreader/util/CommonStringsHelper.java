package ivan.davidov.woman.rssreader.util;

import android.content.Context;
import android.content.res.Resources;

public class CommonStringsHelper
{
	private Context ctx = null;
	
	private Resources res = null;
	
	public CommonStringsHelper(Context ctx)
	{
		this.ctx = ctx;
	}

	public CommonStringsHelper(Resources res)
	{
		this.res = res;
	}
	
	public String getString(int resId)
	{
		if(ctx != null)
		{
			return ctx.getString(resId);
		}
		else
		{
			return res.getString(resId);
		}
	}
}
