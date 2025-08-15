/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.HealthLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HealthLogDetailActivity extends AppCompatActivity {

    private TextView logTypeTextView;
    private TextView logDateTextView;
    private TextView logDescriptionTextView;
    private TextView logTreatmentTextView;
    private Button backButton;

    private HealthLogDao healthLogDao;
    private int healthLogId;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String EXTRA_HEALTH_LOG_ID = "com.example.PetPal.health_log_id";

    /**
     * Factory method to create an Intent for HealthLogDetailActivity.
     * @param packageContext The context.
     * @param healthLogId The ID of the health log to display.
     * @return An Intent to start this activity.
     */
    public static Intent newIntent(Context packageContext, int healthLogId) {
        Intent intent = new Intent(packageContext, HealthLogDetailActivity.class);
        intent.putExtra(EXTRA_HEALTH_LOG_ID, healthLogId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_log_detail);

        // Initialize UI components
        logTypeTextView = findViewById(R.id.detail_log_type);
        logDateTextView = findViewById(R.id.detail_log_date);
        logDescriptionTextView = findViewById(R.id.detail_log_description);
        logTreatmentTextView = findViewById(R.id.detail_log_treatment);
        backButton = findViewById(R.id.back_button_detail);

        // Get the health log ID from the intent
        healthLogId = getIntent().getIntExtra(EXTRA_HEALTH_LOG_ID, -1);

        if (healthLogId == -1) {
            Toast.makeText(this, "Error: Health log ID not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AppDatabase db = AppDatabase.getDatabase(this);
        healthLogDao = db.healthLogDao();

        backButton.setOnClickListener(v -> finish());

        loadHealthLogDetails();
    }

    private void loadHealthLogDetails() {
        executor.execute(() -> {
            HealthLog healthLog = healthLogDao.getLogById(healthLogId);
            mainHandler.post(() -> {
                if (healthLog != null) {
                    logTypeTextView.setText(healthLog.getType());
                    logDateTextView.setText("Date: " + healthLog.getDate());
                    logDescriptionTextView.setText("Description: " + healthLog.getDescription());
                    logTreatmentTextView.setText("Treatment: " + healthLog.getTreatment());
                } else {
                    Toast.makeText(this, "Health log not found.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}