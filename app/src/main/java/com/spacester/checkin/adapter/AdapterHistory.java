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
import com.spacester.checkin.model.HistoryModel;
import com.spacester.checkin.model.ModelRooms;

import java.util.HashMap;
import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyHolder> {

    final  Context context;
    final  List<HistoryModel> roomsList;

    String cost = "";


    public AdapterHistory(Context context, List<HistoryModel> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_list, parent, false);
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
                    cost = snapshot.child("cost").getValue().toString();

                    float time = Float.parseFloat(roomsList.get(position).getTime());
                    float currentTime = System.currentTimeMillis();
                    float dayInDec = currentTime-time;
                    float dayIn = dayInDec / 86400000;
                    int day = Math.round(dayIn);

                    if(day > 0){
                        holder.days.setText("Total Days "+ day);
                    }else{
                        holder.days.setText("Total Days "+ 1);
                    }


                    if(day > 0){
                        int total = Integer.parseInt(cost) * day;
                        holder.total.setText("Total Cost "+ total);
                    }else{
                        holder.total.setText("Total Cost "+ cost);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        TextView total, days;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            total = itemView.findViewById(R.id.total);
            days = itemView.findViewById(R.id.days);

            name = itemView.findViewById(R.id.name);
            capacity = itemView.findViewById(R.id.capacity);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);

        }

    }
}
