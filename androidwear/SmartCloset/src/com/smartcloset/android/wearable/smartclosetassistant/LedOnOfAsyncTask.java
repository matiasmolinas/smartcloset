package com.smartcloset.android.wearable.smartclosetassistant;

import android.os.AsyncTask;
import android.util.Log;

public class LedOnOfAsyncTask extends AsyncTask<String, Void, Void> {
	private static final String TAG = "SmartClosetAssistant";
	
	@Override
	protected Void doInBackground(String... urls) {
		try{
			//Set led on calling arduino service .. this is only a proof of concept
			AssetUtils.callService(urls[0]);
		}
		catch (Exception e) {
			Log.e(TAG, "Failed to perform operation: " + e);
		}
		return null;
	}
}
