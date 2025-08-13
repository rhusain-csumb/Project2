/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PetPal.adapter.HealthLogAdapter;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.model.HealthLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HealthLogActivity extends AppCompatActivity {
    private RecyclerView healthLogRecycler;
    private HealthLogAdapter healthLogAdapter;
    private TextView healthLogTitle, emptyStateTextView;
    private Button addLogButton;

    private HealthLogDao healthLogDao;
    private int currentPetId;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final String EXTRA_PET_ID = "com.example.PetPal.pet_id";
    private static final String EXTRA_PET_NAME = "com.example.PetPal.pet_name";

    public static Intent newIntent(Context packageContext, int petId, String petName) {
        Intent intent = new Intent(packageContext, HealthLogActivity.class);
        intent.putExtra(EXTRA_PET_ID, petId);
        intent.putExtra(EXTRA_PET_NAME, petName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_log);

        healthLogRecycler = findViewById(R.id.health_log_recycler);
        healthLogTitle = findViewById(R.id.health_log_title);
        emptyStateTextView = findViewById(R.id.empty_state_log);
        addLogButton = findViewById(R.id.add_log_button);

        healthLogRecycler.setLayoutManager(new LinearLayoutManager(this));
        healthLogAdapter = new HealthLogAdapter(new ArrayList<>());
        healthLogRecycler.setAdapter(healthLogAdapter);

        healthLogDao = Room.databaseBuilder(this, AppDatabase.class, "pet-pal-db").build().healthLogDao();

        currentPetId = getIntent().getIntExtra(EXTRA_PET_ID, -1);
        String petName = getIntent().getStringExtra(EXTRA_PET_NAME);

        if (currentPetId == -1 || petName == null) {
            Toast.makeText(this, "Pet ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        healthLogTitle.setText("Health Logs for " + petName);
        addLogButton.setOnClickListener(v -> {
            // TODO: Implement the logic for adding a new health log
            // You will need a new activity for this
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHealthLogs();
    }

    private void loadHealthLogs() {
        executor.execute(() -> {
            List<HealthLog> logs = healthLogDao.getLogsByPet(currentPetId);
            mainHandler.post(() -> {
                healthLogAdapter.updateLogs(logs);
                if (logs.isEmpty()) {
                    emptyStateTextView.setVisibility(View.VISIBLE);
                    healthLogRecycler.setVisibility(View.GONE);
                } else {
                    emptyStateTextView.setVisibility(View.GONE);
                    healthLogRecycler.setVisibility(View.VISIBLE);
                }
            });
        });
    }
}