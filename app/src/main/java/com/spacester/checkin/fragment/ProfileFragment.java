package com.spacester.checkin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.PrivacyActivity;
import com.spacester.checkin.R;
import com.spacester.checkin.TermsActivity;
import com.spacester.checkin.staff.StaffActivity;
import com.spacester.checkin.welcome.WelcomeActivity;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView name = v.findViewById(R.id.name);
        TextView email = v.findViewById(R.id.email);
        TextView phone = v.findViewById(R.id.phone);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    name.setText(snapshot.child("name").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Admin").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    v.findViewById(R.id.admin).setVisibility(View.VISIBLE);
                }else{
                    v.findViewById(R.id.admin).setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        v.findViewById(R.id.admin).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), StaffActivity.class));
        });

        v.findViewById(R.id.terms).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TermsActivity.class));
        });

        v.findViewById(R.id.privacy).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), PrivacyActivity.class));
        });

        v.findViewById(R.id.logout).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), WelcomeActivity.class));
            getActivity().finish();
        });



        v.findViewById(R.id.logout).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), WelcomeActivity.class));
            getActivity().finish();
        });

        v.findViewById(R.id.invite).setOnClickListener(view -> {
            String msg = "This is a self checkIn app - download now "+ "https://play.google.com/store/apps/details?id=com.spacester.checkin";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            startActivity(Intent.createChooser(intent, "Share using"));
        });

        return v;
    }
}