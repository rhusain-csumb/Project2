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

import com.example.PetPal.model.HealthLog;

import java.util.List;

/**
 * Data Access Object for the HealthLog table.
 * Defines methods for interacting with health log-related data in the database.
 */
@Dao
public interface HealthLogDao {

    /**
     * Inserts a new health log into the database
     * @param log The health log to insert.
     */
    @Insert
    void insert(HealthLog log);

    /**
     * Retrieves a list of all health logs for a given pet.
     * @param petId The pet_id of the pet.
     * @return List of health logs belonging to the pet.
     */
    @Query("SELECT * FROM HealthLog WHERE pet_id = :petId")
    List<HealthLog> getLogsByPet(int petId);

    /**
     * Retrieves a specific health log by its ID.
     * @param id The log_id of the log.
     * @return The matching HealthLog object.
     */
    @Query("SELECT * FROM HealthLog WHERE log_id = :id")
    HealthLog getLogById(int id);

    /**
     * Updates a health log's details.
     * @param log The health log to update.
     */
    @Update
    void update(HealthLog log);

    /**
     * Deletes a health log from the database.
     * @param log The health log to delete.
     */
    @Delete
    void delete(HealthLog log);
}