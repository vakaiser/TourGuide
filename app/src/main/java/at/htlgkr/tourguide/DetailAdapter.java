package at.htlgkr.tourguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import at.htlgkr.tourguide.diary.Diary;

public class DetailAdapter extends BaseAdapter {

    private List<Sehenswuerdigkeiten> sehenswuerdigkeitenList = new ArrayList<>();
    private int layoutId ;
    private LayoutInflater inflater;
    private SharedPreferences sharedPreferences;

    public DetailAdapter(Context ctx, int layoutId, List<Sehenswuerdigkeiten> sehenswuerdigkeitenList, SharedPreferences sharedPreferences) {
        this.sehenswuerdigkeitenList = sehenswuerdigkeitenList;
        this.layoutId = layoutId ;
        this.inflater = ( LayoutInflater ) ctx .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public int getCount() {
        return sehenswuerdigkeitenList.size();
    }

    @Override
    public Object getItem(int pp) {
        return sehenswuerdigkeitenList.get(pp);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sehenswuerdigkeiten sehenswuerdigkeiten = sehenswuerdigkeitenList.get(position);
        View listItem = (convertView == null) ? inflater.inflate( this.layoutId, null ) : convertView;

        ((TextView) listItem.findViewById(R.id.txt_seerobbe)).setText(sehenswuerdigkeiten.getName());

        darkMode(listItem);

        return listItem;
    }

    private void darkMode(View listItem) {
        if (sharedPreferences.getBoolean("preference_dark_mode", false)) {
            ((TextView) listItem .findViewById(R.id.txt_seerobbe)).setTextColor(Color.parseColor("#E4E4E4"));
        }
        else {
            ((TextView) listItem .findViewById(R.id.txt_seerobbe)).setTextColor(Color.parseColor("#1a1a1a"));
        }
    }
}
