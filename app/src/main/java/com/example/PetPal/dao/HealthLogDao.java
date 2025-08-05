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

import com.example.PetPal.model.HealthLog;

import java.util.List;

/**
 * Data Access Object for the HealthLog table.
 * Provides methods to manage oet health records.
 */
@Dao
public interface HealthLogDao {

    /**
     * Inserts a new health log entry.
     * @param log The HealthLog entry to insert.
     */
    @Insert
    void insert(HealthLog log);

    /**
     * Retrieves all health logs for a given pet.
     * @param petId The ID of the pet.
     * @return A list of HealthLog entries.
     */
    @Query("SELECT * FROM HealthLog WHERE pet_id = :petId")
    List<HealthLog> getLogsByPet(int petId);

    /**
     * Updates an existing health log.
     * @param log The HealthLog entry to update.
     */
    @Update
    void update(HealthLog log);

    /**
     * Deletes a health log entry.
     * @param log The HealthLog entry to delete.
     */
    @Delete
    void delete(HealthLog log);
}
