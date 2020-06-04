package at.htlgkr.tourguide;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import at.htlgkr.tourguide.diary.DiaryAdapter;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private ListView listView;
    //private ArrayAdapter adapter;
    private DetailAdapter detailAdapter;
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



        voidoDarkuSama(MainActivity.isDarkModeActive, v);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void showInformation(String type, Country item) {
        sehenswuerdigkeiten = (type.equals("sehen")) ? item.getPlaces() : item.getFoods();
        /*adapter = new ArrayAdapter(getActivity(), R.layout.detail_view_item, sehenswuerdigkeiten);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        listView.setAdapter(detailAdapter = new DetailAdapter(getContext(), R.layout.detail_view_item, sehenswuerdigkeiten, MainActivity.sharedPreferences));


        detailAdapter.notifyDataSetChanged();

        /*listView.setOnItemClickListener((parent, view1, position, id) -> {
            Sehenswuerdigkeiten e = sehenswuerdigkeiten.get(position);
            openMaps(e.getStreet() + " " + e.getPostcode() + " " + e.getCountry());
        });*/
    }

    private void voidoDarkuSama(boolean isDarkMode, View v) {

        LinearLayout backgroundDetail = v.findViewById(R.id.backgroundDetail);
        if(isDarkMode){
            backgroundDetail.setBackgroundColor(Color.parseColor("#1a1a1a"));


            /*((TextView) v .findViewById(R.id.frag_country_description)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_capitol)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_title)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) v .findViewById(R.id.frag_country_population)).setTextColor(Color.parseColor("#E4E4E4"));*/


            //((ListView) v .findViewById(R.id.frag_detail_list)).setBackgroundColor(Color.parseColor("E4E4E4"));
        }
        else{
            backgroundDetail.setBackgroundColor(Color.WHITE);



            //((ListView) v .findViewById(R.id.frag_detail_list)).setBackgroundColor(Color.parseColor("1a1a1a"));
        }
    }

}
