package at.htlgkr.tourguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment {

    private static final String TAG = CountryFragment.class.getSimpleName();
    private ArrayAdapter adapter;
    private ListView listView;
    private TextView title;
    private TextView population;
    private TextView capitol;
    private TextView desc;
    private OnSelectionChangedListener listener;

    private List<Country> countries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_country, container, false);
        readExtra();
        intitializeView(v);
        showInfo();
        return v;
    }

    private void intitializeView(View v) {
        listView = v.findViewById(R.id.frag_country_lists);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, countries);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                Country item = countries.get(position);
                listener.onSelectionChanged(position, item);
            }
        });

        title = v.findViewById(R.id.frag_country_title);
        population = v.findViewById(R.id.frag_country_population);
        capitol = v.findViewById(R.id.frag_country_capitol);
        desc = v.findViewById(R.id.frag_country_description);
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

    private void readExtra() {
        Intent intent = getIntent();
        if (intent == null) return;
        DetailFragment deets = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.frag_deets);
        int pos = intent.getIntExtra("pos", -1);
        Department item = (Department)intent.getSerializableExtra("item");
        deets.showInformation(pos, item);
    }

    private void showInfo() {

    }


}
