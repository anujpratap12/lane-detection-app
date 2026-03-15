package com.example.echallan;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class FineCalculationActivity extends AppCompatActivity {

    private static final String TAG = "FineCalculationActivity";

    private static final double GENERAL_FINE_RATE = 10.0; // Base fine rate per km/h over limit
    private static final double SCHOOL_HOSPITAL_FINE_MULTIPLIER = 1.20; // 20% extra for special zones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_calculation);
        Log.d(TAG, "Fine Calculation Activity initialized");
    }

    /**
     * Calculate the fine for overspeeding.
     *
     * @param speed The detected speed of the vehicle (km/h).
     * @param speedLimit The speed limit in the area (km/h).
     * @param isSpecialZone True if the area is a school/hospital zone.
     * @return The calculated fine amount.
     */
    public double calculateFine(double speed, double speedLimit, boolean isSpecialZone) {
        if (speed <= speedLimit) {
            Log.d(TAG, "No fine: speed within limit.");
            return 0.0;
        }

        double overSpeed = speed - speedLimit;
        double fine = overSpeed * GENERAL_FINE_RATE;

        if (isSpecialZone) {
            fine *= SCHOOL_HOSPITAL_FINE_MULTIPLIER;
            Log.d(TAG, "Special zone detected. Fine increased by 20%.");
        }

        Log.d(TAG, "Calculated fine: " + fine);
        return fine;
    }

    /**
     * Simulates fine calculation for a specific scenario.
     */
    public void simulateFineCalculation() {
        double vehicleSpeed = 70.0; // Example speed in km/h
        double speedLimit = 50.0; // Example speed limit in km/h
        boolean isSpecialZone = true; // Example: school/hospital zone

        double fine = calculateFine(vehicleSpeed, speedLimit, isSpecialZone);
        Log.d(TAG, "Simulated fine: " + fine);
    }
}
