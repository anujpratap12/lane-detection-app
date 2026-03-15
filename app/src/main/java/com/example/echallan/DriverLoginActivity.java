package com.example.echallan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DriverLoginActivity extends AppCompatActivity {
    private EditText vehicleNumberInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        vehicleNumberInput = findViewById(R.id.vehicle_number_input);
        phoneNumberInput = findViewById(R.id.phone_number_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleNumber = vehicleNumberInput.getText().toString();
                String phoneNumber = phoneNumberInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (validateCredentials(vehicleNumber, phoneNumber, password)) {
                    Intent intent = new Intent(DriverLoginActivity.this, DriverLoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DriverLoginActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateCredentials(String vehicleNumber, String phoneNumber, String password) {
        // Placeholder validation logic. Replace with actual authentication logic.
        return vehicleNumber.equals("driver123") && phoneNumber.equals("1234567890") && password.equals("password");
    }
}
