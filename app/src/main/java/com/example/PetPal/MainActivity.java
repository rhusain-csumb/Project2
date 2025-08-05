/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import com.example.PetPal.data.AppDatabase;

private AppDatabase db;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Initialize the Room Database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries() //For testing only
                .build();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}