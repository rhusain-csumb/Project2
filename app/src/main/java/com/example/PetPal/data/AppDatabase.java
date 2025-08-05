/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.PetPal.model.User;
import com.example.PetPal.model.Pet;
import com.example.PetPal.model.HealthLog;

import com.example.PetPal.dao.UserDao;
import com.example.PetPal.dao.PetDao;
import com.example.PetPal.dao.HealthLogDao;
import com.example.PetPal.model.HealthLog;
import com.example.PetPal.model.User;

/**
 * AppDatabase serves as the main access point to the persisted data using the Room library.
 * This abstract class is annotated with @Database and contains the list of entities (tables), the database version, and abstract methods to access each DAO.
 */

@Database(entities = {User.class, Pet.class, HealthLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Provides access to the User data operations.
     * @return UserDao interface to interact with the User Table.
     */
    public abstract UserDao userDao ();

    /**
     * Provides access to the Pet data operations.
     * @return PetDao interface to interact with the Pet Table.
     */
    public abstract PetDao petDao ();

    /**
     * Provides access to the HealthLog data operations.
     * @return HealthLogDao interface to interact with the HealthLog Table.
     */
    public abstract HealthLogDao healthLogDao ();
}
