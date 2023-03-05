package com.spacester.checkin.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.R;
import com.spacester.checkin.adapter.AdapterRooms;
import com.spacester.checkin.model.ModelRooms;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {

    RecyclerView rooms_list;

    List<ModelRooms> roomsList;
    AdapterRooms adapterRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        findViewById(R.id.back).setOnClickListener(view -> {
            onBackPressed();
        });

        rooms_list = findViewById(R.id.rooms_list);
        rooms_list.setLayoutManager(new LinearLayoutManager(RoomsActivity.this));
        roomsList = new ArrayList<>();
        getRooms();

    }

    private void getRooms() {

        FirebaseDatabase.getInstance().getReference().child("Rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    ModelRooms modelRooms = ds.getValue(ModelRooms.class);
                    roomsList.add(modelRooms);
                }
                adapterRooms = new AdapterRooms(RoomsActivity.this, roomsList);
                rooms_list.setAdapter(adapterRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}