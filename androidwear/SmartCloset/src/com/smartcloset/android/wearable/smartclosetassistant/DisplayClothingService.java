package com.smartcloset.android.wearable.smartclosetassistant;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

public class DisplayClothingService extends Service {
    private NotificationManagerCompat mNotificationManager;
    private Binder mBinder = new LocalBinder();
    private Clothing mClothing;

    public class LocalBinder extends Binder {
    	DisplayClothingService getService() {
            return DisplayClothingService.this;
        }
    }

    @Override
    public void onCreate() {
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION_START_DRESSING)) {
            createNotification(intent);
            return START_STICKY;
        }
        return START_NOT_STICKY;
    }

    private void createNotification(Intent intent) {
        mClothing = Clothing.fromBundle(intent.getBundleExtra(Constants.EXTRA_CLOTHING));
        ArrayList<Notification> notificationPages = new ArrayList<Notification>();

        int stepCount = mClothing.imageSteps.size();

        for (int i = 0; i < stepCount; ++i) {
        	Clothing.ImageStep recipeStep = mClothing.imageSteps.get(i);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.bigText(recipeStep.stepText);
            style.setBigContentTitle(String.format(
                    getResources().getString(R.string.step_count), i + 1, stepCount));
            style.setSummaryText("");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setStyle(style);
            notificationPages.add(builder.build());
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        if (mClothing.clothingImage != null) {
            Bitmap recipeImage = Bitmap.createScaledBitmap(
                    AssetUtils.loadBitmapAsset(this, mClothing.clothingImage),
                    Constants.NOTIFICATION_IMAGE_WIDTH, Constants.NOTIFICATION_IMAGE_HEIGHT, false);
            builder.setLargeIcon(recipeImage);
        }
        builder.setContentTitle(mClothing.titleText);
        builder.setContentText(mClothing.summaryText);
        builder.setSmallIcon(R.mipmap.ic_notification_closet);
        
        Intent displayClothingIntent = new Intent(this, ShowClothingService.class);
        PendingIntent clothingPendingIntent =
                PendingIntent.getActivity(this, 0, displayClothingIntent, 0);
        

        Notification notification = builder
                .extend(new NotificationCompat.WearableExtender()
                        .addPages(notificationPages))
                        .addAction(R.mipmap.ic_notification_closet, "Display clothing", clothingPendingIntent)
                .build();
        mNotificationManager.notify(Constants.NOTIFICATION_ID, notification);
    }
}
