package project.adf.id.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mNotifyButton, mNotifyupdate, mNotifyCancel;
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyButton = (Button) findViewById(R.id.notify);
        mNotifyupdate = (Button) findViewById(R.id.update);
        mNotifyCancel = (Button) findViewById(R.id.cancel);

        mNotifyButton.setEnabled(true);
        mNotifyupdate.setEnabled(false);
        mNotifyCancel.setEnabled(false);

        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                sendNotification();
            }
        });

        mNotifyupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                updateNotification();
            }
        });

        mNotifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                cancelNotification();
            }
        });
    }

    public void sendNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifsound);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        android.support.v4.app.NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You have been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setSound(alarmSound);
        Notification myNotification = notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID, myNotification);
        mNotifyButton.setEnabled(false);
        mNotifyupdate.setEnabled(true);
        mNotifyCancel.setEnabled(true);
    }

    public void updateNotification() {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You have been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                        .setBigContentTitle("Notification updated!"));
        Notification myNotification = notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID, myNotification);
        mNotifyButton.setEnabled(false);
        mNotifyupdate.setEnabled(false);
        mNotifyCancel.setEnabled(true);
    }


    public void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        mNotifyButton.setEnabled(true);
        mNotifyupdate.setEnabled(false);
        mNotifyCancel.setEnabled(false);
    }
}
