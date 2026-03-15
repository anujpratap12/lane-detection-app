package com.example.echallan;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class SchoolHospitalZoneActivity extends AppCompatActivity {

    private static final String TAG = "SchoolHospitalZoneActivity";

    private static final double GENERAL_FINE_RATE = 1500.0; // Base fine rate per km/h over limit
    private static final double SPECIAL_ZONE_FINE_MULTIPLIER = 300; // 20% extra for special zones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_hospital_zone);
        Log.d(TAG, "School/Hospital Zone Activity initialized");
    }

    /**
     * Checks if the vehicle is in a school or hospital zone based on GPS coordinates.
     *
     * @param vehicleLatitude Latitude of the vehicle.
     * @param vehicleLongitude Longitude of the vehicle.
     * @return True if the vehicle is in a special zone; false otherwise.
     */
    public boolean isInSpecialZone(double vehicleLatitude, double vehicleLongitude) {
        // Example coordinates for school or hospital zones (latitude, longitude)
        double[][] specialZones = {
                {28.6139, 77.2090}, // Example: Zone 1 (Latitude, Longitude)
                {28.5355, 77.3910}  // Example: Zone 2 (Latitude, Longitude)
        };

        for (double[] zone : specialZones) {
            double zoneLatitude = zone[0];
            double zoneLongitude = zone[1];
            if (calculateDistance(vehicleLatitude, vehicleLongitude, zoneLatitude, zoneLongitude) < 0.5) {
                Log.d(TAG, "Vehicle is in a special zone.");
                return true;
            }
        }
        Log.d(TAG, "Vehicle is not in a special zone.");
        return false;
    }

    /**
     * Calculate the fine for overspeeding in a school or hospital zone.
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
            fine *= SPECIAL_ZONE_FINE_MULTIPLIER;
            Log.d(TAG, "Special zone detected. Fine increased by 20%.");
        }

        Log.d(TAG, "Calculated fine: " + fine);
        return fine;
    }

    /**
     * Calculate the distance between two GPS coordinates using the Haversine formula.
     *
     * @param lat1 Latitude of the first point.
     * @param lon1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lon2 Longitude of the second point.
     * @return The distance in kilometers.
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        Log.d(TAG, "Calculated distance: " + distance + " km");
        return distance;
    }

    /**
     * Simulates fine calculation in a school or hospital zone.
     */
    public void simulateZoneFineCalculation() {
        double vehicleLatitude = 28.6139; // Example latitude
        double vehicleLongitude = 77.2090; // Example longitude
        double vehicleSpeed = 60.0; // Example speed in km/h
        double speedLimit = 40.0; // Example speed limit in km/h

        boolean isSpecialZone = isInSpecialZone(vehicleLatitude, vehicleLongitude);
        double fine = calculateFine(vehicleSpeed, speedLimit, isSpecialZone);

        Log.d(TAG, "Simulated fine: " + fine);
    }
}
