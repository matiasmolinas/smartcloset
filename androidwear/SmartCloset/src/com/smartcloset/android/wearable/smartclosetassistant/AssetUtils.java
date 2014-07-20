package com.smartcloset.android.wearable.smartclosetassistant;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class AssetUtils {
    private static final String TAG = "SmartClosetAssistant";

    public static byte[] loadAsset(Context context, String asset) {
        byte[] buffer = null;
        try {
            InputStream is = context.getAssets().open(asset);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to load asset " + asset + ": " + e);
        }
        return buffer;
    }

    public static JSONObject loadJSONAsset(Context context, String asset) {
        String jsonString = new String(loadAsset(context, asset));
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON asset " + asset + ": " + e);
        }
        return jsonObject;
    }

    public static Bitmap loadBitmapAsset(Context context, String asset) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.getAssets().open(asset);
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, "Cannot close InputStream: ", e);
                }
            }
        }
        return bitmap;
    }
    
    private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}

	public static JSONObject callService(String url) {
		InputStream inputStream = null;
		JSONObject result = null;
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// convert inputstream to string
			if (inputStream != null){
				String response = convertInputStreamToString(inputStream);
				//result = new JSONObject(response);
			}
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}
}
