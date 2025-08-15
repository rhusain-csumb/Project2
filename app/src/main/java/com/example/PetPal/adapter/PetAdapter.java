package com.example.PetPal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.R;
import com.example.PetPal.model.Pet;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<Pet> petList;
    private final OnPetClickListener listener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
    }

    public PetAdapter(List<Pet> petList, OnPetClickListener listener) {
        this.petList = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.bind(pet, listener);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public void updatePets(List<Pet> newPets) {
        this.petList = newPets;
        notifyDataSetChanged();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        private final TextView petNameTextView;
        private final TextView petSpeciesTextView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petNameTextView = itemView.findViewById(R.id.pet_name_text_view);
            petSpeciesTextView = itemView.findViewById(R.id.pet_species_text_view);
        }

        public void bind(final Pet pet, final OnPetClickListener listener) {
            petNameTextView.setText(pet.pet_name);
            petSpeciesTextView.setText(pet.species);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPetClick(pet);
                }
            });
        }
    }
}