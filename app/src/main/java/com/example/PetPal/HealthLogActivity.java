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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.adapter.HealthLogAdapter;
import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.HealthLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HealthLogActivity extends AppCompatActivity {

    private RecyclerView healthLogRecyclerView;
    private HealthLogAdapter adapter;
    private HealthLogDao healthLogDao;
    private ExecutorService executorService;
    private TextView noLogsTextView;
    private Button backButton;

    private int petId;
    private String petName;

    public static Intent newIntent(Context packageContext, int petId, String petName) {
        Intent intent = new Intent(packageContext, HealthLogActivity.class);
        intent.putExtra("PET_ID", petId);
        intent.putExtra("PET_NAME", petName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_log);

        petId = getIntent().getIntExtra("PET_ID", -1);
        petName = getIntent().getStringExtra("PET_NAME");

        if (petId == -1) {
            Toast.makeText(this, "Error: Pet ID not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        healthLogRecyclerView = findViewById(R.id.health_log_recycler_view);
        noLogsTextView = findViewById(R.id.no_logs_text_view);
        backButton = findViewById(R.id.back_button_health_log);

        FloatingActionButton addLogFab = findViewById(R.id.add_log_fab);
        addLogFab.setOnClickListener(view -> {
            Intent intent = new Intent(HealthLogActivity.this, AddHealthLogActivity.class);
            intent.putExtra("PET_ID", petId);
            startActivity(intent);
        });

        AppDatabase db = AppDatabase.getDatabase(this);
        healthLogDao = db.healthLogDao();
        executorService = Executors.newSingleThreadExecutor();

        adapter = new HealthLogAdapter(this);
        healthLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        healthLogRecyclerView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        loadHealthLogs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHealthLogs();
    }

    private void loadHealthLogs() {
        executorService.execute(() -> {
            List<HealthLog> logs = healthLogDao.getLogsByPet(petId);
            runOnUiThread(() -> {
                if (logs != null && !logs.isEmpty()) {
                    adapter.setHealthLogs(logs);
                    noLogsTextView.setVisibility(View.GONE);
                    healthLogRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    noLogsTextView.setVisibility(View.VISIBLE);
                    healthLogRecyclerView.setVisibility(View.GONE);
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}