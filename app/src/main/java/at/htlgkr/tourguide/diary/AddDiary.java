package at.htlgkr.tourguide.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import at.htlgkr.tourguide.Country;
import at.htlgkr.tourguide.MainActivity;
import at.htlgkr.tourguide.R;
import at.htlgkr.tourguide.Request_GET;

public class AddDiary extends AppCompatActivity {


    private EditText countryField2, dateField2, descriptionField2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RQ_WRITE_STORAGE = 12345;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //String fullPath;
    public static String filenameJSON = "filefile.json";

    private String result = "uwugang";

    private LocationManager locationManager;
    private boolean gpsGo = false;
    private static final String API_TOKEN = "71a976ecac8c81";

    private Criteria criteria;
    private String provider;

    private View vDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);




        //GPS
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 23456);
        } else {
            gpsGo = true;
        }

        if (gpsGo) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setCostAllowed(false);
            provider = locationManager.getBestProvider(criteria, false);
        }


        if (DiaryActivity.gps) {
            vDialog = getAddDialog2();
            gpsStuff();
            countryField2.setText(result);
            dateField2.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()));
            countryField2.setFocusable(false);
            dateField2.setFocusable(false);
        }
        else vDialog = getAddDialog();


        TextView uwutown = findViewById(R.id.textViewLOL);
        if (DiaryActivity.edit) {
            Button uwubutton = findViewById(R.id.btn_hinzufuegen);
            uwutown.setText("Eintrag editieren");
            uwubutton.setText("Editieren");

            countryField2.setText(DiaryActivity.currentNote.getCountry());
            dateField2.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(DiaryActivity.currentNote.getDate()));
            descriptionField2.setText(DiaryActivity.currentNote.getDescription());
        }

        if (MainActivity.isDarkModeActive) {
            voidoDarkuSama(true);
            countryField2.setTextColor(Color.parseColor("#E4E4E4"));
            countryField2.setHintTextColor(Color.parseColor("#404040"));
            dateField2.setTextColor(Color.parseColor("#E4E4E4"));
            dateField2.setHintTextColor(Color.parseColor("#404040"));
            descriptionField2.setTextColor(Color.parseColor("#E4E4E4"));
            descriptionField2.setHintTextColor(Color.parseColor("#404040"));
            uwutown.setTextColor(Color.parseColor("#E4E4E4"));
        }
        else {
            voidoDarkuSama(false);
            countryField2.setTextColor(Color.parseColor("#1a1a1a"));
            dateField2.setTextColor(Color.parseColor("#1a1a1a"));
            descriptionField2.setTextColor(Color.parseColor("#1a1a1a"));
            uwutown.setTextColor(Color.parseColor("#1a1a1a"));
        }



    }

    private View getAddDialog() {
        return getAddDialog(null, null, null);
    }

    private View getAddDialog(String title, LocalDate localDate, String text) {
        //final View vDialog = getLayoutInflater().inflate(R.layout.activity_add_diary, null);
        countryField2 = findViewById(R.id.countryField2);
        dateField2 = findViewById(R.id.dateField2);
        descriptionField2 = findViewById(R.id.descriptionField2);
        if (title != null) {
            countryField2.setText(title);
        }
        if (text != null) {
            descriptionField2.setText(text);
        }
        if (localDate != null) {
            dateField2.setText(localDate.toString());
        }
        try {
            dateField2.setOnClickListener(v -> {
                Calendar currentDate = Calendar.getInstance();
                if (!dateField2.getText().toString().isEmpty()) {
                    try {
                        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        currentDate.setTime(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                DatePickerDialog mDatePicker = new DatePickerDialog(AddDiary.
                        this, (datePicker, year, month, dayOfMonth) -> {
                    Calendar calInput = Calendar.getInstance();
                    calInput.set(year, month, dayOfMonth);

                    LocalDate ld = calInput.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    dateField2.setText(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vDialog;
    }

    private View getAddDialog2() {
        return getAddDialog2(null, null, null);
    }

    private View getAddDialog2(String title, LocalDate localDate, String text) {
        final View vDialog = getLayoutInflater().inflate(R.layout.activity_add_diary, null);
        countryField2 = findViewById(R.id.countryField2);
        dateField2 = findViewById(R.id.dateField2);
        descriptionField2 = findViewById(R.id.descriptionField2);
        if (title != null) {
            countryField2.setText(title);
        }
        if (text != null) {
            descriptionField2.setText(text);
        }
        if (localDate != null) {
            dateField2.setText(localDate.toString());
        }
        return vDialog;
    }


    public void addEntry(View view) {
        addDiaryEntry(vDialog);

    }

    private void addDiaryEntry(View v) {

        try {
            String country = countryField2.getText().toString();
            LocalDate date = LocalDate.parse(dateField2.getText().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String description = descriptionField2.getText().toString();

            if (DiaryActivity.edit) {
                DiaryActivity.currentNote.setCountry(country);
                DiaryActivity.currentNote.setDate(date);
                DiaryActivity.currentNote.setDescription(description);
            }
            else DiaryActivity.diaryList.add(new Diary(country, date, description));;


            DiaryActivity.diaryAdapter.notifyDataSetChanged();
            printInput(this.getCurrentFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiaryActivity.diaryAdapter.notifyDataSetChanged();
        /*Intent in = new Intent(this, DiaryActivity.class);
        startActivity(in);*/
        finish();
    }

    public void notAddEntry(View view) {
        /*Intent in = new Intent(this, DiaryActivity.class);
        startActivity(in);*/
        finish();
    }







    private void writeToFile(String filename) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) return;
        File outFile = getExternalFilesDir(null);
        String path = outFile.getAbsolutePath();
        String fullPath = path + File.separator + filename;
        Log.d(TAG, "filename: " + fullPath);
        try {
            PrintWriter out = new PrintWriter( new OutputStreamWriter(new FileOutputStream(fullPath)));
            //GsonBuilder builder = new GsonBuilder();
            GsonBuilder builder = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
            Gson gson = builder.create();


            out.write(gson.toJson(DiaryActivity.diaryList));

            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==RQ_WRITE_STORAGE) {
            if (grantResults.length>0 && grantResults[0]!= PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "SD Card Permission denied");
                Toast.makeText(this,
                        "SD Car Permission wurde verweigert!",
                        Toast.LENGTH_LONG).show();
            } else {
                writeToFile(filenameJSON);
            }
        }
    }

    public void printInput(View view) {
        Log.d(TAG, "entered printInput");
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED) {
            // RQ_WRITE_STORAGE ist just any constant value to identify the request
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RQ_WRITE_STORAGE);
        } else {
            writeToFile(filenameJSON);
        }
    }

    private void gpsStuff() {
        double longitude = -1;
        double latitude = -1;
        String address = "";
        if (gpsGo) {
            Location location;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 2000, 100, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            Request_GET task = new Request_GET("https://eu1.locationiq.com/v1/reverse.php?key=" + API_TOKEN + "&lat=" + latitude + "&lon=" + longitude + "&format=json");

            task.execute("");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String jsonResponse = task.getsJsonResponse();
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                address = jsonObject.getString("display_name");

            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] owo = address.split(", ");

            result = owo[owo.length - 1];
            //result = "uwu";


        }
    }


    private void voidoDarkuSama(boolean isDarkMode) {

        //LinearLayout backgroundC = findViewById(R.id.backgroundCountry);
        LinearLayout backgroundD = findViewById(R.id.backroundDiary2);
        if(isDarkMode){
            //backgroundC.setBackgroundColor(Color.parseColor("#1a1a1a"));
            backgroundD.setBackgroundColor(Color.parseColor("#1a1a1a"));
        }
        else{
            //backgroundC.setBackgroundColor(Color.parseColor("#E4E4E4"));
            backgroundD.setBackgroundColor(Color.WHITE);

        }
    }
}
