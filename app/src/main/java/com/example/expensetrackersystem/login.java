package com.example.expensetrackersystem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackersystem.DatabaseHandlerRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private DatabaseHandlerRegistration dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn = findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(v -> startActivity(new Intent(login.this, register.class)));

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        Button loginButton = findViewById(R.id.btnlogin);

        // Initialize DatabaseHandlerRegistration
        dbHandler = new DatabaseHandlerRegistration(this);

        loginButton.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Use DatabaseHandlerRegistration to perform login
                boolean loginSuccessful = dbHandler.loginUser(email, password);
                if (loginSuccessful) {
                    // Login successful, proceed with appropriate action
                    performLogin(email, password);
                } else {
                    // Login failed, handle error
                    Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void performLogin(String email, String password) {
        startActivity(new Intent(login.this, MainActivity.class));
        finish();
    }
}
