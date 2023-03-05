package com.spacester.checkin.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.spacester.checkin.MainActivity;
import com.spacester.checkin.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences settings = getSharedPreferences("splash", 0);
        boolean fistRun = settings.getBoolean("splashScreen", false);

        if(!fistRun){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("splashScreen", true);
            editor.apply();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, WalkThroughActivity.class));
                finish();
            }, 3000);
        }else{
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }, 3000);
        }



    }
}