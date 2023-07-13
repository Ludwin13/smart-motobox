package com.example.smartmotobox;

import static android.graphics.Typeface.BOLD;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class secondTab extends Fragment {

    Data data;
    Button unlock, lock, riding, parked, biometric, testBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, connectionDBRef, locationDBRef;
    TextView tvLockStatus, tvAlarmStatus, tvMotorStatus, tvConnectionStatus;

    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAINACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/Data");

        connectionDBRef = firebaseDatabase.getReference("/Connection");

        DatabaseReference locationDBRef = firebaseDatabase.getReference("/Location");

        data = new Data();

        testBtn = (Button) view.findViewById(R.id.testBtn);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        unlock = view.findViewById(R.id.unlockBtn);
        lock = view.findViewById(R.id.lockBtn);
        riding = view.findViewById(R.id.ridingBtn);
        parked = view.findViewById(R.id.parkedBtn);
        biometric = view.findViewById(R.id.enrollBtn);
        tvLockStatus = view.findViewById(R.id.tvLockStatus_Holder);
        tvAlarmStatus = view.findViewById(R.id.tvAlarmStatus_Holder);
        tvMotorStatus = view.findViewById(R.id.tvMotorStatus_Holder);
        tvConnectionStatus = view.findViewById(R.id.tvConnection_Holder);


        checkStatus();

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = timeFormat.format(Calendar.getInstance().getTime());
                String date = dateFormat.format(Calendar.getInstance().getTime());

                String key = locationDBRef.push().getKey();

                locationDBRef.child(key).setValue("Lat=14.222|Lon=10.222|Time="+time);

            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn_Lock = "0";
                data.setBtn_Lock(btn_Lock);
                lockBox(btn_Lock);
            }
        });

        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String btn_Lock = "1";
                data.setBtn_Lock(btn_Lock);
                unlockBox(btn_Lock);

            }
        });

        riding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn_Motor_Status = "0";
                data.setBtn_Lock(btn_Motor_Status);
                ridingMode(btn_Motor_Status);

            }
        });

        parked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn_Motor_Status = "1";
                data.setBtn_Lock(btn_Motor_Status);
                parkedMode(btn_Motor_Status);
            }
        });

        biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), enrollFingerprint.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void checkStatus() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lockStatus = snapshot.child("btn_Lock").getValue(String.class);
                if (lockStatus == "0") {

                    tvLockStatus.setText("LOCKED");
                    tvLockStatus.setTextColor(Color.GREEN);
                } else {
                    tvLockStatus.setText("UNLOCKED");
                    tvLockStatus.setTypeface(null, Typeface.BOLD);
                    tvLockStatus.setTextColor(Color.RED);
                }

                String alarmStatus = snapshot.child("/btn_Alarm").getValue(String.class);
                if (alarmStatus == "0") {

                    tvAlarmStatus.setText("OFF");
                    tvAlarmStatus.setTextColor(Color.RED);
                } else {
                    tvAlarmStatus.setText("N-Word");
                    tvAlarmStatus.setTypeface(null, Typeface.BOLD);
                    tvAlarmStatus.setTextColor(Color.GREEN);
                }

                String motorStatus = snapshot.child("btn_Motor_Status").getValue(String.class);
                if (motorStatus == "0") {

                    tvMotorStatus.setText("RIDING");
                    tvMotorStatus.setTextColor(Color.RED);
                } else {
                        tvMotorStatus.setText("PARKED");
                        tvMotorStatus.setTypeface(null, Typeface.BOLD);
                        tvMotorStatus.setTextColor(Color.GREEN);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference getAlarmStat = databaseReference.child("btn_Alarm");
        DatabaseReference getEnrollStat = databaseReference.child("btn_Enroll");
        DatabaseReference getLockStat = databaseReference.child("btn_Lock");
        DatabaseReference getMotorStat = databaseReference.child("btn_Motor_Status");

        getAlarmStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String alarm = snapshot.getValue(String.class);
                String alarmOff = "0";

                if(alarm.equals(alarmOff)) {
                    tvAlarmStatus.setText("OFF");
                    tvAlarmStatus.setTextColor(Color.RED);
                } else {
                    tvAlarmStatus.setText("ON");
                    tvAlarmStatus.setTypeface(null, Typeface.BOLD);
                    tvAlarmStatus.setTextColor(Color.GREEN);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        getLockStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lock = snapshot.getValue(String.class);
                String lockOff = "0";

                if(lock.equals(lockOff)) {
                    tvLockStatus.setText("LOCKED");
                    tvLockStatus.setTextColor(Color.GREEN);
                } else {
                    tvLockStatus.setText("UNLOCKED");
                    tvLockStatus.setTypeface(null, Typeface.BOLD);
                    tvLockStatus.setTextColor(Color.RED);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getMotorStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String motor = snapshot.getValue(String.class);
                String motorRiding = "0";

                if(motor.equals(motorRiding)) {
                    tvMotorStatus.setText("RIDING");
                    tvMotorStatus.setTextColor(Color.RED);
                } else {
                    tvMotorStatus.setText("PARKED");
                    tvMotorStatus.setTypeface(null, Typeface.BOLD);
                    tvMotorStatus.setTextColor(Color.GREEN);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        connectionDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String SSID = snapshot.child("SSID").getValue(String.class);
                String Status = snapshot.child("Status").getValue(String.class);
                String Connected = "1";

                if (Status == "0") {

                    tvConnectionStatus.setText("NOT CONNNECTED");
                    tvConnectionStatus.setTypeface(null, BOLD);
                    tvConnectionStatus.setTextColor(Color.RED);

                } else {
                    tvConnectionStatus.setText(SSID);
                    tvConnectionStatus.setTypeface(null, BOLD);
                    tvConnectionStatus.setTextColor(Color.GREEN);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void parkedMode(String btn_Motor_Status) {
        data.setBtn_Lock(btn_Motor_Status);
        databaseReference.child("btn_Motor_Status").setValue(btn_Motor_Status);
    }

    private void ridingMode(String btn_Motor_Status) {
        data.setBtn_Lock(btn_Motor_Status);
        databaseReference.child("btn_Motor_Status").setValue(btn_Motor_Status);
    }

    private void lockBox(String btn_Lock) {
        data.setBtn_Lock(btn_Lock);
        databaseReference.child("btn_Lock").setValue(btn_Lock);
    }

    private void unlockBox(String btn_Lock) {
        data.setBtn_Lock(btn_Lock);
        databaseReference.child("btn_Lock").setValue(btn_Lock);
    }


}