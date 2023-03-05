package com.spacester.checkin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacester.checkin.R;
import com.spacester.checkin.model.ModelBook;
import com.spacester.checkin.model.ModelRooms;

import java.util.HashMap;
import java.util.List;

public class AdapterBooking extends RecyclerView.Adapter<AdapterBooking.MyHolder> {

    final  Context context;
    final  List<ModelBook> roomsList;

    public AdapterBooking(Context context, List<ModelBook> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomsList.get(position).getRoom()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    holder.name.setText(snapshot.child("name").getValue().toString());
                    holder.capacity.setText(snapshot.child("capacity").getValue().toString());
                    holder.cost.setText("$"+snapshot.child("cost").getValue().toString());
                    Glide.with(context).load(snapshot.child("image").getValue().toString()).into(holder.image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(roomsList.get(position).getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    holder.userName.setText(snapshot.child("name").getValue().toString());
                    holder.email.setText(snapshot.child("email").getValue().toString());
                    holder.phone.setText(snapshot.child("phone").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Book").child(roomsList.get(position).getUser()).child(roomsList.get(position).getRoom()).getRef().removeValue();
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("Booked").child(roomsList.get(position).getId()).getRef().removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        TextView name, capacity, cost;
        ImageView image;
        Button cancel;
        TextView userName, email, phone;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            cancel = itemView.findViewById(R.id.cancel);
            name = itemView.findViewById(R.id.name);
            capacity = itemView.findViewById(R.id.capacity);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);

        }

    }
}
