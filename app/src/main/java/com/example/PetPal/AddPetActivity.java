/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.dao.PetDao;
import com.example.PetPal.model.Pet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddPetActivity extends AppCompatActivity {
    private static final String TAG = "AddPetActivity";
    private EditText petNameInput, petSpeciesInput, petAgeInput, petBreedInput, petBirthdateInput, petNotesInput;
    private Button savePetButton, deletePetButton;

    private PetDao petDao;
    private int currentUserId;
    private int currentPetId = -1; // -1 indicates a new pet
    private Pet currentPet;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String EXTRA_USER_ID = "com.example.PetPal.user_id";
    private static final String EXTRA_PET_ID = "com.example.PetPal.pet_id";

    public static Intent newIntent(Context packageContext, int userId, int petId) {
        Intent intent = new Intent(packageContext, AddPetActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_PET_ID, petId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        petNameInput = findViewById(R.id.pet_name_input);
        petSpeciesInput = findViewById(R.id.pet_species_input);
        petAgeInput = findViewById(R.id.pet_age_input);
        petBreedInput = findViewById(R.id.pet_breed_input);
        petBirthdateInput = findViewById(R.id.pet_birthdate_input);
        petNotesInput = findViewById(R.id.pet_notes_input);
        savePetButton = findViewById(R.id.save_pet_button);
        deletePetButton = findViewById(R.id.delete_pet_button);

        petDao = Room.databaseBuilder(this, AppDatabase.class, "pet-pal-db").build().petDao();

        currentUserId = getIntent().getIntExtra(EXTRA_USER_ID, -1);
        currentPetId = getIntent().getIntExtra(EXTRA_PET_ID, -1);

        if (currentPetId != -1) {
            loadPetForEditing(currentPetId);
            deletePetButton.setVisibility(Button.VISIBLE);
            deletePetButton.setOnClickListener(v -> deletePet());
        } else {
            deletePetButton.setVisibility(Button.GONE);
        }

        savePetButton.setOnClickListener(v -> savePet());
    }

    private void loadPetForEditing(int petId) {
        executor.execute(() -> {
            currentPet = petDao.getPetById(petId);
            mainHandler.post(() -> {
                if (currentPet != null) {
                    petNameInput.setText(currentPet.pet_name);
                    petSpeciesInput.setText(currentPet.species);
                    petAgeInput.setText(String.valueOf(currentPet.age));
                    petBreedInput.setText(currentPet.breed);
                    petBirthdateInput.setText(currentPet.birthdate);
                    petNotesInput.setText(currentPet.notes);
                }
            });
        });
    }

    private void savePet() {
        String petName = petNameInput.getText().toString().trim();
        String petSpecies = petSpeciesInput.getText().toString().trim();
        String petAgeStr = petAgeInput.getText().toString().trim();
        String petBreed = petBreedInput.getText().toString().trim();
        String petBirthdate = petBirthdateInput.getText().toString().trim();
        String petNotes = petNotesInput.getText().toString().trim();

        if (petName.isEmpty() || petSpecies.isEmpty()) {
            Toast.makeText(this, "Pet name and species are required", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            if (currentPet == null) {
                currentPet = new Pet();
                currentPet.user_id = currentUserId;
            }

            currentPet.pet_name = petName;
            currentPet.species = petSpecies;
            currentPet.age = petAgeStr.isEmpty() ? 0 : Integer.parseInt(petAgeStr);
            currentPet.breed = petBreed;
            currentPet.birthdate = petBirthdate;
            currentPet.notes = petNotes;

            if (currentPetId == -1) {
                petDao.insert(currentPet);
                mainHandler.post(() -> {
                    Toast.makeText(AddPetActivity.this, "Pet added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                petDao.update(currentPet);
                mainHandler.post(() -> {
                    Toast.makeText(AddPetActivity.this, "Pet updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    private void deletePet() {
        if (currentPet != null) {
            executor.execute(() -> {
                petDao.delete(currentPet);
                mainHandler.post(() -> {
                    Toast.makeText(AddPetActivity.this, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        }
    }
}