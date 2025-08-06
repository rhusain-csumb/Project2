/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;
import com.example.PetPal.dao.PetDao;

/**
 * AddPetActivity allows users to add a new pet to their account.
 */
public class AddPetActivity extends AppCompatActivity {

    private EditText nameInput, speciesInput, ageInput, breedInput;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Initialize input fields
        nameInput = findViewById(R.id.pet_name_input);
        speciesInput = findViewById(R.id.pet_species_input);
        ageInput = findViewById(R.id.pet_age_input);
        breedInput = findViewById(R.id.pet_breed_input);

        // Initialize DB
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries()
                .build();

        //Set up save button listener
        Button saveButton = findViewById(R.id.save_pet_button);
        saveButton.setOnClickListener(view -> savePet());
    }

    /**
     * Saves the pet to the database.
     */
    private void savePet() {
        String name = nameInput.getText().toString().trim();
        String species = speciesInput.getText().toString().trim();
        String breed = breedInput.getText().toString().trim();
        String ageStr = ageInput.getText().toString().trim();

        if (name.isEmpty() || species.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        Pet newPet = new Pet();
        newPet.pet_name = name;
        newPet.species = species;
        newPet.age = age;
        newPet.breed = breed;
        newPet.user_id = 1; // TODO: Replace with actual logged-in user ID

        PetDao petDao = db.petDao();
        petDao.insert(newPet);

        Toast.makeText(this, "Pet added!", Toast.LENGTH_SHORT).show();
        finish(); // close the activity
    }

}
