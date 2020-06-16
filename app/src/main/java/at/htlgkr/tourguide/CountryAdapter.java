package at.htlgkr.tourguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryAdapter extends BaseAdapter {

    private Context context;
    private List<Country> countries;
    private int layoutId ;
    private LayoutInflater inflater;
    private SharedPreferences sharedPreferences;

    public CountryAdapter(Context context, int layoutId, List<Country> countries, SharedPreferences sharedPreferences) {
        this.context = context;
        this.countries = countries;
        this.layoutId = layoutId;
        this.inflater = ( LayoutInflater ) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View listItem = (convertView == null) ? inflater.inflate( this.layoutId, null ) : convertView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.country_view_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Country currentCountry = (Country) getItem(position);
        Picasso.get().load(countryPicture(currentCountry)).resize(225,150).into(viewHolder.imageView);

        ((TextView)(convertView.findViewById(R.id.countryMainName))).setText(currentCountry.getName());

        darkMode(listItem);

        notifyDataSetChanged();

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.imageView);
        }
    }

    private void darkMode(View listItem) {
        if (sharedPreferences.getBoolean("preference_dark_mode", false)) {
            ((TextView) listItem .findViewById(R.id.countryMainName)).setTextColor(Color.parseColor("#E4E4E4"));
        }
        else {
            ((TextView) listItem .findViewById(R.id.countryMainName)).setTextColor(Color.parseColor("#1a1a1a"));
        }
    }

    public static String countryPicture(Country c) {
        String url = "";
        switch(c.getName().toUpperCase()){
            case "FRANKREICH":
                url = "http://www.nationalflaggen.de/media/flags/flagge-frankreich.gif";
                break;

            case "FINNLAND":
                url = "https://node01.flagstat.net/bilder/finnland-flagge.png";
                break;

            case "LIECHTENSTEIN":
                url = "https://cdn.pixabay.com/photo/2013/07/13/14/16/liechtenstein-162343_960_720.png";
                break;

            case "ITALIEN":
                url = "http://picture.yatego.com/images/4d1a2974936fc5.8/Italien-hjg/flagge-fahne-italien-90-x-150-cm.gif";
                break;

            case "KANADA":
                url = "http://3.bp.blogspot.com/-w3UXa9dRM2Y/UDTJPXp_LdI/AAAAAAAAACY/v9k2fv5fmsY/s1600/flagge-kanada.jpg";
                break;

            case "CHINA":
                url = "http://flags.fmcdn.net/data/flags/w580/cn.png";
                break;

            case "JAPAN":
                url = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9e/Flag_of_Japan.svg/1200px-Flag_of_Japan.svg.png";
                break;

            case "NEUSEELAND":
                url = "https://2.bp.blogspot.com/-rHKgUK7V2KI/WLTcs944Q-I/AAAAAAAAUIs/VihKuLvXjngh6Ktx5jhp30N8oNECAHgtwCLcB/s1600/e35f56e94fb50a591864296856d5a74e_new-zealand-flag-vector-art-new-zealand-flag-clipart_534-320.jpeg";
                break;

            case "SERBIEN":
                url = "https://libertyflagandbanner.com/wp-content/uploads/2015/08/serbia-flag.jpg";
                break;

            case "AEGYPTEN":
                url = "http://2.bp.blogspot.com/-_8K5c8E0fwc/Tp4kGBcBHuI/AAAAAAAAADo/h467zYIfCzs/s1600/flagge-aegypten.gif";
                break;

            case "KENIA":
                url = "https://cdn.webshopapp.com/shops/94414/files/52385976/flagge-von-kenia.jpg";
                break;

            case "FEUERLAND":
                url = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Bandera_de_la_Provincia_de_Tierra_del_Fuego.svg/1024px-Bandera_de_la_Provincia_de_Tierra_del_Fuego.svg.png";
                break;

            case "BOTSWANA":
                url = "https://www.worldatlas.com/webimage/flags/countrys/zzzflags/bwlarge.gif";//"https://upload.wikimedia.org/wikipedia/commons/f/fa/Flag_of_Botswana.svg";
                break;

            case "BULGARIEN":
                url = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Flag_of_Bulgaria.svg/1000px-Flag_of_Bulgaria.svg.png";
                break;

            default:
                url = "https://www.optikunde.de/farbe/image/weiss-white.png";
        }

        return url;
    }

}
