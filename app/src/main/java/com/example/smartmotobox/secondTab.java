package com.example.smartmotobox;

import static android.graphics.Typeface.BOLD;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
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
    Button lockBtn, motorStatusBtn, biometricBtn, gpsBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, connectionDBRef, locationDBRef;
    TextView tvLockStatus, tvAlarmStatus, tvMotorStatus, tvConnectionStatus, tvGPSStatus;

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

//        testBtn = (Button) view.findViewById(R.id.testBtn);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        lockBtn = view.findViewById(R.id.lockBtn);
        motorStatusBtn = view.findViewById(R.id.ridingBtn);
        biometricBtn = view.findViewById(R.id.enrollBtn);
        gpsBtn = view.findViewById(R.id.gpsBtn);
        tvLockStatus = view.findViewById(R.id.tvLockStatus_Holder);
        tvAlarmStatus = view.findViewById(R.id.tvAlarmStatus_Holder);
        tvMotorStatus = view.findViewById(R.id.tvMotorStatus_Holder);
        tvConnectionStatus = view.findViewById(R.id.tvConnection_Holder);
        tvGPSStatus = view.findViewById(R.id.tvGPSStatus_Holder);

        checkStatus();

        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lockStatus = data.getBtn_Lock();

                if (lockStatus == "0") {
                    String Status = "1";
                    data.setBtn_Lock(Status);
                    lockStatus(Status);
                } else {
                    String Status = "0";
                    data.setBtn_Lock(Status);
                    lockStatus(Status);
                }

            }
        });

        biometricBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), enrollFingerprint.class);
                startActivity(intent);
            }
        });

        motorStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String motorStatus = data.getBtn_Motor_Status();

                if (motorStatus == "0") {
                    String Status = "1";
                    data.setBtn_Motor_Status(Status);
                    motorStatus_Mode(Status);
                } else {
                    String Status = "0";
                    data.setBtn_Motor_Status(Status);
                    motorStatus_Mode(Status);
                }
            }
        });


        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String GPS_Status = data.getBtn_GPS_Status();

                if (GPS_Status == "0") {
                    String btn_GPS_Status = "1";
                    data.setBtn_GPS_Status(btn_GPS_Status);
                    enableGPSConfirmation(btn_GPS_Status);
                } else {
                    String btn_GPS_Status = "0";
                    data.setBtn_GPS_Status(btn_GPS_Status);
                    disableGPSConfirmation(btn_GPS_Status);
                }
            }
        });

        return view;
    }

    private void checkStatus() {

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String lockStatus = snapshot.child("btn_Lock").getValue(String.class);
//                if (lockStatus == "0") {
//
//                    tvLockStatus.setText("LOCKED");
//                    tvLockStatus.setTextColor(Color.GREEN);
//                } else {
//                    tvLockStatus.setText("UNLOCKED");
//                    tvLockStatus.setTypeface(null, Typeface.BOLD);
//                    tvLockStatus.setTextColor(Color.RED);
//                }
//
//                String alarmStatus = snapshot.child("/btn_Alarm").getValue(String.class);
//                if (alarmStatus == "0") {
//
//                    tvAlarmStatus.setText("OFF");
//                    tvAlarmStatus.setTextColor(Color.RED);
//                } else {
//                    tvAlarmStatus.setText("ON");
//                    tvAlarmStatus.setTypeface(null, Typeface.BOLD);
//                    tvAlarmStatus.setTextColor(Color.GREEN);
//                }
//
//                String motorStatus = snapshot.child("btn_Motor_Status").getValue(String.class);
//                if (motorStatus == "0") {
//
//                    tvMotorStatus.setText("RIDING");
//                    tvMotorStatus.setTextColor(Color.RED);
//                } else {
//                        tvMotorStatus.setText("PARKED");
//                        tvMotorStatus.setTypeface(null, Typeface.BOLD);
//                        tvMotorStatus.setTextColor(Color.GREEN);
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        DatabaseReference getAlarmStat = databaseReference.child("btn_Alarm");
        DatabaseReference getEnrollStat = databaseReference.child("btn_Enroll");
        DatabaseReference getLockStat = databaseReference.child("btn_Lock");
        DatabaseReference getMotorStat = databaseReference.child("btn_Motor_Status");
        DatabaseReference getGPSStat = databaseReference.child("btn_GPS_Enabler");

        getGPSStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String gps = snapshot.getValue(String.class);
                String gpsOff = "0";

                if(gps.equals(gpsOff)) {
                    tvGPSStatus.setText("OFF");
                    tvGPSStatus.setTextColor(Color.RED);
                    gpsBtn.setText("Enable GPS");
                } else {
                    tvGPSStatus.setText("ON");
                    tvGPSStatus.setTypeface(null, Typeface.BOLD);
                    tvGPSStatus.setTextColor(Color.GREEN);
                    gpsBtn.setText("Disable GPS");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    lockBtn.setText("Locked");
                } else {
                    tvLockStatus.setText("UNLOCKED");
                    tvLockStatus.setTypeface(null, Typeface.BOLD);
                    tvLockStatus.setTextColor(Color.RED);
                    lockBtn.setText("Unlocked");
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
                    motorStatusBtn.setText("Riding Mode");
                } else {
                    tvMotorStatus.setText("PARKED");
                    tvMotorStatus.setTypeface(null, Typeface.BOLD);
                    tvMotorStatus.setTextColor(Color.GREEN);
                    motorStatusBtn.setText("Parked Mode");
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


    private void motorStatus_Mode(String btn_Motor_Status) {
        data.setBtn_Lock(btn_Motor_Status);
        databaseReference.child("btn_Motor_Status").setValue(btn_Motor_Status);
    }

    private void lockStatus(String btn_Lock) {
        data.setBtn_Lock(btn_Lock);
        databaseReference.child("btn_Lock").setValue(btn_Lock);
    }


    private void enableGPSConfirmation(String btn_GPS_Status) {
        final String title = "Enable GPS";
        final String message = "Do you want to enable GPS?";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.setBtn_GPS_Status(btn_GPS_Status);
                        databaseReference.child("btn_GPS_Enabler").setValue(btn_GPS_Status);
                    }
                });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void disableGPSConfirmation(String btn_GPS_Status) {
        final String title = "Disable GPS";
        final String message = "Do you want to disable GPS?";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.setBtn_GPS_Status(btn_GPS_Status);
                        databaseReference.child("btn_GPS_Enabler").setValue(btn_GPS_Status);
                    }
                });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }




}