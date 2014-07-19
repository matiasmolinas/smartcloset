package com.smartcloset.android.wearable.smartclosetassistant;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Clothing {
    private static final String TAG = "SmartClosetAssistant";

    public String titleText;
    public String summaryText;
    public String clothingImage;
    public String featuresText;

    public static class ImageStep {
    	ImageStep() { }
        public String stepImage;
        public String stepText;

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CLOTHING_FIELD_STEP_TEXT, stepText);
            bundle.putString(Constants.CLOTHING_FIELD_STEP_IMAGE, stepImage);
            return bundle;
        }

        public static ImageStep fromBundle(Bundle bundle) {
        	ImageStep recipeStep = new ImageStep();
            recipeStep.stepText = bundle.getString(Constants.CLOTHING_FIELD_STEP_TEXT);
            recipeStep.stepImage = bundle.getString(Constants.CLOTHING_FIELD_STEP_IMAGE);
            return recipeStep;
        }
    }
    ArrayList<ImageStep> imageSteps;

    public Clothing() {
    	imageSteps = new ArrayList<ImageStep>();
    }

    public static Clothing fromJson(Context context, JSONObject json) {
    	Clothing clothing = new Clothing();
        try {
        	clothing.titleText = json.getString(Constants.CLOTHING_FIELD_TITLE);
        	clothing.summaryText = json.getString(Constants.CLOTHING_FIELD_SUMMARY);
            if (json.has(Constants.CLOTHING_FIELD_IMAGE)) {
            	clothing.clothingImage = json.getString(Constants.CLOTHING_FIELD_IMAGE);
            }
            JSONArray features = json.getJSONArray(Constants.CLOTHING_FIELD_FEATURES);
            clothing.featuresText = "";
            for (int i = 0; i < features.length(); i++) {
            	clothing.featuresText += " - "
                        + features.getJSONObject(i).getString(Constants.CLOTHING_FIELD_TEXT) + "\n";
            }

            JSONArray steps = json.getJSONArray(Constants.CLOTHING_FIELD_STEPS);
            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                ImageStep imageStep = new ImageStep();
                imageStep.stepText = step.getString(Constants.CLOTHING_FIELD_TEXT);
                if (step.has(Constants.CLOTHING_FIELD_IMAGE)) {
                	imageStep.stepImage = step.getString(Constants.CLOTHING_FIELD_IMAGE);
                }
                clothing.imageSteps.add(imageStep);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error loading clothing: " + e);
            return null;
        }
        return clothing;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CLOTHING_FIELD_TITLE, titleText);
        bundle.putString(Constants.CLOTHING_FIELD_SUMMARY, summaryText);
        bundle.putString(Constants.CLOTHING_FIELD_IMAGE, clothingImage);
        bundle.putString(Constants.CLOTHING_FIELD_FEATURES, featuresText);
        if (imageSteps != null) {
            ArrayList<Parcelable> stepBundles = new ArrayList<Parcelable>(imageSteps.size());
            for (ImageStep imageStep : imageSteps) {
                stepBundles.add(imageStep.toBundle());
            }
            bundle.putParcelableArrayList(Constants.CLOTHING_FIELD_STEPS, stepBundles);
        }
        return bundle;
    }

    public static Clothing fromBundle(Bundle bundle) {
        Clothing clothing = new Clothing();
        clothing.titleText = bundle.getString(Constants.CLOTHING_FIELD_TITLE);
        clothing.summaryText = bundle.getString(Constants.CLOTHING_FIELD_SUMMARY);
        clothing.clothingImage = bundle.getString(Constants.CLOTHING_FIELD_IMAGE);
        clothing.featuresText = bundle.getString(Constants.CLOTHING_FIELD_FEATURES);
        ArrayList<Parcelable> stepBundles =
                bundle.getParcelableArrayList(Constants.CLOTHING_FIELD_STEPS);
        if (stepBundles != null) {
            for (Parcelable stepBundle : stepBundles) {
            	clothing.imageSteps.add(ImageStep.fromBundle((Bundle) stepBundle));
            }
        }
        return clothing;
    }
}
