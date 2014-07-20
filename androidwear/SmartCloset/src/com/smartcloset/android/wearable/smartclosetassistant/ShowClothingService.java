package com.smartcloset.android.wearable.smartclosetassistant;

import android.app.IntentService;
import android.content.Intent;

public class ShowClothingService extends IntentService {

	public ShowClothingService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// call AsynTask to perform network operation on separate thread
        new LedOnOfAsyncTask().execute("http://192.168.100.44/e");
	}

}
