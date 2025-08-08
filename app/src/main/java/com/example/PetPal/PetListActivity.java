/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.PetPal.dao.PetDao;
import com.example.PetPal.PetAdapter;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;

import java.util.List;

/**
 * PetListActivity displays all pest for the logged-in user.
 * Allows tapping a pet to edit it in AddPetActivity.
 */
public class PetListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private AppDatabase db;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        // Get the user_id passed from DashboardActivity
        userID = getIntent().getIntExtra("user_id", -1);

        // Init database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries() // only for testing
                .build();

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view_pets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load pets for this user

        List<Pet> petList = db.petDao().getPetByOwner(userID);

        // Setup adapter
        petAdapter = new PetAdapter(petList);
        recyclerView.setAdapter(petAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh pet list when returning from AddPetActivity
        List<Pet> updatedPetList = db.petDao().getPetByOwner(userID);
        petAdapter.updatePets(updatedPetList);
    }
}
