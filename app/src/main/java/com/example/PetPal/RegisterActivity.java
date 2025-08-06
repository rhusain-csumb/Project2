/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.User;
import com.example.PetPal.dao.UserDao;

/**
 * Activity for user registration in the PetPal app.
 * Provides input fields for username and password, and stores the new user in the Room database.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private AppDatabase db;

    /**
     * Called when the RegisterActivity is first created.
     * Initializes the Room database and binds the UI components to layout elements.
     *
     * @param savedInstanceState The previously saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries() //For testing only
                .build();

        // Bind UI elements
        usernameInput = findViewById(R.id.register_username_input);
        passwordInput = findViewById(R.id.register_password_input);
        Button registerButton = findViewById(R.id.register_button);

        // Set button action
        registerButton.setOnClickListener(view -> registerUser());
    }

    /**
     * Attempts to register a new user.
     * Validates the input fields
     * Checks if the username already exists
     * Inserts the user into the database if valid
     * Displays appropriate messages
     * Navigates back to the Login screen on success
     */
    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDao userDao = db.userDao();
        User existingUser = userDao.login(username, password);

        if (existingUser != null) {
            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
        } else {
            User newUser = new User(username, password);
            userDao.insert(newUser);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish(); // Close RegisterActivity and go back to Login
        }
    }
}
