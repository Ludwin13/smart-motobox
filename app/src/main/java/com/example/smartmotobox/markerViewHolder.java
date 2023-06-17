package com.example.smartmotobox;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class markerViewHolder extends RecyclerView.ViewHolder{

    TextView Latitude_Holder, Longitude_Holder, Time_Holder;

    public markerViewHolder(@NonNull View itemView) {
        super(itemView);

        Latitude_Holder = itemView.findViewById(R.id.Latitude_Holder);
        Longitude_Holder = itemView.findViewById(R.id.Longitude_Holder);
        Time_Holder = itemView.findViewById(R.id.Time_Holder);

    }
}
