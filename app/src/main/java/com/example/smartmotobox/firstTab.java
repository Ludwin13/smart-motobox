package com.example.smartmotobox;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class firstTab extends Fragment {


    public firstTab() {
    }

    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAIN ACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fragment fragment = new MapFragment();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        getChildFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        if(mapFragment != null) {
            mapFragment.getMapAsync((OnMapReadyCallback) this);
        }


        return inflater.inflate(R.layout.fragment_first_tab, container, false);
    }
}