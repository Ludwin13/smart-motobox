package com.example.smartmotobox;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class secondTab extends Fragment {

    Button unlock, lock, riding, parked, biometric;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);


        unlock = view.findViewById(R.id.unlockBtn);
        lock = view.findViewById(R.id.lockBtn);
        riding = view.findViewById(R.id.ridingBtn);
        parked = view.findViewById(R.id.parkingBtn);
        biometric = view.findViewById(R.id.enrollFingerBtn);

        biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), enrollFingerprint.class);
                startActivity(intent);
            }
        });

        return view;
    }
}