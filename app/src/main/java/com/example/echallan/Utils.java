package com.example.echallan;

import android.location.Location;

public class Utils {

    // Function to calculate speed in km/h from distance (meters) and time (milliseconds)
    public static double calculateSpeed(double distanceInMeters, long timeInMilliseconds) {
        if (timeInMilliseconds <= 0) return 0.0;
        double speedMetersPerSecond = distanceInMeters / (timeInMilliseconds / 1000.0);
        return speedMetersPerSecond * 3.6; // Convert to km/h
    }

    // Function to check if a location is within a geofenced area
    public static boolean isInSpecialZone(Location userLocation, Location zoneCenter, float radius) {
        if (userLocation == null || zoneCenter == null) return false;
        float distance = userLocation.distanceTo(zoneCenter); // Distance in meters
        return distance <= radius; // Returns true if within radius
    }

    // Function to calculate fine based on the violation and zone
    public static double calculateFine(double speed, boolean isLaneViolation, boolean isInSpecialZone) {
        double baseFine = 500;

        // Overspeeding Fine
        if (speed > 60) { // Assume 60 km/h as speed limit
            baseFine += (speed - 60) * 10; // Additional fine for every km/h over the limit
        }

        // Lane Violation Fine
        if (isLaneViolation) {
            baseFine += 300;
        }

        // Special Zone Extra Fine
        if (isInSpecialZone) {
            baseFine *= 1.20; // 20% additional fine
        }

        return baseFine;
    }

    // Function to extract vehicle registration number from image/video using OCR
    public static String extractVehicleNumber(String imagePath) {
        // This is a placeholder for OCR implementation
        // You can use libraries like Google ML Kit or Tesseract OCR
        return "Placeholder-Vehicle-Number"; // Replace with actual implementation
    }

    // Function to detect lane violation
    public static boolean isLaneViolation(double[] previousPosition, double[] currentPosition, double laneBoundary) {
        // Check if the vehicle crosses the lane boundary
        double distanceFromLane = Math.abs(currentPosition[1] - laneBoundary);
        return distanceFromLane > 0.5; // Assume 0.5 meters as permissible deviation
    }

    // Function to format fines for display
    public static String formatFineAmount(double fine) {
        return "₹" + String.format("%.2f", fine); // Example: ₹1200.50
    }

    // Function to convert milliseconds to a human-readable time format
    public static String formatTime(long timeInMillis) {
        int seconds = (int) (timeInMillis / 1000) % 60;
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int hours = (int) ((timeInMillis / (1000 * 60 * 60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // Function to calculate the distance between two GPS coordinates (Haversine formula)
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // Convert to meters
    }
}
