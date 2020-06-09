package at.htlgkr.tourguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import at.htlgkr.tourguide.preferences.SettingsActivity;

public class NotificationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        //setContentView(R.layout.activity_main);
    }



}
