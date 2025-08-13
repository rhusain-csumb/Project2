/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

/**
 * Represents a health log entry for a pet.
 * Each log entry is associated with a specific pet via foreign key.
 */
@Entity(foreignKeys = @ForeignKey(
        entity = Pet.class,
        parentColumns = "pet_id",
        childColumns = "pet_id",
        onDelete = ForeignKey.CASCADE
))
public class HealthLog {
    /**
     * Primary key for the health log (auto-generated).
     */
    @PrimaryKey(autoGenerate = true)
    public int log_id;

    /**
     * ID of the pet this log entry belongs to (foreign key).
     */
    public int pet_id;

    /**
     * Type of log entry (e.g., Vaccination, Vet Visit, Medication).
     */
    public String type;

    /**
     * The date of the event in YYYY-MM-DD format.
     */
    public String date;

    /**
     * Description or notes for the log entry.
     */
    public String description;
}