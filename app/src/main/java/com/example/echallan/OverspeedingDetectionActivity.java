package com.example.echallan;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.List;

public class OverspeedingDetectionActivity extends AppCompatActivity {

    private static final String TAG = "OverspeedingDetection";
    private CascadeClassifier vehicleDetector;

    private static final double SCHOOL_HOSPITAL_SPEED_LIMIT = 20.0; // Speed limit in school/hospital zones (km/h)
    private static final double GENERAL_SPEED_LIMIT = 50.0; // General speed limit (km/h)
    private static final double EXTRA_FINE_PERCENTAGE = 0.20; // 20% extra fine for overspeeding in school/hospital zones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overspeeding_detection);

        // Initialize vehicle detector
        vehicleDetector = Utils.loadCascade(this, R.raw.haarcascade_vehicle);
        if (vehicleDetector == null) {
            Log.e(TAG, "Failed to load vehicle detector");
        }
    }

    /**
     * Process a video frame to detect overspeeding.
     */
    public Mat detectOverspeeding(Mat frame, boolean isSchoolOrHospitalZone) {
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        MatOfRect vehicles = new MatOfRect();
        if (vehicleDetector != null) {
            vehicleDetector.detectMultiScale(grayFrame, vehicles, 1.1, 2, 0,
                    new Size(30, 30), new Size());
        }

        for (Rect rect : vehicles.toArray()) {
            // Draw rectangle around detected vehicle
            Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);

            // Calculate speed (dummy calculation, replace with actual logic)
            double speed = Utils.calculateSpeed(50, 2000); // distance in meters, time in milliseconds

            Log.d(TAG, "Vehicle detected with speed: " + speed + " km/h");

            // Check for overspeeding
            double speedLimit = isSchoolOrHospitalZone ? SCHOOL_HOSPITAL_SPEED_LIMIT : GENERAL_SPEED_LIMIT;
            if (speed > speedLimit) {
                double extraFine = 0.0;
                if (isSchoolOrHospitalZone) {
                    extraFine = (speed - speedLimit) * EXTRA_FINE_PERCENTAGE;
                }
                Log.d(TAG, "Overspeeding detected! Fine applied: " + calculateFine(speed, speedLimit, extraFine));
            }
        }

        return frame;
    }

    /**
     * Calculate the fine for overspeeding.
     */
    private double calculateFine(double speed, double speedLimit, double extraFine) {
        double baseFine = (speed - speedLimit) * 10; // Example fine calculation
        return baseFine + extraFine;
    }
}
