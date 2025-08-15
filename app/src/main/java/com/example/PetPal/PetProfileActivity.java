/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.dao.PetDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PetProfileActivity extends AppCompatActivity {

    private TextView petNameTextView;
    private TextView speciesTextView;
    private TextView breedTextView;
    private TextView ageTextView;
    private TextView birthdateTextView;
    private TextView notesTextView;
    private Button backButton;
    private Button editButton;
    private Button viewHealthLogsButton;
    private Button makeAppointmentButton;

    private PetDao petDao;
    private int petId;
    private int userId;
    private Pet currentPet;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String EXTRA_PET_ID = "com.example.PetPal.pet_id";
    private static final String EXTRA_USER_ID = "com.example.PetPal.user_id";

    public static Intent newIntent(Context packageContext, int petId, int userId) {
        Intent intent = new Intent(packageContext, PetProfileActivity.class);
        intent.putExtra(EXTRA_PET_ID, petId);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        petNameTextView = findViewById(R.id.pet_name_text_view);
        speciesTextView = findViewById(R.id.species_text_view);
        breedTextView = findViewById(R.id.breed_text_view);
        ageTextView = findViewById(R.id.age_text_view);
        birthdateTextView = findViewById(R.id.birthdate_text_view);
        notesTextView = findViewById(R.id.notes_text_view);
        backButton = findViewById(R.id.back_button_profile);
        editButton = findViewById(R.id.edit_pet_button);
        viewHealthLogsButton = findViewById(R.id.view_health_logs_button);
        makeAppointmentButton = findViewById(R.id.make_appointment_button);

        AppDatabase db = AppDatabase.getDatabase(this);
        petDao = db.petDao();

        petId = getIntent().getIntExtra(EXTRA_PET_ID, -1);
        userId = getIntent().getIntExtra(EXTRA_USER_ID, -1);

        if (petId == -1 || userId == -1) {
            Toast.makeText(this, "Error: Pet or User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        backButton.setOnClickListener(v -> finish());
        editButton.setOnClickListener(v -> {
            Intent intent = AddPetActivity.newIntent(PetProfileActivity.this, userId, petId);
            startActivity(intent);
        });

        viewHealthLogsButton.setOnClickListener(v -> {
            if (currentPet != null) {
                Intent intent = HealthLogActivity.newIntent(PetProfileActivity.this, currentPet.pet_id, currentPet.pet_name);
                startActivity(intent);
            }
        });

        // Updated onClickListener to launch the new AppointmentActivity
        makeAppointmentButton.setOnClickListener(v -> {
            Intent intent = AppointmentActivity.newIntent(PetProfileActivity.this, petId);
            startActivity(intent);
        });

        loadPetDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPetDetails();
    }

    private void loadPetDetails() {
        executor.execute(() -> {
            currentPet = petDao.getPetById(petId);
            mainHandler.post(() -> {
                if (currentPet != null) {
                    petNameTextView.setText(currentPet.pet_name);
                    speciesTextView.setText("Species: " + currentPet.species);
                    breedTextView.setText("Breed: " + currentPet.breed);
                    ageTextView.setText("Age: " + currentPet.age);
                    birthdateTextView.setText("Birthdate: " + currentPet.birthdate);
                    notesTextView.setText("Notes: " + currentPet.notes);
                } else {
                    Toast.makeText(this, "Pet not found.", Toast.LENGTH_SHORT).show();
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