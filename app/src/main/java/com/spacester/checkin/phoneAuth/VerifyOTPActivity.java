package com.spacester.checkin.phoneAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.MainActivity;
import com.spacester.checkin.R;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    private String verificationId;
    EditText otp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        mAuth = FirebaseAuth.getInstance();

        otp = findViewById(R.id.otp);

        String phoneNo = getIntent().getStringExtra("phone");
        sendVerificationCode(phoneNo);

        findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().isEmpty()){
                    Toast.makeText(VerifyOTPActivity.this, "Enter your OTP", Toast.LENGTH_LONG).show();
                }else {
                    verifyCode(otp.getText().toString());
                }
            }
        });

    }

    private void sendVerificationCode(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                60,
                TimeUnit.SECONDS,
                VerifyOTPActivity.this,
                mCallbacks
        );

    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOTPActivity.this, task -> {
                    if(task.isSuccessful()){
                        final String phone = getIntent().getStringExtra("phone");
                        Query userQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("phone").equalTo(phone);
                        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.getChildrenCount() > 0){
                                    Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent = new Intent(VerifyOTPActivity.this, SignUpPhoneActivity.class);
                                    intent.putExtra("phone", phone);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
    }


}