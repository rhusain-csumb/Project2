/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;


/**
 * AddPetActivity allows users to add, edit or delete a pet.
 */
public class AddPetActivity extends AppCompatActivity {

    private EditText nameInput, speciesInput, ageInput, breedInput, birthdateInput, notesInput;
    private Button saveButton, deleteButton;
    private AppDatabase db;
    private int userId;
    private int petId = -1;
    private Pet currentPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Get data from Intent
        userId = getIntent().getIntExtra("user_id", -1);
        petId = getIntent().getIntExtra("pet_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Missing user session. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        // Initialize DB
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries()
                .build();

        // Initialize input fields
        nameInput = findViewById(R.id.pet_name_input);
        speciesInput = findViewById(R.id.pet_species_input);
        ageInput = findViewById(R.id.pet_age_input);
        breedInput = findViewById(R.id.pet_breed_input);
        birthdateInput = findViewById(R.id.pet_birthdate_input);
        notesInput = findViewById(R.id.pet_notes_input);

        saveButton = findViewById(R.id.save_pet_button);
        deleteButton = findViewById(R.id.delete_pet_button);

        // If editing, load existing pet. Otherwise hide delete
        if (petId != -1) {
            currentPet = db.petDao().getPetById(petId);
            if (currentPet != null) {
                nameInput.setText(safe(currentPet.pet_name));
                speciesInput.setText(safe(currentPet.species));
                breedInput.setText(safe(currentPet.breed));
                ageInput.setText(currentPet.age > 0 ? String.valueOf(currentPet.age) : "");
                birthdateInput.setText(safe(currentPet.birthdate));
                notesInput.setText(safe(currentPet.notes));
            }
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> deletePet());
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        // Save button listener
        saveButton.setOnClickListener(v -> savePet());

    }


    /**
     * Saves the pet to the database.
     */
    private void savePet() {
        String name = trim(nameInput);
        String species = trim(speciesInput);
        String breed = trim(breedInput);
        String ageStr = trim(ageInput);
        String birthdate = trim(birthdateInput); // optional
        String notes = trim(notesInput); // optional

        if (name.isEmpty() || species.isEmpty()) {
            Toast.makeText(this, "Name and species are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = safeparseInt(ageStr); // returns 0 if empty/invalid

        if (petId == -1) {
            //add new
            Pet newPet = new Pet();
            newPet.user_id = userId;
            newPet.pet_name = name;
            newPet.species = species;
            newPet.age = age;
            newPet.breed = breed;
            newPet.birthdate = birthdate;
            newPet.notes = notes;
            db.petDao().insert(newPet);
            Toast.makeText(this, "Pet added", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing
            if (currentPet == null) {
                Toast.makeText(this, "Could not load pet to update.", Toast.LENGTH_SHORT).show();
                return;
            }
            currentPet.pet_name = name;
            currentPet.species = species;
            currentPet.breed = breed;
            currentPet.age = age;
            currentPet.birthdate = birthdate;
            currentPet.notes = notes;
            db.petDao().update(currentPet);
            Toast.makeText(this, "Pet updated!", Toast.LENGTH_SHORT).show();
        }

        finish(); //PetListActivity.onResume() will refresh the list
    }

    private void deletePet () {
        if (currentPet != null) {
            db.petDao().delete(currentPet);
            Toast.makeText(this, "Pet deleted", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    // helpers
    private static String safe(String s) { return s == null ? "" : s; }
    private static String trim(EditText e) { return e.getText() == null ? "" : e.getText().toString().trim(); }
    private static int safeparseInt(String s) {
        try {
            return s.isEmpty() ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }

    }

}
