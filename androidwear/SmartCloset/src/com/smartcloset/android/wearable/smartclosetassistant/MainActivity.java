
package com.smartcloset.android.wearable.smartclosetassistant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private static final String TAG = "SmartClosetAssistant";
    private ClothingListAdapter mAdapter;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG , "onListItemClick " + position);
        }
        String itemName = mAdapter.getItemName(position);
        Intent intent = new Intent(getApplicationContext(), ClothingActivity.class);
        intent.putExtra(Constants.CLOTHING_NAME_TO_LOAD, itemName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);

        mAdapter = new ClothingListAdapter(this);
        setListAdapter(mAdapter);
        
        // call AsynTask to perform network operation on separate thread
        new LedOnOfAsyncTask().execute("http://192.168.100.44/a");
    }
}
