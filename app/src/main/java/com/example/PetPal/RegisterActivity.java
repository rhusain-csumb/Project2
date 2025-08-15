/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PetPal.dao.UserDao;
import com.example.PetPal.data.AppDatabase;
import com.example.PetPal.model.User;
import com.example.PetPal.PasswordUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button backButton;

    private UserDao userDao;
    private ExecutorService executorService;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        registerButton = findViewById(R.id.register_button);
        backButton = findViewById(R.id.back_button_register);

        AppDatabase db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();

        registerButton.setOnClickListener(v -> registerUser());
        backButton.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            User existingUser = userDao.findUserByUsername(username);

            if (existingUser != null) {
                runOnUiThread(() -> Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT).show());
            } else {
                String hashedPassword = PasswordUtil.hashPassword(password);
                Log.d("RegisterActivity", "Registering new user: " + username + " with hashed password: " + hashedPassword);
                User newUser = new User(username, hashedPassword);
                userDao.insert(newUser);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}