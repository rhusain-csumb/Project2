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

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {

    private List<Pet> pets;
    private final OnPetClickListener onPetClickListener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
    }

    public PetAdapter(List<Pet> pets, OnPetClickListener listener) {
        this.pets = pets;
        this.onPetClickListener = listener;
    }

    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.bind(pet, onPetClickListener);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public void updatePets(List<Pet> newPets) {
        this.pets = newPets;
        notifyDataSetChanged();
    }

    static class PetHolder extends RecyclerView.ViewHolder {
        private final TextView petNameTextView;
        private final TextView petDetailsTextView;

        public PetHolder(@NonNull View itemView) {
            super(itemView);
            petNameTextView = itemView.findViewById(R.id.pet_name_text_view);
            petDetailsTextView = itemView.findViewById(R.id.pet_details_text_view);
        }

        public void bind(final Pet pet, final OnPetClickListener listener) {
            petNameTextView.setText(pet.pet_name);
            petDetailsTextView.setText(pet.species + " • " + pet.breed);
            itemView.setOnClickListener(v -> listener.onPetClick(pet));
        }
    }
}