package com.example.echallan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etVehicleNo, etPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        etVehicleNo = findViewById(R.id.etVehicleNo);
        etPhoneNo = findViewById(R.id.etPhoneNo);

        Button btnRequestOTP = findViewById(R.id.btnRequestOTP);

        btnRequestOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input and send OTP
                if (validateInput()) {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP Sent to Phone", Toast.LENGTH_SHORT).show();
                    // Code to send OTP goes here
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid details, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        String vehicleNo = etVehicleNo.getText().toString().trim();
        String phoneNo = etPhoneNo.getText().toString().trim();

        // Basic validation: ensure fields are not empty and phone number is 10 digits
        return !vehicleNo.isEmpty() && !phoneNo.isEmpty() && phoneNo.length() == 10;
    }
}
