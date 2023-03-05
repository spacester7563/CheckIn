package com.spacester.checkin.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.R;
import com.spacester.checkin.adapter.AdapterHistory;
import com.spacester.checkin.adapter.AdapterRoomsBook;
import com.spacester.checkin.home.BookRoomActivity;
import com.spacester.checkin.model.HistoryModel;
import com.spacester.checkin.model.ModelRooms;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    RecyclerView rooms_list;

    List<HistoryModel> roomsList;
    AdapterHistory adapterRooms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);


        rooms_list = v.findViewById(R.id.list);
        rooms_list.setLayoutManager(new LinearLayoutManager(getContext()));
        roomsList = new ArrayList<>();
        getRooms();

        return v;
    }


    private void getRooms() {

        FirebaseDatabase.getInstance().getReference().child("Check").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    HistoryModel modelRooms = ds.getValue(HistoryModel.class);
                    roomsList.add(modelRooms);
                }
                adapterRooms = new AdapterHistory(getContext(), roomsList);
                rooms_list.setAdapter(adapterRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}