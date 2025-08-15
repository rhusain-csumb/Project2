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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.model.HealthLog;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class AppointmentActivity extends AppCompatActivity {

    private Button selectDateButton;
    private Button selectTimeButton;
    private Button saveAppointmentButton;
    private Button backButton;
    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;

    private int petId;
    private String selectedDate;
    private String selectedTime;

    private static final String EXTRA_PET_ID = "com.example.PetPal.pet_id";

    public static Intent newIntent(Context packageContext, int petId) {
        Intent intent = new Intent(packageContext, AppointmentActivity.class);
        intent.putExtra(EXTRA_PET_ID, petId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        petId = getIntent().getIntExtra(EXTRA_PET_ID, -1);
        if (petId == -1) {
            Toast.makeText(this, "Error: Pet ID not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        selectDateButton = findViewById(R.id.select_date_button);
        selectTimeButton = findViewById(R.id.select_time_button);
        saveAppointmentButton = findViewById(R.id.save_appointment_button);
        backButton = findViewById(R.id.back_button_appointment);
        selectedDateTextView = findViewById(R.id.selected_date_text_view);
        selectedTimeTextView = findViewById(R.id.selected_time_text_view);

        selectDateButton.setOnClickListener(v -> showDatePickerDialog());
        selectTimeButton.setOnClickListener(v -> showTimePickerDialog());
        saveAppointmentButton.setOnClickListener(v -> saveAppointment());
        backButton.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year1;
                    selectedDateTextView.setText("Date: " + selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> {
                    String amPm;
                    if (hourOfDay < 12) {
                        amPm = "AM";
                    } else {
                        amPm = "PM";
                    }
                    int hour12 = hourOfDay % 12;
                    if (hour12 == 0) {
                        hour12 = 12;
                    }
                    selectedTime = String.format("%02d:%02d %s", hour12, minuteOfHour, amPm);
                    selectedTimeTextView.setText("Time: " + selectedTime);
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void saveAppointment() {
    if (selectedDate == null || selectedTime == null) {
        Toast.makeText(this, "Please select a date and time.", Toast.LENGTH_SHORT).show();
        return;
    }

   
    int petId = getIntent().getIntExtra("PET_ID", -1);
    if (petId == -1) {
        Toast.makeText(this, "Missing pet context.", Toast.LENGTH_SHORT).show();
        return;
    }

    final String combined = selectedDate + " " + selectedTime; // fits existing String 'date' field

    Executors.newSingleThreadExecutor().execute(() -> {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        HealthLogDao dao = db.healthLogDao();

        HealthLog log = new HealthLog();
        log.setPetId(petId);
        log.setType("Appointment"); // reuse existing 'type' column
        log.setDate(combined); // store date + time in the existing 'date' String
        log.setDescription("Scheduled appointment");
        log.setTreatment("");         

        dao.insert(log);

        runOnUiThread(() -> {
            Toast.makeText(this, "Appointment saved: " + combined, Toast.LENGTH_LONG).show();
            finish();
        });
    });
}
