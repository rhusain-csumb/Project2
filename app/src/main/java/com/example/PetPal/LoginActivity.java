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

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private UserDao userDao;
    private ExecutorService executorService;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        AppDatabase db = AppDatabase.getDatabase(this);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            String hashedPassword = PasswordUtil.hashPassword(password);

            Log.d("LoginActivity", "Login attempt for user: " + username);
            Log.d("LoginActivity", "Hashed password being checked: " + hashedPassword);

            User user = userDao.login(username, hashedPassword);

            runOnUiThread(() -> {
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = DashboardActivity.newIntent(LoginActivity.this, user.user_id, user.username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}