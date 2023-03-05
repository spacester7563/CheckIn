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
import com.spacester.checkin.adapter.AdapterUser;
import com.spacester.checkin.model.ModelRooms;
import com.spacester.checkin.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    RecyclerView rooms_list;

    List<UserModel> roomsList;
    AdapterUser adapterRooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        findViewById(R.id.back).setOnClickListener(view -> {
            onBackPressed();
        });

        rooms_list = findViewById(R.id.rooms_list);
        rooms_list.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        roomsList = new ArrayList<>();
        getUser();

    }


    private void getUser() {

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomsList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    UserModel modelRooms = ds.getValue(UserModel.class);
                    roomsList.add(modelRooms);
                }
                adapterRooms = new AdapterUser(UserListActivity.this, roomsList);
                rooms_list.setAdapter(adapterRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}