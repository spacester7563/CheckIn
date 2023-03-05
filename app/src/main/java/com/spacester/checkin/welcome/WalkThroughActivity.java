package com.spacester.checkin.welcome;

import android.content.Intent;
import android.os.Bundle;

import com.spacester.checkin.MainActivity;
import com.spacester.checkin.R;

import io.github.shubhkotnala.walkthrough.WalkthroughActivity;
import io.github.shubhkotnala.walkthrough.model.WalkthroughItem;

public class WalkThroughActivity extends WalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addWalkthroughItem(new WalkthroughItem(R.drawable.one, "#fff","Booking","Book rooms from the app"));
        addWalkthroughItem(new WalkthroughItem(R.drawable.two, "#fff","Self Check-In", "Hassle free Self Check-In"));
        addWalkthroughItem(new WalkthroughItem(R.drawable.three, "#fff","Enjoy","Keep track of everything & Enjoy"));

        startWalkthrough();

    }

    @Override
    public void onFinish() {
        super.onFinish();

        startActivity(new Intent(WalkThroughActivity.this, MainActivity.class));
        finish();

    }

}