package com.example.smartmotobox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    Context context;
    List<Item> item;

    public MyAdapter(Context context, List<Item> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.notifID.setText(item.get(position).getId());
        holder.notifTrigger.setText(item.get(position).getTrigger());
        holder.notifDate.setText(item.get(position).getDate());
        holder.notifTime.setText(item.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
