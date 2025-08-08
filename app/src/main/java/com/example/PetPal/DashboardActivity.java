/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * DashboardActivity serves as the home screen after a successful login.
 */
public class DashboardActivity  extends AppCompatActivity {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Get the user ID passed from LoginActivity
        userID = getIntent().getIntExtra("user_id", -1);

        Button addPetButton = findViewById(R.id.add_pet_button);
        Button viewPetsButton = findViewById(R.id.view_pets_button);

        addPetButton.setOnClickListener(v -> {
            Intent i = new Intent(this, AddPetActivity.class);
            i.putExtra("user_id", userID);
            startActivity(i);
        });

        viewPetsButton.setOnClickListener(v -> {
            Intent i = new Intent(this, PetListActivity.class);
            i.putExtra("user_id", userID);
            startActivity(i);
        });

        TextView welcomeText = findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome to PetPal");
    }
}
