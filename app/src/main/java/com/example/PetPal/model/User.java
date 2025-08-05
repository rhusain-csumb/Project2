/**
 * This is an andriod moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a user in the PetPal application
 * Each user can have multiple pets and corresponding health logs.
 */

@Entity
public class User {

    /**
     * Primary key for the user (auto-generated).
     */
    @PrimaryKey(autoGenerate = true)
    public int user_id;

    /**
     * Username used to log in
     */
    public String user_name;

    /**
     * User's password
     */
    public String user_password;

    /**
     * Indicated if the user has admin privileges
     */
    public boolean is_admin;

}
