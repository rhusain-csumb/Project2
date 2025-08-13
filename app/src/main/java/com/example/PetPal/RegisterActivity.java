/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.dao.UserDao;
import com.example.PetPal.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button registerButton, loginRedirectButton;
    private UserDao userDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String EXTRA_USER_ID = "com.example.PetPal.user_id";

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_button);
        loginRedirectButton = findViewById(R.id.login_redirect_button);

        userDao = Room.databaseBuilder(this, AppDatabase.class, "pet-pal-db").build().userDao();

        registerButton.setOnClickListener(v -> registerUser());
        loginRedirectButton.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            mainHandler.post(() -> Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show());
            return;
        }

        executor.execute(() -> {
            User existingUser = userDao.getUserByUsername(username);
            if (existingUser != null) {
                mainHandler.post(() -> Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show());
            } else {
                User newUser = new User(username, PasswordUtil.hashPassword(password));
                userDao.insert(newUser);
                mainHandler.post(() -> {
                    Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }
}