/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.model.Pet;
import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of pets
 */

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;

    /**
     * Constructor for PetAdapter.
     * @param petList The list of pets to display.
     */
    public PetAdapter(List<Pet> petList) {
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petNameText.setText(pet.pet_name);
        holder.petSubtitleText.setText(pet.species + " • " + pet.breed);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    /**
     * Updates the list of pets in the adapter.
     * @param pets The new list of pets.
     */
    public void updatePets(List<Pet> pets) {
        this.petList = pets;
        notifyDataSetChanged();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petNameText, petSubtitleText;

        PetViewHolder(View itemView) {
            super(itemView);
            petNameText = itemView.findViewById(R.id.pet_name_text);
            petSubtitleText = itemView.findViewById(R.id.pet_subtitle_text);
        }
    }
}
