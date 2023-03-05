package com.spacester.checkin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.spacester.checkin.emailAuth.LoginActivity;
import com.spacester.checkin.fragment.HistoryFragment;
import com.spacester.checkin.fragment.HomeFragment;
import com.spacester.checkin.fragment.ProfileFragment;
import com.spacester.checkin.welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(navigationSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();


    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationSelected = item -> {

        switch (item.getItemId()){
            case R.id.home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.history:
                selectedFragment = new HistoryFragment();
                break;
            case R.id.profile:
                selectedFragment = new ProfileFragment();
                break;
        }

        if (selectedFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
        }

        return true;
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }
    }
}