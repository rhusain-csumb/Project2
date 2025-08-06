package com.example.PetPal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.PetPal.dao.PetDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;

import java.util.List;

public class PetProfileActivity extends AppCompatActivity {

    private TextView welcomeText;
    private RecyclerView petsRecyclerView;
    private FloatingActionButton addPetButton;
    private AppDatabase db;
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        // Initialize UI components
        welcomeText = findViewById(R.id.welcome_text);
        petsRecyclerView = findViewById(R.id.pets_recycler_view);
        addPetButton = findViewById(R.id.add_pet_button);

        // Initialize database
        db = AppDatabase.getDatabase(getApplicationContext()); // Use a singleton pattern here

        // Retrieve the logged-in user's ID and username from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            loggedInUserId = intent.getIntExtra("USER_ID", -1);
            String username = intent.getStringExtra("USERNAME");
            if (username != null) {
                welcomeText.setText("Welcome, " + username + "!");
            }
        }

        // Set up the RecyclerView
        petsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load the pets for the current user
        loadPetsForUser(loggedInUserId);

        // Set up the button to add a new pet
        addPetButton.setOnClickListener(view -> {
            // TODO: Start a new activity to add a pet
        });
    }

    private void loadPetsForUser(int userId) {
        if (userId != -1) {
            // TODO: Query the PetDao on a background thread to get pets for this user
            // Example using a simple thread (for demonstration)
            new Thread(() -> {
                PetDao petDao = db.petDao();
                List<Pet> pets = petDao.getPetByUser(userId);

                // Once data is loaded, update the UI on the main thread
                runOnUiThread(() -> {
                    // TODO: Set up and pass the list of pets to the RecyclerView adapter
                });
            }).start();
        }
    }
}