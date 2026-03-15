package com.example.echallan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class VideoProcessingActivity extends AppCompatActivity {

    private static final String TAG = "VideoProcessing";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private VideoCapture videoCapture;
    private CascadeClassifier vehicleDetector;
    private VideoView videoViewProcessedFrame; // VideoView to show processed frames

    // OpenCV callback
    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.d(TAG, "OpenCV loaded successfully");
                initializeVehicleDetector();
                startVideoProcessing();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_processing);

        // Initialize VideoView to display processed video frames
        videoViewProcessedFrame = findViewById(R.id.videoViewProcessedFrame);

        // Check for camera permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            initializeOpenCV();
        }
    }

    private void initializeOpenCV() {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, loaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    private void initializeVehicleDetector() {
        try {
            // Load the pre-trained vehicle detector cascade file
            InputStream is = getResources().openRawResource(R.raw.haarcascade_vehicle);
            File cascadeDir = getDir("cascade", MODE_PRIVATE);
            File cascadeFile = new File(cascadeDir, "haarcascade_vehicle.xml");
            FileOutputStream fos = new FileOutputStream(cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            is.close();
            fos.close();

            vehicleDetector = new CascadeClassifier(cascadeFile.getAbsolutePath());
            if (vehicleDetector.empty()) {
                Log.e(TAG, "Failed to load vehicle detector");
                vehicleDetector = null;
            } else {
                Log.d(TAG, "Vehicle detector loaded successfully");
            }

            cascadeFile.delete();
            cascadeDir.delete();
        } catch (Exception e) {
            Log.e(TAG, "Error loading vehicle detector: " + e.getMessage());
        }
    }

    private void startVideoProcessing() {
        // Initialize VideoCapture (use a file or camera)
        videoCapture = new VideoCapture("res/raw/sample_video.mp4"); // Replace with actual video path or URI

        if (videoCapture.isOpened()) {
            processVideoFrames();
        } else {
            Log.e(TAG, "Failed to open video stream.");
        }
    }

    // Method to process video frames in real-time
    private void processVideoFrames() {
        Mat currentFrame = new Mat();
        while (videoCapture.read(currentFrame)) {
            // Process each frame
            Mat processedFrame = processFrame(currentFrame);

            // You need to convert the processed frame to a Bitmap to show it on the VideoView
            Bitmap bitmap = Bitmap.createBitmap(processedFrame.cols(), processedFrame.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(processedFrame, bitmap);

            // Set the bitmap to the VideoView or any other UI component
            // This is an example and may require a custom solution to overlay the frame onto a video view
            videoViewProcessedFrame.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_video));  // Example video
            videoViewProcessedFrame.start();
        }
    }

    // Process video frames to detect vehicles
    public Mat processFrame(Mat inputFrame) {
        Mat grayFrame = new Mat();
        Imgproc.cvtColor(inputFrame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        MatOfRect vehicles = new MatOfRect();
        if (vehicleDetector != null) {
            vehicleDetector.detectMultiScale(grayFrame, vehicles, 1.1, 2, 2,
                    new Size(30, 30), new Size());
        }

        for (Rect rect : vehicles.toArray()) {
            Imgproc.rectangle(inputFrame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);  // Draw rectangle around detected vehicles
            Log.d(TAG, "Vehicle detected at: " + rect.tl() + " to " + rect.br());
        }

        return inputFrame;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeOpenCV();
            } else {
                Toast.makeText(this, "Camera permission is required to process video", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
