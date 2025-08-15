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
import com.example.PetPal.model.User;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username")
    User findUserByUsername(String username);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}

