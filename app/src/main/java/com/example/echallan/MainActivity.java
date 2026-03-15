package com.example.echallan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure activity_main.xml exists

        // Set up the driver login button
        findViewById(R.id.btnDriverLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DriverLoginActivity.class));
            }
        });

        // Set up the police login button
        findViewById(R.id.btnPoliceLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PoliceLoginActivity.class));
            }
        });
    }
}
