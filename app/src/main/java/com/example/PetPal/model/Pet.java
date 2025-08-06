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
 * Represents a pet belonging to a user in the PetPal app.
 * Each pet is associated with a specific user via foreign key.
 */
@Entity( foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "user_id",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE
))
public class Pet {

    /**
     * Primary key for the pet (auto-generated).
     */
    @PrimaryKey(autoGenerate = true)
    public int pet_id;

    /**
     * ID of the user who owns this pet (foreign key).
     */
    public int user_id;

    /**
     * Pet's name.
     */
    public String pet_name;

    /**
     * Type/species of pet (ex: Dog, Cat and more).
     */
    public String species;

    /**
     * Pet's breed
     */
    public String breed;

    /**
     * Pet's age
     */
    public int age;

    /**
     * Birthdate of the pet in YYYY-MM-DD format.
     */
    public String birthdate;

    /**
     * Additional notes (ex: allergies, behaviors, restrictions and more).
     */
    public String notes;
}
