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

public class register extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private DatabaseHandlerRegistration dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConformPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        TextView alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(v -> startActivity(new Intent(register.this, login.class)));

        dbHandler = new DatabaseHandlerRegistration(this);

        btnRegister.setOnClickListener(v -> {
            String username = inputUsername.getText().toString().trim(); // Not used in registration
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String confirmPassword = inputConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                boolean registrationSuccessful = dbHandler.registerUser(email, password);
                if (registrationSuccessful) {
                    startActivity(new Intent(register.this, login.class));
                    finish();
                } else {
                    Toast.makeText(register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
