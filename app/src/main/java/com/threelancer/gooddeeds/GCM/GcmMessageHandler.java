package com.threelancer.gooddeeds.GCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.threelancer.gooddeeds.Activities.TabbedActivity;
import com.threelancer.gooddeeds.R;

import com.google.android.gms.gcm.GcmListenerService;

public class GcmMessageHandler extends GcmListenerService {


    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    private static final String server_name = "GoodDeeds";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        Log.v("myTest", "NotificationReceived");
        createNotification(from, message);
    }

    private void createNotification(String title, String body) {
        Context context = getBaseContext();

        if (title.equals(getResources().getString(R.string.sender_id))) {
            title = server_name;
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher).setContentTitle(title)
                .setSound(uri)
                .setContentText("A new Applicant has offered to fulfill your Request");

        Intent resultIntent = new Intent(this, TabbedActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(TabbedActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
