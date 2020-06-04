package at.htlgkr.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

public class CountryActivity extends AppCompatActivity implements OnSelectionChangedListener {

    private DetailFragment detailFragment;
    private boolean showDetails = false;

    private Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        readExtra();

        detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_details_real);
        showDetails = detailFragment != null && detailFragment.isInLayout();
    }

    @Override
    public void onSelectionChanged(String type) {
        if (showDetails) detailFragment.showInformation(type, country);
        else callDetails(country, type);
    }

    private void callDetails(Country item, String type) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("item", (Serializable)item);
        startActivity(intent);
    }


    private void readExtra() {
        Intent intent = getIntent();
        if (intent == null) return;
        CountryFragment deets = (CountryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_country_real);
        country = (Country) intent.getSerializableExtra("item");
        deets.showInformation(country);
    }
}
