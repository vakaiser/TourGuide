package at.htlgkr.tourguide.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.app.ActivityCompat;
import at.htlgkr.tourguide.MainActivity;
import at.htlgkr.tourguide.R;
import at.htlgkr.tourguide.Request_GET;
import at.htlgkr.tourguide.preferences.SettingsActivity;

public class DiaryActivity extends AppCompatActivity {

    static List<Diary> diaryList;
    static DiaryAdapter diaryAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RQ_WRITE_STORAGE = 12345;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //String fullPath;
    public static String filenameJSON = "filefile.json";


    static boolean edit = false;
    static boolean gps = false;
    static Diary currentNote = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(diaryList == null) diaryList = new ArrayList<>();
        ListView listView = findViewById(R.id.diary_listview);
        registerForContextMenu(listView);

        voidoDarkuSama(MainActivity.isDarkModeActive);


        /*sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferencesChangeListener = (sharedPrefs, key) -> preferenceChanged(sharedPrefs, key);
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferencesChangeListener);*/

        File outFile = getExternalFilesDir(null);
        String path = outFile.getAbsolutePath();
        String fullPath = path + File.separator + filenameJSON;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fullPath));
            String sJson = br.lines().reduce("", (a,b)-> a+""+b);
            GsonBuilder builder = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
            Gson gson = builder.create();
            //Gson gson=new Gson();
            TypeToken<List<Diary>> typeToken=new TypeToken<List<Diary>>(){};
            diaryList = gson.fromJson(sJson, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(diaryAdapter = new DiaryAdapter(this, R.layout.lv_item_layout, diaryList, MainActivity.sharedPreferences));


        diaryAdapter.notifyDataSetChanged();
    }





    public void addNewDiaryEntry(View view) {
        gps = false;
        edit = false;
        Intent in = new Intent(this, AddDiary.class);
        startActivity(in);
        //View vDialog = getAddDialog();

        /*new AlertDialog.Builder(this)
                .setTitle("Neuer Entry")
                .setView(vDialog)
                .setPositiveButton("Hinzufügen", (dialog, which) -> {
                    try {
                    String country = countryField.getText().toString();
                    LocalDate date = LocalDate.parse(dateField.getText().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    String description = descriptionField.getText().toString();

                        diaryList.add(new Diary(country, date, description));

                        diaryAdapter.notifyDataSetChanged();
                        printInput(this.getCurrentFocus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //addEntry(vDialog);

                    //preferenceChanged(getSharedPreferences("preference_dark_mode", Context.MODE_PRIVATE), "preference_dark_mode");


                })
                .setNegativeButton("Abbrechen", null)
                .show();

        diaryAdapter.notifyDataSetChanged();*/
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //write it here pls => contextMenuInfo gibt dir das aktuelle Item(bzw. die Info über das Item) aus. Damit können wir zum Beispiel ermitteln welche Node wir bei delete entfernen müssen. B)
        AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterView.AdapterContextMenuInfo ) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_deleteItem:
                diaryList.remove(contextMenuInfo.position);
                diaryAdapter.notifyDataSetChanged();
                printInput(this.getCurrentFocus());
                break;

            case R.id.menu_editItem:
                gps = false;
                edit = true;
                currentNote = diaryList.get(contextMenuInfo.position);
                Intent in = new Intent(this, AddDiary.class);
                startActivity(in);
                /*currentNote = diaryList.get(contextMenuInfo.position);
                View vDialog = getAddDialog(currentNote.getCountry(), currentNote.getDate(), currentNote.getDescription());
                new AlertDialog.Builder(this)
                        .setTitle("Eintrag bearbeiten")
                        .setView(vDialog)
                        .setPositiveButton("Speichern", (dialog, which) -> {

                            currentNote.setCountry(countryField.getText().toString());
                            if (!currentNote.getDate().toString().equals(dateField.getText().toString())) currentNote.setDate(LocalDate.parse(dateField.getText().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                            currentNote.setDescription(descriptionField.getText().toString());

                            diaryAdapter.notifyDataSetChanged();
                            printInput(this.getCurrentFocus());
                        })
                        .setNegativeButton("Abbrechen", null)
                        .show();
                diaryList.set(contextMenuInfo.position, currentNote);

                //cLolPost(toDoList.get(contextMenuInfo.position).getId(), vDialog, toDoList.get(contextMenuInfo.position));

                diaryAdapter.notifyDataSetChanged();*/

                break;
            case R.id.menu_detailsItem:
                currentNote = diaryList.get(contextMenuInfo.position);
                new AlertDialog.Builder(this)
                        .setTitle("Details von " +currentNote.getCountry())
                        .setMessage(currentNote.getDescription()) //it is deleted
                        .show();
                break;
        }
        diaryAdapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.context_menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
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


            out.write(gson.toJson(diaryList));

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //View vDialog = getAddDialog2();
        View alert = getLayoutInflater().inflate(R.layout.dialog_delete_diary, null);
        switch (item.getItemId()) {
            case R.id.menu_saveItem:
                gps = true;
                edit = false;
                Intent in = new Intent(this, AddDiary.class);
                startActivity(in);
                //addNewDiaryEntry(vDialog);
                //addGPSDiaryEntry(vDialog);
                break;

            case R.id.menu_deleteJson:
                deleteAlert(alert);
                break;

            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }

        diaryAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);

    }

    /*private void addGPSDiaryEntry(View v) {

        Intent in = new Intent(this, AddDiary.class);
        startActivity(in);

        countryField.setText(gpsStuff());
        dateField.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()));
        countryField.setFocusable(false);
        dateField.setFocusable(false);

        new AlertDialog.Builder(this)
                .setTitle("Neuer Entry")
                .setView(v)
                .setPositiveButton("Hinzufügen", (dialog, which) -> {
                    try {
                        String country = countryField.getText().toString();
                        LocalDate date = LocalDate.parse(dateField.getText().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        String description = descriptionField.getText().toString();

                        diaryList.add(new Diary(country, date, description));

                        diaryAdapter.notifyDataSetChanged();
                        printInput(this.getCurrentFocus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //addEntry(vDialog);

                    //preferenceChanged(getSharedPreferences("preference_dark_mode", Context.MODE_PRIVATE), "preference_dark_mode");


                })
                .setNegativeButton("Abbrechen", null)
                .show();

        diaryAdapter.notifyDataSetChanged();
    }*/


    private void deleteAlert(View v) {

        new AlertDialog.Builder(this)
                .setTitle("Reisetagebuch wirklich leeren?")
                .setView(v)
                .setPositiveButton("Leeren", (dialog, which) -> {
                    try {
                        diaryList.clear();
                        //diaryAdapter.notifyDataSetChanged();

                        File outFile = getExternalFilesDir(null);

                        String path = outFile.getAbsolutePath();
                        String fullPath = path + File.separator + DiaryActivity.filenameJSON;

                        Files.delete(Paths.get(fullPath));

                        diaryAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                })
                .setNegativeButton("Abbrechen", null)
                .show();

        diaryAdapter.notifyDataSetChanged();
    }

    private void voidoDarkuSama(boolean isDarkMode) {

        //LinearLayout backgroundC = findViewById(R.id.backgroundCountry);
        LinearLayout backgroundD = findViewById(R.id.backgroundDiary);
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
