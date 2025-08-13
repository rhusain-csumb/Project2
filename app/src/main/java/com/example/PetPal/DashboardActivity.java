/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    private Button addPetButton, viewPetsButton;
    private int currentUserId;

    private static final String EXTRA_USER_ID = "com.example.PetPal.user_id";

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, DashboardActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addPetButton = findViewById(R.id.add_pet_button);
        viewPetsButton = findViewById(R.id.view_pets_button);

        currentUserId = getIntent().getIntExtra(EXTRA_USER_ID, -1);
        if (currentUserId == -1) {
            Toast.makeText(this, "User ID not found, please log in again.", Toast.LENGTH_LONG).show();
            finish();
        }

        addPetButton.setOnClickListener(v -> {
            Intent intent = AddPetActivity.newIntent(this, currentUserId, -1);
            startActivity(intent);
        });

        viewPetsButton.setOnClickListener(v -> {
            Intent intent = PetListActivity.newIntent(this, currentUserId);
            startActivity(intent);
        });
    }
}