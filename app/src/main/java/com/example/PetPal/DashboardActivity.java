/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */
// DashboardActivity.java (full code)

package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private int currentUserId;
    private TextView welcomeTextView;
    private Button addPetButton;
    private Button viewPetsButton;
    private Button logoutButton;
    private String currentUsername;

    private static final String EXTRA_USER_ID = "com.example.petpal.user_id";
    private static final String EXTRA_USERNAME = "com.example.petpal.username"; // New constant for username

    // Update the newIntent method to accept the username
    public static Intent newIntent(Context packageContext, int userId, String username) {
        Intent intent = new Intent(packageContext, DashboardActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_USERNAME, username); // Put the username into the intent
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user ID and username from the intent
        currentUserId = getIntent().getIntExtra(EXTRA_USER_ID, -1);
        currentUsername = getIntent().getStringExtra(EXTRA_USERNAME);

        if (currentUserId == -1 || currentUsername == null) {
            Toast.makeText(this, "User information not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        welcomeTextView = findViewById(R.id.welcome_text_view);
        addPetButton = findViewById(R.id.add_pet_button);
        viewPetsButton = findViewById(R.id.view_pets_button);
        logoutButton = findViewById(R.id.logout_button);

        // Update the welcome message to use the retrieved username
        String welcomeMessage = "Welcome, " + currentUsername + "!";
        welcomeTextView.setText(welcomeMessage);

        addPetButton.setOnClickListener(v -> {
            Intent intent = AddPetActivity.newIntent(this, currentUserId, -1);
            startActivity(intent);
        });

        viewPetsButton.setOnClickListener(v -> {
            Intent intent = PetListActivity.newIntent(this, currentUserId);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = LoginActivity.newIntent(DashboardActivity.this);
            startActivity(intent);
            finish();
        });
    }
}