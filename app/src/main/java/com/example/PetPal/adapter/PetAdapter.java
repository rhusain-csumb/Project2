/**
 * PetAdapter.java
 *
 * Adapter class for displaying a list of Pet objects inside a RecyclerView.
 *
 * This adapter handles two main tasks:
 *  1. Binding pet data to the UI elements in the item layout.
 *  2. Handling click events for each pet, including optional delete functionality.
 *
 * @authors:
 *   Rasna Husain
 *   Chanroop Randhawa
 *
 *   Last Updated: August 15, 2025
 */

package com.example.PetPal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.R;
import com.example.PetPal.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    // List of Pet objects to display in the RecyclerView
    private List<Pet> pets;
    // Listener for handling click events
    private OnPetClickListener listener;
    // Boolean flag to determine if delete button should be shown
    private boolean showDeleteButton;

    /**
     * Interface for handling click events on pet items.
     * Activities/Fragments using this adapter must implement these.
     */
    public interface OnPetClickListener {
        void onPetClick(Pet pet); // Click on a pet item

        // Optional method for delete clicks (default empty implementation)
        default void onDeleteClick(Pet pet) {}
    }

    /**
     * Constructor for PetAdapter
     *
     * @param petList - initial list of pets
     * @param listener - click event listener
     * @param showDeleteButton - whether delete button should be visible
     */
    public PetAdapter(List<Pet> petList, OnPetClickListener listener, boolean showDeleteButton) {
        this.pets = petList;
        this.listener = listener;
        this.showDeleteButton = showDeleteButton;
    }

    /**
     * Update the list of pets in the adapter
     *
     * @param pets - new list of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>();
        notifyDataSetChanged(); // Refresh the RecyclerView
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single pet item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        // Get the pet at the given position
        Pet pet = pets.get(position);
        // Bind data to the ViewHolder
        holder.bind(pet, listener, showDeleteButton);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    /**
     * ViewHolder class for individual pet items
     */
    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petNameTextView;
        ImageButton deleteButton;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find UI elements in the layout
            petNameTextView = itemView.findViewById(R.id.pet_name_text);
            deleteButton = itemView.findViewById(R.id.delete_pet_button);
        }

        /**
         * Bind pet data and click events to UI components
         */
        public void bind(final Pet pet, final OnPetClickListener listener, boolean showDeleteButton) {
            // Set the pet's name in the TextView
            petNameTextView.setText(pet.getPetName());

            // Handle item click
            itemView.setOnClickListener(v -> listener.onPetClick(pet));

            // Handle delete button visibility & click
            if (deleteButton != null) {
                if (showDeleteButton) {
                    deleteButton.setVisibility(View.VISIBLE);
                    deleteButton.setOnClickListener(v -> listener.onDeleteClick(pet));
                } else {
                    deleteButton.setVisibility(View.GONE);
                }
            }
        }
    }
}
