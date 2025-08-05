/**
 * This is an andriod moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

/**
 * Represents a health log entry for a pet in the PetPal app.
 * Each entry is tied to a specific pet and stores care/medical information such as vaccinations.
 */
@Entity( foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "user_id",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE
))
public class HealthLog {

    /**
     * Primary key for the health log entry (auto-generated).
     */
    @PrimaryKey(autoGenerate = true)
    public int log_id;

    /**
     * ID of the pet this health log belongs to (foreign key).
     */
    public int pet_id;

    /**
     * Type of health entry (ex: Vaccinations, Medications, Vet Visits)
     */
    public String type;

    /**
     * Detailed description of the log entry.
     */
    public String descritpion;

    /**
     * Date of the event in YYY-MM-DD format.
     */
    public String date;

    /**
     * Name of the vet or clinic
     */
    public String vet_name;
}



