package at.htlgkr.tourguide.diary;

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

import at.htlgkr.tourguide.R;

public class DiaryAdapter extends BaseAdapter {

    private List<Diary> diaryList = new ArrayList<>();
    private int layoutId ;
    private LayoutInflater inflater;
    private SharedPreferences sharedPreferences;

    public DiaryAdapter(Context ctx, int layoutId, List<Diary> diaryList, SharedPreferences sharedPreferences) {
        this.diaryList = diaryList;
        this.layoutId = layoutId ;
        this.inflater = ( LayoutInflater ) ctx .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public int getCount() {
        return diaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return diaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Diary diary = diaryList.get(position);
        View listItem = (view == null) ? inflater.inflate( this.layoutId, null ) : view;

        ((TextView) listItem.findViewById(R.id.txt_country)).setText(diary.getCountry());
        ((TextView) listItem.findViewById(R.id.txt_date)).setText(diary.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        darkMode(listItem);

        return listItem;
    }

    private void darkMode(View listItem) {
        if (sharedPreferences.getBoolean("preference_dark_mode", false)) {
            ((TextView) listItem .findViewById(R.id.txt_country)).setTextColor(Color.parseColor("#E4E4E4"));
            ((TextView) listItem .findViewById(R.id.txt_date)).setTextColor(Color.parseColor("#E4E4E4"));
        }
        else {
            ((TextView) listItem .findViewById(R.id.txt_country)).setTextColor(Color.parseColor("#1a1a1a"));
            ((TextView) listItem .findViewById(R.id.txt_date)).setTextColor(Color.parseColor("#1a1a1a"));
        }
    }
}
