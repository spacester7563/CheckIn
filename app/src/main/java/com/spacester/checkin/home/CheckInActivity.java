package com.spacester.checkin.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.R;
import com.spacester.checkin.adapter.AdapterRoomsBook;
import com.spacester.checkin.adapter.AdapterRoomsCheckIn;
import com.spacester.checkin.model.ModelRooms;

import java.util.ArrayList;
import java.util.List;

public class CheckInActivity extends AppCompatActivity {

    RecyclerView rooms_list;

    List<ModelRooms> roomsList;
    AdapterRoomsCheckIn adapterRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        findViewById(R.id.back).setOnClickListener(view -> {
            onBackPressed();
        });

        rooms_list = findViewById(R.id.rooms_list);
        rooms_list.setLayoutManager(new LinearLayoutManager(CheckInActivity.this));
        roomsList = new ArrayList<>();
        getRooms();

    }


    private void getRooms() {

        FirebaseDatabase.getInstance().getReference().child("Book").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference().child("Rooms").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {

                            for(DataSnapshot snap : snapshot2.getChildren()){
                                if(snap.getKey().equals(ds.getKey())){
                                    ModelRooms modelRooms = snap.getValue(ModelRooms.class);
                                    roomsList.add(modelRooms);
                                }
                            }

                            adapterRooms = new AdapterRoomsCheckIn(CheckInActivity.this, roomsList);
                            rooms_list.setAdapter(adapterRooms);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}