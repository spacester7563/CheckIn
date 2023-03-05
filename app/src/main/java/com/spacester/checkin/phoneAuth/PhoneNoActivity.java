package com.spacester.checkin.phoneAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.spacester.checkin.R;

public class PhoneNoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_no);

        EditText editText = findViewById(R.id.editText);
        CountryCodePicker code = findViewById(R.id.code);

        String countryCode = code.getSelectedCountryCode();
        editText.setText("+"+countryCode);

        findViewById(R.id.phone).setOnClickListener(view -> {
            if (editText.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter your phone no.", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(PhoneNoActivity.this, VerifyOTPActivity.class);
                intent.putExtra("phone", editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}