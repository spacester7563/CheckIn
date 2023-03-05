package com.spacester.checkin.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.spacester.checkin.R;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        findViewById(R.id.back).setOnClickListener(view -> {
            onBackPressed();
        });

        findViewById(R.id.add).setOnClickListener(view -> {
            startActivity(new Intent(StaffActivity.this, AddRoomActivity.class));
        });


        findViewById(R.id.rooms).setOnClickListener(view -> {
            startActivity(new Intent(StaffActivity.this, RoomsActivity.class));
        });


        findViewById(R.id.customer).setOnClickListener(view -> {
            startActivity(new Intent(StaffActivity.this, UserListActivity.class));
        });

        findViewById(R.id.bookings).setOnClickListener(view -> {
            startActivity(new Intent(StaffActivity.this, BookingsListActivity.class));
        });


    }
}