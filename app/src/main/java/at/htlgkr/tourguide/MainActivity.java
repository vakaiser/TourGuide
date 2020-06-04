package at.htlgkr.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.htlgkr.tourguide.diary.DiaryActivity;
import at.htlgkr.tourguide.preferences.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private List<Country> countries = new ArrayList<>();
    private static final String ASSET_NAME = "Assets_uwu.txt";

    public static SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

    private CountryAdapter countryAdapter;

    public static boolean isDarkModeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readAssets();
        //System.out.println(countries);

        GridView gridView = findViewById(R.id.main_countries);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesChangeListener = (sharedPrefs, key) -> preferenceChanged(sharedPrefs, key);

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener);



        countryAdapter = new CountryAdapter(this, R.layout.country_view_item, countries, sharedPreferences);


        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position){
                Country item = (Country) adapter.getItemAtPosition(position);

                Intent intent = new Intent(, CountryActivity.class);
                intent.putExtra("item", (Serializable)item);
                startActivity(intent);
            }
        });*/

        gridView.setOnItemClickListener((parent, v, position, id) -> {
            Country item = (Country) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, CountryActivity.class);
            intent.putExtra("item", (Serializable)item);
            startActivity(intent);
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesChangeListener = ((sharedPreferences, key) -> preferenceChanged(sharedPreferences, key));
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        isDarkModeActive = sharedPreferences.getBoolean("preference_dark_mode", false);
        voidoDarkuSama(isDarkModeActive);

        gridView.setAdapter(countryAdapter);

        countryAdapter.notifyDataSetChanged();


    }


    /*private InputStream getInputStreamForAsset(String filename) {
        AssetManager assets = getAssets();
        try {
            return assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private void readData(String assets) {
        String[] arr = assets.split(";");

        String name = "";
        int population = -1;
        String capitol = "";
        String description = "";

        for (int i = 1; i < arr.length; i++) {
            List<Sehenswuerdigkeiten> places = new ArrayList<>();
            List<Sehenswuerdigkeiten> foods = new ArrayList<>();

            String[] data = arr[i].split("#");

            String[] sight = data[4].split("!");
            String[] food = data[5].split("!");

            name = data[0];
            population = Integer.parseInt(data[1]);
            capitol = data[2];
            description = data[3];
            Arrays.stream(sight)
                    .forEach(x-> places.add(new Place(x, "null", -1, -1)));
            Arrays.stream(food)
                    .forEach(x-> foods.add(new Sehenswuerdigkeiten(x, null)));

            countries.add(new Country(name, population, capitol, description, places, foods));
        }

    }

    private void readAssets() {
        //InputStream in = getInputStreamForAsset(ASSET_NAME);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(ASSET_NAME)));
            String assets = "";
            String line = "";
            while((line = reader.readLine()) != null) {
                assets += line;
            }

            readData(assets);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //View vDialog = getAddDialog();
        switch (item.getItemId()) {
            case R.id.menu_goToDiary:
                Intent i = new Intent(this, DiaryActivity.class);
                startActivityForResult(i, 1);
                break;

            case R.id.menu_options:
                Intent in = new Intent(this, SettingsActivity.class);
                startActivity(in);
                //startActivityForResult(in, 1);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    /*              - - - PREFERENCES - - -                */

    /*private void preferenceChanged(SharedPreferences sharedPrefs, String key) {
        Map<String, ?> allEntries = sharedPrefs. getAll () ;
        boolean sValue = false;
        if ( allEntries.get(key) instanceof Boolean) {
            sValue = sharedPrefs.getBoolean(key, false);
        }
        Toast.makeText(this, key + " new Value: " + sValue, Toast.LENGTH_LONG).show();
    }*/

    private void preferenceChanged(SharedPreferences prefs, String key){
        countryAdapter.notifyDataSetChanged();

        Map<String, ?> allEntries = sharedPreferences.getAll () ;

        if(allEntries.get(key)instanceof Boolean) {
            switch (key){
                case "preference_dark_mode":
                    //GridLayout background = findViewById(R.id.background);
                    boolean isDarkMode = prefs.getBoolean("preference_dark_mode",false);

                    voidoDarkuSama(isDarkMode);
                    countryAdapter.notifyDataSetChanged();

                    break;
                default:
                    break;
            }
        }
    }

    private void voidoDarkuSama(boolean isDarkMode) {
        String lol = "uwu";
        lol = "uwu";

        LinearLayout backgroundM = findViewById(R.id.backgroundMain);
        //LinearLayout backgroundC = findViewById(R.id.backgroundCountry);
        //LinearLayout backgroundD = findViewById(R.id.backgroundDiary);
        if(isDarkMode){
            isDarkModeActive = true;
            backgroundM.setBackgroundColor(Color.parseColor("#1a1a1a"));
            //backgroundC.setBackgroundColor(Color.parseColor("#1a1a1a"));
            //backgroundD.setBackgroundColor(Color.parseColor("#1a1a1a"));
            //updateTextColor(background, Color.parseColor("#ffffff"));
            countryAdapter.notifyDataSetChanged();
        }
        else{
            isDarkModeActive = false;
            backgroundM.setBackgroundColor(Color.WHITE);
            //backgroundC.setBackgroundColor(Color.parseColor("#E4E4E4"));
            //backgroundD.setBackgroundColor(Color.parseColor("#E4E4E4"));
            //updateTextColor(background, Color.parseColor("#000000"));
            countryAdapter.notifyDataSetChanged();

        }
        countryAdapter.notifyDataSetChanged();
    }


    /*              - - - END OF PREFERENCES - - -              */
}
