package com.example.echallan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etFullName, etVehicleNo, etEmail, etAadharNo, etPhoneNo, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        etFullName = findViewById(R.id.etFullName);
        etVehicleNo = findViewById(R.id.etVehicleNo);
        etEmail = findViewById(R.id.etEmail);
        etAadharNo = findViewById(R.id.etAadharNo);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input and register the user
                if (validateInput()) {
                    Toast.makeText(RegistrationActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    // Code to save data and navigate
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please check your input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close registration.xml
            }
        });
    }

    private boolean validateInput() {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        return password.equals(confirmPassword); // Basic validation example
    }
}
