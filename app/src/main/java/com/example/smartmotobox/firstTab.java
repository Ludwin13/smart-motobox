package com.example.smartmotobox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class firstTab extends Fragment {

    private GoogleMap googleMap;
    private DatabaseReference databaseReference;
    Marker marker;
    private FirebaseDatabase firebaseDatabase;

    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAINACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fragment fragment = new MapFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        getChildFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        if(mapFragment != null) {
            mapFragment.getMapAsync((OnMapReadyCallback) this);
        }

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("Location");

//        databaseReference.child("Latitude").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        databaseReference.child("Longitude").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        return inflater.inflate(R.layout.fragment_first_tab, container, false);
    }
}