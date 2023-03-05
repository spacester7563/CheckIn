package com.spacester.checkin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spacester.checkin.R;
import com.spacester.checkin.home.BookRoomActivity;
import com.spacester.checkin.home.CheckInActivity;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.findViewById(R.id.invite).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), BookRoomActivity.class));
        });

        v.findViewById(R.id.checkin).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CheckInActivity.class));
        });



        return v;
    }
}