/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.AddPetActivity;
import com.example.PetPal.R;
import com.example.PetPal.model.Pet;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of pets
 */
public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context context;
    private List<Pet> petList;

    /**
     * Constructor for PetAdapter.
     *
     * @param petList The list of pets to display.
     */
    public PetAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petNameText.setText(pet.pet_name);
        holder.petSpeciesText.setText(pet.species + " • " + pet.breed);

        // Click lister to edit pet
        holder.itemView.setOnClickListener(v -> {
            Intent intent = AddPetActivity.newIntent(context, pet.user_id, pet.pet_id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    /**
     * Updates the list of pets in the adapter.
     * @param newPets The new list of pets.
     */
    public void updatePets(List<Pet> newPets) {
        this.petList = newPets;
        notifyDataSetChanged();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petNameText, petSpeciesText;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petNameText = itemView.findViewById(R.id.pet_name_text);
            petSpeciesText = itemView.findViewById(R.id.pet_species_text);
        }
    }
}