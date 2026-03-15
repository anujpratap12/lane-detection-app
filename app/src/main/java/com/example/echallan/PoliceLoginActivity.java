package com.example.echallan;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PoliceLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.police_login);

        findViewById(R.id.btnPoliceLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement police login logic here
                Toast.makeText(PoliceLoginActivity.this, "Police Login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
