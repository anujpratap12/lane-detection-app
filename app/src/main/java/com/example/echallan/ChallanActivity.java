// ChallanActivity.java
package com.example.echallan;
import com.example.echallan.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ChallanActivity extends AppCompatActivity {
    private EditText vehicleNumberInput;
    private EditText driverNameInput;
    private EditText offenseTypeInput;
    private EditText fineAmountInput;
    private Button generateChallanButton;
    private TextView challanDetailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan);
        vehicleNumberInput = findViewById(R.id.vehicle_number_input);
        driverNameInput = findViewById(R.id.driver_name_input);
        offenseTypeInput = findViewById(R.id.offense_type_input);
        fineAmountInput = findViewById(R.id.fine_amount_input);
        generateChallanButton = findViewById(R.id.generate_challan_button);
        challanDetailsText = findViewById(R.id.challan_details_text);

        generateChallanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleNumber = vehicleNumberInput.getText().toString();
                String driverName = driverNameInput.getText().toString();
                String offenseType = offenseTypeInput.getText().toString();
                String fineAmount = fineAmountInput.getText().toString();

                if (vehicleNumber.isEmpty() || driverName.isEmpty() || offenseType.isEmpty() || fineAmount.isEmpty()) {
                    Toast.makeText(ChallanActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    String challanDetails = "Challan Details:\n" +
                            "Vehicle Number: " + vehicleNumber + "\n" +
                            "Driver Name: " + driverName + "\n" +
                            "Offense Type: " + offenseType + "\n" +
                            "Fine Amount: Rs." + fineAmount;

                    challanDetailsText.setText(challanDetails);
                }
            }
        });
    }
}
