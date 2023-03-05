package com.spacester.checkin.emailAuth;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        EditText password = findViewById(R.id.password);


        findViewById(R.id.login).setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        findViewById(R.id.singUp).setOnClickListener(view -> {
            if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || password.getText().toString().isEmpty() ){
                Toast.makeText(this, "Please enter the details", Toast.LENGTH_LONG).show();
            }else if(password.getText().length() < 6){
                Toast.makeText(this, "Password should be greater than 6 characters", Toast.LENGTH_LONG).show();
            }else{

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Map<String, Object> hashmap = new HashMap<>();
                        hashmap.put("name", name.getText().toString());
                        hashmap.put("email", email.getText().toString());
                        hashmap.put("phone", phone.getText().toString());
                        hashmap.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashmap);
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                });

            }
        });

    }
}