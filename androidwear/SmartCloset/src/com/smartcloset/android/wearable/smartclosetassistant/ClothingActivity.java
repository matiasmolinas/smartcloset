package com.smartcloset.android.wearable.smartclosetassistant;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

public class ClothingActivity extends Activity {
    private static final String TAG = "SmartClosetAssistant";
    private String mClothingName;
    private Clothing mClothing;
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mSummaryTextView;
    private TextView mFeaturesTextView;
    private LinearLayout mStepsLayout;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        mClothingName = intent.getStringExtra(Constants.CLOTHING_NAME_TO_LOAD);
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Intent: " + intent.toString() + " " + mClothingName);
        }
        loadRecipe();
        
        // call AsynTask to perform network operation on separate thread
        new LedOnAsyncTask().execute("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing);
        mTitleTextView = (TextView) findViewById(R.id.clothingTextTitle);
        mSummaryTextView = (TextView) findViewById(R.id.clothingTextSummary);
        mImageView = (ImageView) findViewById(R.id.clothingImageView);
        mFeaturesTextView = (TextView) findViewById(R.id.textFeatures);
        mStepsLayout = (LinearLayout) findViewById(R.id.layoutSteps);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_cook:
                startDressing();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRecipe() {
        JSONObject jsonObject = AssetUtils.loadJSONAsset(this, mClothingName);
        if (jsonObject != null) {
            mClothing = Clothing.fromJson(this, jsonObject);
            if (mClothing != null) {
                displayClothing(mClothing);
            }
        }
    }

    private void displayClothing(Clothing clothing) {
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mTitleTextView.setAnimation(fadeIn);
        mTitleTextView.setText(clothing.titleText);
        mSummaryTextView.setText(clothing.summaryText);
        if (clothing.clothingImage != null) {
            mImageView.setAnimation(fadeIn);
            Bitmap recipeImage = AssetUtils.loadBitmapAsset(this, clothing.clothingImage);
            mImageView.setImageBitmap(recipeImage);
        }
        mFeaturesTextView.setText(clothing.featuresText);

        findViewById(R.id.featuresHeader).setAnimation(fadeIn);
        findViewById(R.id.featuresHeader).setVisibility(View.VISIBLE);
        findViewById(R.id.stepsHeader).setAnimation(fadeIn);

        findViewById(R.id.stepsHeader).setVisibility(View.VISIBLE);

        LayoutInflater inf = LayoutInflater.from(this);
        mStepsLayout.removeAllViews();
        int stepNumber = 1;
        for (Clothing.ImageStep step : clothing.imageSteps) {
            View view = inf.inflate(R.layout.step_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.stepImageView);
            if (step.stepImage == null) {
                iv.setVisibility(View.GONE);
            } else {
                Bitmap stepImage = AssetUtils.loadBitmapAsset(this, step.stepImage);
                iv.setImageBitmap(stepImage);
            }
            ((TextView) view.findViewById(R.id.textStep)).setText(
                    (stepNumber++) + ". " + step.stepText);
            mStepsLayout.addView(view);
        }
    }

    private void startDressing() {
        Intent intent = new Intent(this, ClothingService.class);
        intent.setAction(Constants.ACTION_START_DRESSING);
        intent.putExtra(Constants.EXTRA_CLOTHING, mClothing.toBundle());
        startService(intent);
    }
    
    private class LedOnAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... urls) {
			try{
				//Set led on calling arduino service .. this is only a proof of concept
				AssetUtils.callService(urls[0]);
			}
			catch (Exception e) {
				Log.e(TAG, "Failed to load exercise: " + e);
			}
			return null;
		}
	}
}
