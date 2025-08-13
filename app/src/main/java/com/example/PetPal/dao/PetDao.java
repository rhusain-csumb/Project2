/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.PetPal.model.Pet;

import java.util.List;

/**
 * Data Access Object for the Pet table.
 * Defines methods for interacting with pet-related data in the database.
 */
@Dao
public interface PetDao {

    /**
     * Inserts a new pet into the database
     * @param pet The pet to insert.
     */
    @Insert
    void insert(Pet pet);

    /**
     * Retrieves a list of all pets for a given owner.
     * @param ownerId The user_id of the owner.
     * @return List of pets belonging to the owner.
     */
    @Query("SELECT * FROM Pet WHERE user_id = :ownerId")
    List<Pet> getPetsByOwner(int ownerId);

    /**
     * Retrieves a specific pet by its ID.
     * @param id The pet_id of the pet.
     * @return The matching Pet object.
     */
    @Query("SELECT * FROM Pet WHERE pet_id = :id")
    Pet getPetById(int id);

    /**
     * Updates a pet's details.
     * @param pet The pet to update.
     */
    @Update
    void update(Pet pet);

    /**
     * Deletes a pet from the database.
     * @param pet The pet to delete.
     */
    @Delete
    void delete(Pet pet);
}