package at.htlgkr.tourguide;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MyService extends IntentService {

    private static final String TAG = MyService.class.getName();
    private static final String CHANNEL_ID = "42069";
    private static final int NOTIFICATION_ID_STANDARD = 1;

    private static boolean notificate;

    public MyService() {
        super("Baumkuchenuda");

    }

    /*@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }*/

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: entered");
        Log.d(TAG, "onHandleIntent: Thread: " +
                Thread.currentThread().getName());

        while (true) {
            try {
                LocalTime lt = LocalTime.now();
                if (lt.equals(LocalTime.of(14,0)) || lt.equals(LocalTime.of(17,0))){//|| lt.equals(lt)) {
                    showNotification();
                }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (!MainActivity.isNotificationActive) break;
        }

        /*for (int i=1; i<=3; i++) {
            Log.d(TAG, "onHandleIntent: ....working");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "onHandleIntent: " + e.getMessage());
            }
        }*/




        //Log.d(TAG, "onHandleIntent: finished");
    }



    public void showNotification() { //View view
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(getApplicationContext(), NotificationDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Notice that the Notification.Builder constructor requires a channel ID.
        // This is required for compatibility with Android 8.0 (API level 26) and higher,
        // but is ignored by older versions.
        Notification.Builder builder  = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setColor(Color.parseColor("#ff0066"))
                //.setContentTitle("uwu")//getString(R.string.app_name))
                .setContentText("adsfasdf")//"This is just a small Notification")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(notiiificatiiooon()))
                .setWhen(System.currentTimeMillis())
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID_STANDARD, builder.build());
    }

    private String notiiificatiiooon() {
        DecimalFormat df1 = new DecimalFormat( "#,###,###,###" );
        String result = "";

        int random = (int)(Math.random()*6);
        int randomio = (int)(Math.random()*MainActivity.countries.size());
        int randididi = (int)(Math.random()*MainActivity.countries.get(randomio).getPlaces().size());
        String uwu;

        switch (random) {
            case 0:
                result = "Wie wärs mal mit einem neuen Sommerurlaubsziel?";
                break;

            case 1:
                result = "Schon mal an Urlaub gedacht?";
                break;

            case 2:
                result = "Reisetagebuch is ready to be updated!";
                break;

            case 3:
                uwu = MainActivity.countries.get(randomio).getFoods().get(randididi).toString();
                result = "Check doch verschiedenstes Essen von unterschiedlichen Ländern aus! Wie wärs mit "+uwu+"?";
                break;

            case 4:
                uwu = MainActivity.countries.get(randomio).getPlaces().get(randididi).toString();
                result = "Hast du mal "+uwu+" besucht?";

            case 5:
                uwu = MainActivity.countries.get(randomio).getName().toString();
                String owo = df1.format(MainActivity.countries.get(randomio).getPopulation());
                result = "Wusstest du schon, "+uwu+" hat "+owo+" Einwohner?";
                break;

            default:
                result = "uwu owo qwq let the uwugang rise up!";
                break;

        }


        return result;
    }

    /*public static void setFalse() {
        notificate = false;
    }
    public static void setTrue() {
        notificate = true;
    }*/

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Service: onStartCommand");
        if (intent.hasExtra("msg")) {
            Log.d(TAG, "onStartCommand: Message:" + intent.getStringExtra("msg"));
        }
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1 * 1000);
                    Log.d(TAG, "do Work within Service");
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "onStartCommand: " + e.getMessage() +
                        "\n" + e.getStackTrace());
            }
            Log.d(TAG, "onStartCommand: selfstopping service");
            stopSelf();
        }).start();

        return START_STICKY;
    }*/
}
