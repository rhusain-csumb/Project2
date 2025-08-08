/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.User;

/**
 * LoginActivity handles user authentication and account creation.
 * It provides UI components for users to log in or sign up using a username and password.
 * Interacts with the UserDao to perform database operations using Room.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private AppDatabase db;

    /**
     * Called when the activity is first created.
     * Initializes UI components and database access.
     * @param savedInstanceState The previously saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the Room database
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "petpal-db")
                .allowMainThreadQueries() // For testing only
                .build();

        // Connect input fields to layout elements
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        // Set up login and sign-up button actions
        Button loginButton = findViewById(R.id.login_button);
        Button signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(view -> loginUser());
        signupButton.setOnClickListener(view -> {
            // Open the RegisterActivity Room
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Attempts to log in the user using the provided username and password.
     * Queries the UserDao to verify credentials and shows a result message.
     */
    private void loginUser() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = db.userDao().login(username, hashedPassword);


        if (user != null) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            //Navigate to dashboard or next screen
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("user_id", user.user_id);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

}
