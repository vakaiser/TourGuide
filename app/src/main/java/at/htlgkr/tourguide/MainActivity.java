package at.htlgkr.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Country> countries = new ArrayList<>();
    private static final String ASSET_NAME = "Assets_uwu.txt";

    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readAssets();
        //System.out.println(countries);

        GridView gridView = findViewById(R.id.main_countries);

        countryAdapter = new CountryAdapter(this, countries);
        gridView.setAdapter(countryAdapter);

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
}
