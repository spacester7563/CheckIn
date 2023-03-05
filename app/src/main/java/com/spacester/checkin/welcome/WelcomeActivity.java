package com.spacester.checkin.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.spacester.checkin.R;
import com.spacester.checkin.emailAuth.LoginActivity;
import com.spacester.checkin.phoneAuth.PhoneNoActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.phone).setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, PhoneNoActivity.class));
        });

        findViewById(R.id.email).setOnClickListener(view -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        });

    }
}