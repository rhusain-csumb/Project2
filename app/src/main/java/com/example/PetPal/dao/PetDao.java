/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.PetPal.model.Pet;

import java.util.List;

/**
 * Data Access Object for the Pet table.
 * Provides methods to manage pet records in the database.
 */
@Dao
public interface PetDao {

    /**
     * Inserts a new pet into the database.
     * @param pet The pet to insert.
     */
    @Insert
    void insert (Pet pet);

    /**
     * Retrieves all pets belonging to a specific user.
     * @param userId The user_id of the pet owner.
     * @return A list of Pet objects.
     */
    @Query("SELECT * FROM Pet WHERE user_id =:userId")
    List<Pet> getPetByOwner(int userId);

    /**
     * Retrieves a specific pet by ID.
     * @param petID The ID of the pet.
     * @return The matching Pet object.
     */
    @Query("SELECT * FROM Pet WHERE pet_id = :petID")
    Pet getPetById(int petID);

    /**
     * Updates a pet's details.
     * @param pet The pet update.
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
