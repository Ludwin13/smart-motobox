package com.example.smartmotobox;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView notifTrigger, notifDate, notifTime;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        notifTrigger = itemView.findViewById(R.id.notifTrigger);
        notifDate = itemView.findViewById(R.id.notifDate);
        notifTime = itemView.findViewById(R.id.notifTime);

    }
}
