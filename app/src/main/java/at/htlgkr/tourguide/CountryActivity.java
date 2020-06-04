package at.htlgkr.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

public class CountryActivity extends AppCompatActivity implements OnSelectionChangedListener {

    private DetailFragment detailFragment;
    private boolean showDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_details_real);
        showDetails = detailFragment != null && detailFragment.isInLayout();
    }

    @Override
    public void onSelectionChanged(int pos, Country item) {
        if (showDetails) detailFragment.showInformation(pos, item);
        else callDetails(pos, item);
    }

    private void callDetails(int pos, Country item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("pos", pos);
        intent.putExtra("item", (Serializable)item);
        startActivity(intent);
    }
}
