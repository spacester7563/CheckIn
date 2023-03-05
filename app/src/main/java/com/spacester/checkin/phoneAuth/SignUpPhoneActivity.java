package com.spacester.checkin.phoneAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.spacester.checkin.MainActivity;
import com.spacester.checkin.R;
import com.spacester.checkin.emailAuth.LoginActivity;
import com.spacester.checkin.emailAuth.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

public class SignUpPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_phone);

        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phoneNo);

        String phoneNo = getIntent().getStringExtra("phone");
        phone.setText(phoneNo);

        findViewById(R.id.phone).setOnClickListener(view -> {
            if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ){
                Toast.makeText(this, "Please enter the details", Toast.LENGTH_LONG).show();
            }else{

                Map<String, Object> hashmap = new HashMap<>();
                hashmap.put("name", name.getText().toString());
                hashmap.put("email", email.getText().toString());
                hashmap.put("phone", phone.getText().toString());
                hashmap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashmap);
                startActivity(new Intent(SignUpPhoneActivity.this, MainActivity.class));
                finish();

            }
        });


    }
}