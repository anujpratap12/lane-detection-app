package com.example.echallan;
import android.util.Log;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterface {

    // Endpoint to retrieve fine details for a given violation ID
    @GET("fine/{violationId}")
    Call<FineDetails> getFineDetails(@Path("violationId") int violationId);
}



public class VehicleViolation {

    private static final String TAG = "VehicleViolation";

    private static final double GENERAL_FINE_RATE = 10.0; // Base fine rate per km/h over limit
    private static final double SPECIAL_ZONE_FINE_MULTIPLIER = 1.20; // 20% extra for special zones

    private double speed;
    private double speedLimit;
    private boolean isSpecialZone;
    private boolean isLaneViolation;

    public VehicleViolation(double speed, double speedLimit, boolean isSpecialZone, boolean isLaneViolation) {
        this.speed = speed;
        this.speedLimit = speedLimit;
        this.isSpecialZone = isSpecialZone;
        this.isLaneViolation = isLaneViolation;
    }

    /**
     * Calculates the total fine for the detected violations.
     *
     * @return The total fine amount.
     */
    public double calculateTotalFine() {
        double totalFine = 0.0;

        // Calculate overspeeding fine
        if (speed > speedLimit) {
            double overSpeed = speed - speedLimit;
            double overspeedFine = overSpeed * GENERAL_FINE_RATE;
            if (isSpecialZone) {
                overspeedFine *= SPECIAL_ZONE_FINE_MULTIPLIER;
                Log.d(TAG, "Special zone detected. Overspeeding fine increased by 20%.");
            }
            totalFine += overspeedFine;
            Log.d(TAG, "Overspeeding fine: " + overspeedFine);
        }

        // Calculate lane violation fine
        if (isLaneViolation) {
            double laneViolationFine = 500.0; // Example lane violation fine
            totalFine += laneViolationFine;
            Log.d(TAG, "Lane violation fine: " + laneViolationFine);
        }

        Log.d(TAG, "Total fine calculated: " + totalFine);
        return totalFine;
    }

    /**
     * Checks if the vehicle is overspeeding.
     *
     * @return True if the vehicle is overspeeding, false otherwise.
     */
    public boolean isOverspeeding() {
        return speed > speedLimit;
    }

    /**
     * Provides a summary of the violations and fines.
     *
     * @return A string summary of the violations.
     */
    public String getViolationSummary() {
        StringBuilder summary = new StringBuilder("Violation Summary:\n");

        if (isOverspeeding()) {
            summary.append("- Overspeeding: Detected\n");
            summary.append("  Speed: ").append(speed).append(" km/h (Limit: ").append(speedLimit).append(" km/h)\n");
        } else {
            summary.append("- Overspeeding: Not Detected\n");
        }

        if (isLaneViolation) {
            summary.append("- Lane Violation: Detected\n");
        } else {
            summary.append("- Lane Violation: Not Detected\n");
        }

        if (isSpecialZone) {
            summary.append("- Special Zone: Yes (20% fine increase applied)\n");
        } else {
            summary.append("- Special Zone: No\n");
        }

        summary.append("- Total Fine: ").append(Utils.formatFine(calculateTotalFine())).append("\n");

        return summary.toString();
    }

    /**
     * Creates a Violation object to send to the backend.
     * This will be used to upload violation data via the API.
     *
     * @return A Violation object with the calculated fine and other details.
     */
    public Violation createViolationObject() {
        // Create a Violation object
        String licensePlate = "ABC123"; // This can be dynamically fetched or passed as a parameter
        String violationType = "Speeding"; // This could be dynamic based on the type of violation
        String location = "Main St"; // Example location
        String timestamp = "2024-12-09T15:00:00"; // Example timestamp, use current time or pass dynamically

        // Return a new Violation object
        return new Violation(licensePlate, violationType, location, timestamp);
    }

    public static void main(String[] args) {
        // Example usage of VehicleViolation class
        VehicleViolation violation = new VehicleViolation(60.0, 40.0, true, true);

        Log.d(TAG, violation.getViolationSummary());

        // Create a Violation object to send to the backend
        Violation violationObject = violation.createViolationObject();

        // Here, you would call the API to upload violationObject
        // Example: apiInterface.uploadViolationData(violationObject);
    }
}
