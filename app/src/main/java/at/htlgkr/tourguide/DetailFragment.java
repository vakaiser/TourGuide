package at.htlgkr.tourguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private ListView listView;
    private ArrayAdapter adapter;
    private List<Sehenswuerdigkeiten> sehenswuerdigkeiten;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        intitializeView(view);
        return view;
    }

    private void intitializeView(View v) {
        listView = v.findViewById(R.id.frag_detail_list);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void showInformation(String type, Country item) {
        sehenswuerdigkeiten = (type.equals("sehen")) ? item.getPlaces() : item.getFoods();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sehenswuerdigkeiten);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        /*listView.setOnItemClickListener((parent, view1, position, id) -> {
            Sehenswuerdigkeiten e = sehenswuerdigkeiten.get(position);
            openMaps(e.getStreet() + " " + e.getPostcode() + " " + e.getCountry());
        });*/
    }

}
