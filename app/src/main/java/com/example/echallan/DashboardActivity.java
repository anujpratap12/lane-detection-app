package com.example.echallan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnOverspeedingDetection;
    private Button btnLaneViolation;
    private Button btnFineCalculation;
    private Button btnSchoolHospitalZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons
        btnOverspeedingDetection = findViewById(R.id.btnOverspeedingDetection);
        btnLaneViolation = findViewById(R.id.btnLaneViolation);
        btnFineCalculation = findViewById(R.id.btnFineCalculation);
        btnSchoolHospitalZone = findViewById(R.id.btnSchoolHospitalZone);

        // Set up button click listeners
        btnOverspeedingDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, OverspeedingDetectionActivity.class);
                startActivity(intent);
            }
        });

        btnLaneViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LaneViolationActivity.class);
                startActivity(intent);
            }
        });

        btnFineCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, FineCalculationActivity.class);
                startActivity(intent);
            }
        });

        btnSchoolHospitalZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SchoolHospitalZoneActivity.class);
                startActivity(intent);
            }
        });
    }
}
