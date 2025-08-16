/**
 * PetProfileActivity.java
 *
 * Activity that displays the profile details of a specific pet.
 * It shows key pet information such as name, species, breed, age, birthdate, and notes.
 * Users can also edit the pet’s details, view health logs, schedule appointments,
 * and cancel the latest appointment from health logs.
 *
 * Key Features:
 *   - Retrieves and displays a pet’s details from the Room database.
 *   - Allows editing pet details via AddPetActivity.
 *   - Provides quick access to HealthLogActivity for medical records.
 *   - Provides quick access to AppointmentActivity for scheduling.
 *   - Cancel the latest appointment (logType = "Appointment") directly from here.
 *
 * Authors:
 *   Rasna Husain
 *   Chanroop Randhawa
 *
 * Last Updated: August 15, 2025
 */

package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.dao.PetDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.HealthLog;
import com.example.PetPal.model.Pet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PetProfileActivity extends AppCompatActivity {

    // --- UI Components ---
    private TextView petNameTextView;
    private TextView speciesTextView;
    private TextView breedTextView;
    private TextView ageTextView;
    private TextView birthdateTextView;
    private TextView notesTextView;
    private TextView petIdTextView;
    private Button backButton;
    private Button editButton;
    private Button viewHealthLogsButton;
    private Button makeAppointmentButton;
    private Button cancelAppointmentButton;

    // --- Database Access ---
    private PetDao petDao;
    private HealthLogDao healthLogDao;
    private int petId;
    private int userId;
    private Pet currentPet;

    // --- Threading ---
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // --- Intent Keys ---
    public static final String EXTRA_PET_ID = "com.example.PetPal.pet_id";
    public static final String EXTRA_USER_ID = "com.example.PetPal.user_id";

    /**
     * Factory method to create an intent for this activity.
     *
     * @param context The calling context.
     * @param petId   The ID of the pet to display.
     * @param userId  The ID of the user who owns the pet.
     * @return Configured Intent for launching PetProfileActivity.
     */
    public static Intent newIntent(Context context, int petId, int userId) {
        Intent intent = new Intent(context, PetProfileActivity.class);
        intent.putExtra(EXTRA_PET_ID, petId);  // Ensure EXTRA_PET_ID is passed correctly
        intent.putExtra(EXTRA_USER_ID, userId);  // Ensure EXTRA_USER_ID is passed correctly
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        // --- Bind UI Components ---
        petNameTextView = findViewById(R.id.pet_name_text_view);
        petIdTextView = findViewById(R.id.pet_id_text_view);
        speciesTextView = findViewById(R.id.species_text_view);
        breedTextView = findViewById(R.id.breed_text_view);
        ageTextView = findViewById(R.id.age_text_view);
        birthdateTextView = findViewById(R.id.birthdate_text_view);
        notesTextView = findViewById(R.id.notes_text_view);
        backButton = findViewById(R.id.back_button_profile);
        editButton = findViewById(R.id.edit_pet_button);
        viewHealthLogsButton = findViewById(R.id.view_health_logs_button);
        makeAppointmentButton = findViewById(R.id.make_appointment_button);
        cancelAppointmentButton = findViewById(R.id.cancel_appointment_button);

        // --- Setup Database ---
        AppDatabase db = AppDatabase.getDatabase(this);
        petDao = db.petDao();
        healthLogDao = db.healthLogDao();

        // --- Retrieve IDs ---
        petId = getIntent().getIntExtra(EXTRA_PET_ID, -1);
        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        if (petId == -1 || userId == -1) {
            Toast.makeText(this, "Error: Pet or User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // --- Button Actions ---
        backButton.setOnClickListener(v -> finish());

        editButton.setOnClickListener(v -> {
            Intent intent = AddPetActivity.newIntent(PetProfileActivity.this, userId, petId);
            startActivity(intent);
        });

        viewHealthLogsButton.setOnClickListener(v -> {
            if (currentPet != null) {
                Intent intent = HealthLogActivity.newIntent(
                        PetProfileActivity.this,
                        currentPet.getPetId(),
                        currentPet.getPetName()
                );
                startActivity(intent);
            }
        });

        makeAppointmentButton.setOnClickListener(v -> {
            Intent intent = AppointmentActivity.newIntent(PetProfileActivity.this, petId);
            startActivity(intent);
        });

        cancelAppointmentButton.setOnClickListener(v -> cancelLatestAppointment());

        // --- Load Data ---
        loadPetDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPetDetails();
    }

    /**
     * Loads pet details from the database and updates the UI.
     */
    private void loadPetDetails() {
        executor.execute(() -> {
            currentPet = petDao.getPetById(petId);
            mainHandler.post(() -> {
                if (currentPet != null) {
                    petNameTextView.setText(currentPet.getPetName());
                    petIdTextView.setText("Pet ID: " + currentPet.getPetId());
                    speciesTextView.setText("Species: " + currentPet.getSpecies());
                    breedTextView.setText("Breed: " + currentPet.getBreed());
                    ageTextView.setText("Age: " + currentPet.getAge());
                    birthdateTextView.setText("Birthdate: " + currentPet.getBirthdate());
                    notesTextView.setText("Notes: " + currentPet.getNotes());
                } else {
                    Toast.makeText(this, "Pet not found.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }

    /**
     * Cancels the latest appointment by deleting the most recent "Appointment" HealthLog.
     */
    private void cancelLatestAppointment() {
        executor.execute(() -> {
            HealthLog latestAppointment = healthLogDao.getLatestAppointmentForPet(petId);

            if (latestAppointment == null) {
                mainHandler.post(() ->
                        Toast.makeText(PetProfileActivity.this, "No appointment found to cancel.", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Confirm cancel with AlertDialog
            mainHandler.post(() -> new AlertDialog.Builder(PetProfileActivity.this)
                    .setTitle("Cancel Appointment")
                    .setMessage("Are you sure you want to cancel the latest appointment?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        executor.execute(() -> {
                            healthLogDao.delete(latestAppointment);
                            mainHandler.post(() ->
                                    Toast.makeText(PetProfileActivity.this, "Appointment canceled.", Toast.LENGTH_SHORT).show()
                            );
                        });
                    })
                    .setNegativeButton("No", null)
                    .show());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
