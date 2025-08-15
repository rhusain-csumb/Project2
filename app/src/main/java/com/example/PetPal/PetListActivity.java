/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */
package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.adapter.PetAdapter;
import com.example.PetPal.dao.PetDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PetListActivity extends AppCompatActivity implements PetAdapter.OnPetClickListener {
    private RecyclerView petRecycler;
    private PetAdapter petAdapter;
    private TextView emptyStateTextView;
    private Button backButton;

    private PetDao petDao;
    private int currentUserId;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String EXTRA_USER_ID = "com.example.petpal.user_id";

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, PetListActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petRecycler = findViewById(R.id.pet_recycler);
        emptyStateTextView = findViewById(R.id.empty_state);
        backButton = findViewById(R.id.back_button_pet_list);

        petRecycler.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter(new ArrayList<>(), this);
        petRecycler.setAdapter(petAdapter);

        AppDatabase db = AppDatabase.getDatabase(this);
        petDao = db.petDao();

        currentUserId = getIntent().getIntExtra(EXTRA_USER_ID, -1);
        if (currentUserId == -1) {
            Toast.makeText(this, "User ID not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPets();
    }

    private void loadPets() {
        executor.execute(() -> {
            List<Pet> pets = petDao.getPetsByOwner(currentUserId);
            mainHandler.post(() -> {
                petAdapter.updatePets(pets);
                if (pets.isEmpty()) {
                    emptyStateTextView.setVisibility(View.VISIBLE);
                    petRecycler.setVisibility(View.GONE);
                } else {
                    emptyStateTextView.setVisibility(View.GONE);
                    petRecycler.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    @Override
    public void onPetClick(Pet pet) {
        // This is the key change. When a pet is clicked, it now launches PetProfileActivity.
        Intent intent = PetProfileActivity.newIntent(this, pet.pet_id, currentUserId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}