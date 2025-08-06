/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a user in the PetPal application
 * Each user can have multiple pets and corresponding health logs.
 */

@Entity
public class
User {

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

    /**
     * Default constructor required by Room
     */
    public User() {
    }

    /**
     * Constructs a User object with the given username and password.
     * @param user_name The username for login.
     * @param user_password The hashed password.
     */
    public User(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.is_admin = false; //Default to regular user
    }

    /**
     * Returns the username for display or debugging
     */
    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", is_admin=" + is_admin +
                '}';
    }

}
