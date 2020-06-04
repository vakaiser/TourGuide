package at.htlgkr.tourguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment {

    private static final String TAG = CountryFragment.class.getSimpleName();
    //private ArrayAdapter adapter;
    //private ListView listView;

    private TextView title;
    private TextView population;
    private TextView capitol;
    private TextView desc;
    private Button sehen;
    private Button food;

    ImageView imageView;

    private OnSelectionChangedListener listener;

    private Country country;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_country, container, false);
        intitializeView(v);
        return v;
    }

    private void intitializeView(View v) {

        voidoDarkuSama(MainActivity.isDarkModeActive, v);
        /*listView = v.findViewById(R.id.frag_country_lists);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                Country item = countries.get(position);
                listener.onSelectionChanged(position, item);
            }
        });*/
        sehen = v.findViewById(R.id.frag_country_seh_btn);
        food = v.findViewById(R.id.frag_country_food_btn);

        sehen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectionChanged("sehen");
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectionChanged("food");
            }
        });


        title = v.findViewById(R.id.frag_country_title);
        population = v.findViewById(R.id.frag_country_population);
        capitol = v.findViewById(R.id.frag_country_capitol);
        desc = v.findViewById(R.id.frag_country_description);

        imageView = v.findViewById(R.id.imageView2);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: entered");
        super.onAttach(context);
        if (context instanceof OnSelectionChangedListener) {
            listener = (OnSelectionChangedListener) context;
        } else {
            Log.d(TAG, "onAttach: Activity does not implement OnSelectionChangedListener");
        }
    }


    public void showInformation(Country item) {

        DecimalFormat df1 = new DecimalFormat( "#,###,###,###" );

        title.setText(item.getName());
        population.setText(df1.format(item.getPopulation()) + " Einwohner");
        capitol.setText("Hauptstadt: " + item.getCapitol());
        desc.setText(item.getDescription());

        //Picasso.get().load((new CountryAdapter(null, , null)).countryPicture(item)).resize(750, 520).into(imageView);
        Picasso.get().load(CountryAdapter.countryPicture(item)).resize(750, 520).into(imageView);
    }

    private void voidoDarkuSama(boolean isDarkMode, View v) {

        LinearLayout backgroundC = v.findViewById(R.id.backgroundCountry);
        if(isDarkMode){
            backgroundC.setBackgroundColor(Color.parseColor("#1a1a1a"));


            ((TextView) v .findViewById(R.id.frag_country_description)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_capitol)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_title)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_population)).setTextColor(Color.parseColor("#E4E4E4"));
        }
        else{
            backgroundC.setBackgroundColor(Color.WHITE);

            ((TextView) v .findViewById(R.id.frag_country_description)).setTextColor(Color.parseColor("#1a1a1a"));
            ((TextView) v .findViewById(R.id.frag_country_capitol)).setTextColor(Color.parseColor("#1a1a1a"));
            ((TextView) v .findViewById(R.id.frag_country_title)).setTextColor(Color.parseColor("#1a1a1a"));
            ((TextView) v .findViewById(R.id.frag_country_population)).setTextColor(Color.parseColor("#1a1a1a"));
        }
    }


}
