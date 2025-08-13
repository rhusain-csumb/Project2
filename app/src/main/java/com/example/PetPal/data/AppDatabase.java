/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.PetPal.dao.UserDao;
import com.example.PetPal.dao.PetDao;
import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.model.User;
import com.example.PetPal.model.Pet;
import com.example.PetPal.model.HealthLog;

/**
 * Main database class for the PetPal application.
 * Defines the database schema and provides access to DAOs.
 */
@Database(entities = {User.class, Pet.class, HealthLog.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PetDao petDao();
    public abstract HealthLogDao healthLogDao();
}