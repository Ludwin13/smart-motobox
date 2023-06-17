package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class mapMarkerDatabase extends AppCompatActivity {

    FirebaseRecyclerOptions<Markers> options;
    FirebaseRecyclerAdapter<Markers, markerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker_database);

        RecyclerView markerRecyclerView = findViewById(R.id.markerRecyclerView);

        List<Markers> markers = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mapMarkerDatabase.this);
        layoutManager.setReverseLayout(true);
        markerRecyclerView.setLayoutManager(layoutManager);

        DatabaseReference locationMarkers = FirebaseDatabase.getInstance().getReference("Location");


        options = new FirebaseRecyclerOptions.Builder<Markers>().setQuery(locationMarkers, Markers.class).build();
        adapter = new FirebaseRecyclerAdapter<Markers, markerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull markerViewHolder holder, int position, @NonNull Markers model) {
                holder.Latitude_Holder.setText(""+model.getLatitude());
                holder.Longitude_Holder.setText(""+model.getLongitude());
                holder.Time_Holder.setText(""+model.getTime());

            }

            @NonNull
            @Override
            public markerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_view, parent, false);
                return new markerViewHolder(v);
            }
        };


    }
}