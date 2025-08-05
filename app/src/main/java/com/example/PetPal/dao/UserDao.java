/**
 * This is an andriod moble application called PetPal. This practical app will track pet visits,
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

import com.example.PetPal.model.User;

import java.util.List;

/**
 * Data Access Object for the User table.
 * Defines methods for interacting with user-related data in the database.
 */
@Dao
public interface UserDao {

    /**
     * Inserts a new user into the database
     * @param user The user to insert.
     */
    @Insert
    void insert(User user);

    /**
     * Retrieves a list of all users.
     * @return List of all users.
     */
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    /**
     * Retrieves a specific user by their ID.
     * @param id The user_id of the user.
     * @return The matching User object.
     */
    @Query("SELECT * FROM user WHERE user_id = :id")
    User getUserById(int id);

    /**
     * Retrieves a user by username and password (used for login).
     * @param username The username.
     * @param password The password.
     * @return The matching User object.
     */
    @Query("SELECT * FROM User WHERE user_name = :username AND user_password = :passworf")
    User login(String username, String password);

    /**
     * Updates a user's details.
     * @param user The user to update.
     */
    @Update
    void update(User user);

    /**
     * Deletes a user from the database.
     * @param user The user to update.
     */
    @Delete
    void delete(User user);
}
