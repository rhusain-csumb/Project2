/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */
package com.example.PetPal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.HealthLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddHealthLogActivity extends AppCompatActivity {

    private EditText logTypeEditText;
    private EditText logDateEditText;
    private EditText logDescriptionEditText;
    private EditText logTreatmentEditText; // New field for treatment
    private Button saveLogButton;
    private Button backButton;

    private HealthLogDao healthLogDao;
    private ExecutorService executorService;
    private int petId;

    public static Intent newIntent(Context packageContext, int petId) {
        Intent intent = new Intent(packageContext, AddHealthLogActivity.class);
        intent.putExtra("PET_ID", petId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_log);

        petId = getIntent().getIntExtra("PET_ID", -1);

        logTypeEditText = findViewById(R.id.log_type_edit_text);
        logDateEditText = findViewById(R.id.log_date_edit_text);
        logDescriptionEditText = findViewById(R.id.log_description_edit_text);
        logTreatmentEditText = findViewById(R.id.log_treatment_edit_text); // Find the new view
        saveLogButton = findViewById(R.id.save_log_button);
        backButton = findViewById(R.id.back_button_add_log);

        AppDatabase db = AppDatabase.getDatabase(this);
        healthLogDao = db.healthLogDao();
        executorService = Executors.newSingleThreadExecutor();

        //For date field to pick both date and time and store in the same String
         logDateEditText.setFocusable(false);
        logDateEditText.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(
                    this,
                    (view, year, month, day) -> {
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, day);

                        new TimePickerDialog(
                                this,
                                (timeView, hour, minute) -> {
                                    cal.set(Calendar.HOUR_OF_DAY, hour);
                                    cal.set(Calendar.MINUTE, minute);
                                    SimpleDateFormat fmt =
                                            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                    logDateEditText.setText(fmt.format(cal.getTime()));
                                },
                                cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE),
                                false
                        ).show();
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        saveLogButton.setOnClickListener(v -> saveHealthLog());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveHealthLog() {
        String logType = logTypeEditText.getText().toString().trim();
        String logDate = logDateEditText.getText().toString().trim();
        String logDescription = logDescriptionEditText.getText().toString().trim();
        String logTreatment = logTreatmentEditText.getText().toString().trim(); // Get the treatment text

        if (logType.isEmpty() || logDate.isEmpty() || logDescription.isEmpty() || logTreatment.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass the treatment to the HealthLog constructor
        HealthLog newLog = new HealthLog(petId, logType, logDate, logDescription, logTreatment);

        executorService.execute(() -> {
            healthLogDao.insert(newLog);
            runOnUiThread(() -> {
                Toast.makeText(this, "Health log added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
