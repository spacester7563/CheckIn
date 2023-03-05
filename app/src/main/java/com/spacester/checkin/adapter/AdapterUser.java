package com.spacester.checkin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spacester.checkin.R;
import com.spacester.checkin.model.ModelRooms;
import com.spacester.checkin.model.UserModel;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    final  Context context;
    final  List<UserModel> roomsList;

    public AdapterUser(Context context, List<UserModel> roomsList){
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.name.setText(roomsList.get(position).getName());
        holder.email.setText(roomsList.get(position).getEmail());
        holder.phone.setText(roomsList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        TextView name, email, phone;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);


        }

    }
}
