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

public class AdapterRoomsBook extends RecyclerView.Adapter<AdapterRoomsBook.MyHolder> {

    final  Context context;
    final  List<ModelRooms> roomsList;

    public AdapterRoomsBook(Context context, List<ModelRooms> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_rooms_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.name.setText(roomsList.get(position).getName());
        holder.capacity.setText(roomsList.get(position).getCapacity());
        holder.cost.setText("$"+roomsList.get(position).getCost());
        Glide.with(context).load(roomsList.get(position).getImage()).into(holder.image);

        FirebaseDatabase.getInstance().getReference().child("Booked").child(roomsList.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.available.setVisibility(View.GONE);
                    holder.notAvailable.setVisibility(View.VISIBLE);
                    holder.book.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookId = ""+System.currentTimeMillis();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", bookId);
                hashMap.put("user", FirebaseAuth.getInstance().getCurrentUser().getUid());
                hashMap.put("room", roomsList.get(position).getId());
                hashMap.put("time", ""+System.currentTimeMillis());
                FirebaseDatabase.getInstance().getReference().child("Booked").child(roomsList.get(position).getId()).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("Book").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).setValue(hashMap);
                Toast.makeText(context, "Booked", Toast.LENGTH_SHORT).show();
                holder.book.setVisibility(View.GONE);
                holder.cancel.setVisibility(View.VISIBLE);

            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Book").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).getRef().removeValue();
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                holder.book.setVisibility(View.VISIBLE);
                holder.cancel.setVisibility(View.GONE);
                FirebaseDatabase.getInstance().getReference().child("Booked").child(roomsList.get(position).getId()).getRef().removeValue();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Book").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(roomsList.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.book.setVisibility(View.GONE);
                    holder.cancel.setVisibility(View.VISIBLE);
                }else {
                    holder.book.setVisibility(View.VISIBLE);
                    holder.cancel.setVisibility(View.GONE);
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
        Button book, cancel;
        TextView available, notAvailable;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            available = itemView.findViewById(R.id.available);
            notAvailable = itemView.findViewById(R.id.notavailable);
            book = itemView.findViewById(R.id.book);
            cancel = itemView.findViewById(R.id.cancel);
            name = itemView.findViewById(R.id.name);
            capacity = itemView.findViewById(R.id.capacity);
            cost = itemView.findViewById(R.id.cost);
            image = itemView.findViewById(R.id.image);

        }

    }
}
