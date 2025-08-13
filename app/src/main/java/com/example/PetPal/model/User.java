/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

/**
 * Represents a user of the PetPal application.
 * Each user has a unique username, password, and an ID.
 */
@Entity(tableName = "user")
public class User {
    /**
     * Primary key for the user (auto-generated).
     */
    @PrimaryKey(autoGenerate = true)
    public int user_id;

    /**
     * User's unique username.
     */
    @ColumnInfo(name = "user_name")
    public String user_name;

    /**
     * Hashed password of the user.
     */
    @ColumnInfo(name = "user_password")
    public String user_password;

    /**
     * Constructs a new User with a username and password.
     * @param username The user's username.
     * @param password The user's hashed password.
     */
    public User(String username, String password) {
        this.user_name = username;
        this.user_password = password;
    }
}