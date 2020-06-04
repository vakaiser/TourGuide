package at.htlgkr.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            finish();
            return;
        }
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent == null) return;
        DetailFragment deets = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_details_real);
        int pos = intent.getIntExtra("pos", -1);
        Country item = (Country) intent.getSerializableExtra("item");
        deets.showInformation(pos, item);
    }
}
