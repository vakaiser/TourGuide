package at.htlgkr.tourguide.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import at.htlgkr.tourguide.MainActivity;
import at.htlgkr.tourguide.R;
import at.htlgkr.tourguide.preferences.SettingsActivity;

public class AddDiary extends AppCompatActivity {


    private EditText countryField, dateField, descriptionField;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RQ_WRITE_STORAGE = 12345;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //String fullPath;
    public static String filenameJSON = "filefile.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);

        if (DiaryActivity.gps) {
            countryField.setText(DiaryActivity.result);
            dateField.setText(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDate.now()));
            countryField.setFocusable(false);
            dateField.setFocusable(false);
        }
    }

    private View getAddDialog() {
        return getAddDialog(null, null, null);
    }

    private View getAddDialog(String title, LocalDate localDate, String text) {
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog_add_diary, null);
        countryField = vDialog.findViewById(R.id.countryField);
        dateField = vDialog.findViewById(R.id.dateField);
        descriptionField = vDialog.findViewById(R.id.descriptionField);
        if (title != null) {
            countryField.setText(title);
        }
        if (text != null) {
            descriptionField.setText(text);
        }
        if (localDate != null) {
            dateField.setText(localDate.toString());
        }
        dateField.setOnClickListener(v -> {
            Calendar currentDate = Calendar.getInstance();
            if (!dateField.getText().toString().isEmpty()) {
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
                dateField.setText(ld.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
            mDatePicker.setTitle("Select date");
            mDatePicker.show();
        });
        return vDialog;
    }

    private View getAddDialog2() {
        return getAddDialog2(null, null, null);
    }

    private View getAddDialog2(String title, LocalDate localDate, String text) {
        final View vDialog = getLayoutInflater().inflate(R.layout.dialog_add_diary, null);
        countryField = vDialog.findViewById(R.id.countryField);
        dateField = vDialog.findViewById(R.id.dateField);
        descriptionField = vDialog.findViewById(R.id.descriptionField);
        if (title != null) {
            countryField.setText(title);
        }
        if (text != null) {
            descriptionField.setText(text);
        }
        if (localDate != null) {
            dateField.setText(localDate.toString());
        }
        return vDialog;
    }


    public void addEntry(View view) {
        View vDialog = getAddDialog();
        View eDialog = getAddDialog2();
        if(DiaryActivity.edit){

        }
        else if (DiaryActivity.gps) {
            addDiaryEntry(eDialog);
        }
        else {
            addDiaryEntry(vDialog);
        }

    }

    private void addDiaryEntry(View v) {

        try {
            String country = countryField.getText().toString();
            LocalDate date = LocalDate.parse(dateField.getText().toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String description = descriptionField.getText().toString();

            DiaryActivity.diaryList.add(new Diary(country, date, description));

            DiaryActivity.diaryAdapter.notifyDataSetChanged();
            printInput(this.getCurrentFocus());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DiaryActivity.diaryAdapter.notifyDataSetChanged();
        Intent in = new Intent(this, DiaryActivity.class);
        startActivity(in);
    }

    public void notAddEntry(View view) {
        Intent in = new Intent(this, DiaryActivity.class);
        startActivity(in);
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
}
