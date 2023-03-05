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
import com.spacester.checkin.adapter.AdapterBooking;
import com.spacester.checkin.model.ModelBook;

import java.util.ArrayList;
import java.util.List;

public class BookingsListActivity extends AppCompatActivity {

    RecyclerView book_list;
    List<ModelBook> bookList;
    AdapterBooking adapterBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_list);

        findViewById(R.id.back).setOnClickListener(view -> {
            onBackPressed();
        });

        book_list = findViewById(R.id.rooms_list);
        book_list.setLayoutManager(new LinearLayoutManager(BookingsListActivity.this));
        bookList = new ArrayList<>();
        getBookings();

    }

    private void getBookings() {

        FirebaseDatabase.getInstance().getReference().child("Book").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    for(DataSnapshot snap : ds.getChildren()){
                        ModelBook modelBook = snap.getValue(ModelBook.class);
                        bookList.add(modelBook);
                    }

                    adapterBooking = new AdapterBooking(BookingsListActivity.this, bookList);
                    book_list.setAdapter(adapterBooking);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}