package com.example.echallan;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.List;

public class LaneViolationActivity extends AppCompatActivity {

    private static final String TAG = "LaneViolationDetection";
    private VideoCapture videoCapture;
    private Mat currentFrame;
    private VideoView videoViewProcessedFrame; // VideoView to display processed frames

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lane_violation);

        // Initialize OpenCV
        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "OpenCV initialization failed");
        } else {
            Log.d(TAG, "OpenCV initialization succeeded");
        }

        // Initialize VideoView to display processed frames
        videoViewProcessedFrame = findViewById(R.id.videoViewProcessedFrame); // Make sure to have this VideoView in your XML

        // Initialize VideoCapture
        videoCapture = new VideoCapture("res/raw/sample_video.mp4"); // Replace with actual video path or URI
        Log.d(TAG, "Video Capture initialized");

        // Set the video URI to your VideoView
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_video);
        videoViewProcessedFrame.setVideoURI(videoUri);


        // Start video playback
        videoViewProcessedFrame.start();

        // Start frame processing in the background
        processVideoFrames();
    }

    /**
     * Process video frames to detect lane violations.
     */
    private void processVideoFrames() {
        if (videoCapture.isOpened()) {
            while (videoCapture.grab()) {
                videoCapture.retrieve(currentFrame);

                // Process the current frame
                Mat processedFrame = detectLaneViolation(currentFrame, null, null);

                // Display the processed frame in ImageView or elsewhere
                showProcessedFrame(processedFrame);
            }
        } else {
            Log.e(TAG, "Error opening video stream.");
        }
    }

    /**
     * Detects lane violations by checking if the vehicle crosses the lane boundary.
     *
     * @param frame The current video frame.
     * @param laneLines List of detected lane lines (this can be an empty list or null for testing).
     * @param vehiclePosition The detected position of the vehicle (null for testing).
     * @return The processed frame with lane violations marked.
     */
    public Mat detectLaneViolation(Mat frame, List<Point[]> laneLines, Point vehiclePosition) {
        // If lane lines or vehicle position are not available, mock some data for testing
        if (laneLines == null || vehiclePosition == null) {
            laneLines = mockLaneLines(); // Mock some lane lines
            vehiclePosition = new Point(320, 240); // Mock vehicle position (center of the frame)
        }

        // Draw detected lanes
        for (Point[] laneLine : laneLines) {
            Imgproc.line(frame, laneLine[0], laneLine[1], new Scalar(255, 0, 0), 2);
        }

        // Check if the vehicle crosses the lane boundaries
        for (Point[] laneLine : laneLines) {
            if (isVehicleOutOfLane(laneLine, vehiclePosition)) {
                Imgproc.circle(frame, vehiclePosition, 10, new Scalar(0, 0, 255), -1); // Mark violation with a red circle
                Log.d(TAG, "Lane violation detected at position: " + vehiclePosition.toString());
            }
        }

        return frame;
    }

    /**
     * Checks if the vehicle is out of lane boundaries.
     *
     * @param laneLine The lane line as a pair of points.
     * @param vehiclePosition The current position of the vehicle.
     * @return True if the vehicle is out of lane; false otherwise.
     */
    private boolean isVehicleOutOfLane(Point[] laneLine, Point vehiclePosition) {
        double slope = (laneLine[1].y - laneLine[0].y) / (laneLine[1].x - laneLine[0].x);
        double intercept = laneLine[0].y - (slope * laneLine[0].x);

        double distanceFromLine = Math.abs(slope * vehiclePosition.x - vehiclePosition.y + intercept) /
                Math.sqrt(slope * slope + 1);

        // Example threshold for lane boundary
        return distanceFromLine > 30;
    }

    /**
     * Mocks lane lines for testing (mock data).
     *
     * @return A list of points representing lane lines.
     */
    private List<Point[]> mockLaneLines() {
        // Example mock lane lines
        Point[] lane1 = {new Point(100, 0), new Point(100, 480)};
        Point[] lane2 = {new Point(500, 0), new Point(500, 480)};
        return List.of(lane1, lane2);
    }

    /**
     * Displays the processed frame in an ImageView or other components.
     *
     * @param processedFrame The processed frame.
     */
    private void showProcessedFrame(Mat processedFrame) {
        // Convert Mat to Bitmap for displaying
        Bitmap bitmap = Bitmap.createBitmap(processedFrame.cols(), processedFrame.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(processedFrame, bitmap);

        // Note: Since we are working with a video, you may have to implement custom solutions
        // to overlay the processed frame onto the video, such as using SurfaceView or streaming
        // processed frames. For now, this is just an example.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoCapture != null && videoCapture.isOpened()) {
            videoCapture.release(); // Release video capture resources
        }
    }
}
