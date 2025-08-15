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

@Dao
public interface HealthLogDao {

    @Insert
    void insert(HealthLog healthLog);

    @Update
    void update(HealthLog healthLog);

    @Delete
    void delete(HealthLog healthLog);

    @Query("SELECT * FROM health_logs WHERE pet_id = :petId ORDER BY date DESC")
    List<HealthLog> getLogsByPet(int petId);

    @Query("SELECT * FROM health_logs WHERE id = :id")
    HealthLog getLogById(int id);

    @Query("SELECT * FROM health_logs")
    List<HealthLog> getAllLogs();
}
