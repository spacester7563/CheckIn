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
import com.spacester.checkin.model.ModelRooms;

import java.util.HashMap;
import java.util.List;

public class AdapterRoomsCheckIn extends RecyclerView.Adapter<AdapterRoomsCheckIn.MyHolder> {

    final  Context context;
    final  List<ModelRooms> roomsList;

    public AdapterRoomsCheckIn(Context context, List<ModelRooms> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkin_rooms_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.name.setText(roomsList.get(position).getName());
        holder.capacity.setText(roomsList.get(position).getCapacity());
        holder.cost.setText("$"+roomsList.get(position).getCost());
        Glide.with(context).load(roomsList.get(position).getImage()).into(holder.image);

        holder.in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkInId = ""+System.currentTimeMillis();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", checkInId);
                hashMap.put("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
                hashMap.put("room", roomsList.get(position).getId());
                hashMap.put("time", ""+System.currentTimeMillis());
                hashMap.put("check", "in");

                FirebaseDatabase.getInstance().getReference().child("Check").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).setValue(hashMap);
                Toast.makeText(context, "Checked In", Toast.LENGTH_SHORT).show();
                holder.in.setVisibility(View.GONE);
                holder.out.setVisibility(View.VISIBLE);

            }
        });

        holder.out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("Check").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            float time = Float.parseFloat(snapshot.child("time").getValue().toString());
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
                                int total = Integer.parseInt(roomsList.get(position).getCost()) * day;
                                holder.total.setText("Total Cost "+ total);
                            }else{
                                holder.total.setText("Total Cost "+ roomsList.get(position).getCost());
                            }

                            holder.days.setVisibility(View.VISIBLE);

                            holder.total.setVisibility(View.VISIBLE);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("check", "out");

                FirebaseDatabase.getInstance().getReference().child("Check").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).updateChildren(hashMap);
                Toast.makeText(context, "Checked out", Toast.LENGTH_SHORT).show();

                holder.out.setVisibility(View.GONE);

                FirebaseDatabase.getInstance().getReference().child("Book").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).getRef().removeValue();
                FirebaseDatabase.getInstance().getReference().child("Booked").child(roomsList.get(position).getId()).getRef().removeValue();

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Check").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.in.setVisibility(View.GONE);
                    holder.out.setVisibility(View.VISIBLE);
                }else {
                    holder.in.setVisibility(View.VISIBLE);
                    holder.out.setVisibility(View.GONE);
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
        Button out, in;
        TextView total, days;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            total = itemView.findViewById(R.id.total);
            days = itemView.findViewById(R.id.days);
            out = itemView.findViewById(R.id.out);
            in = itemView.findViewById(R.id.in);
            name = itemView.findViewById(R.id.name);
            capacity = itemView.findViewById(R.id.capacity);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);

        }

    }
}
