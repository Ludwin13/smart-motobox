package com.example.smartmotobox;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class secondTab extends Fragment {

    Data data;
    Button lockBtn, motorStatusBtn, gpsBtn, resetWiFiBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, connectionDBRef;
    TextView tvLockStatus, tvAlarmStatus, tvMotorStatus, tvConnectionStatus, tvGPSStatus;
    String getBtn_Alarm, getBtn_Lock, getMotor_Status, getBtn_GPS_Enabler;
    String statusOff = "0";
    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAINACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");
        connectionDBRef = firebaseDatabase.getReference("/Connection");
        data = new Data();
//        testBtn = (Button) view.findViewById(R.id.testBtn);
        lockBtn = (Button) view.findViewById(R.id.lockBtn);
        motorStatusBtn = (Button) view.findViewById(R.id.ridingBtn);
        gpsBtn = (Button) view.findViewById(R.id.gpsBtn);
        resetWiFiBtn = (Button) view.findViewById(R.id.resetWiFi);

        tvLockStatus = (TextView) view.findViewById(R.id.tvLockStatus_Holder);
        tvAlarmStatus = (TextView) view.findViewById(R.id.tvAlarmStatus_Holder);
        tvMotorStatus = (TextView) view.findViewById(R.id.tvMotorStatus_Holder);
        tvConnectionStatus = (TextView) view.findViewById(R.id.tvConnection_Holder);
        tvGPSStatus = (TextView) view.findViewById(R.id.tvGPSStatus_Holder);

        getMotorStatus();
        btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler);

//        lockBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String lockStatus = data.getBtn_Lock();
//
//                if (lockStatus == "0") {
//                    String Status = "1";
//                    data.setBtn_Lock(Status);
//                    lockStatus(Status);
//                } else {
//                    String Status = "0";
//                    data.setBtn_Lock(Status);
//                    lockStatus(Status);
//                }
//
//            }
//        });
//
//        motorStatusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String motorStatus = data.getBtn_Motor_Status();
//
//                if (motorStatus == "0") {
//                    String Status = "1";
//                    data.setBtn_Motor_Status(Status);
//                    motorStatus_Mode(Status);
//                } else {
//                    String Status = "0";
//                    data.setBtn_Motor_Status(Status);
//                    motorStatus_Mode(Status);
//                }
//            }
//        });
//
//
//        gpsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String GPS_Status = data.getBtn_GPS_Status();
//                String gpsOff = "0";
//
//                if (GPS_Status.equals(gpsOff)) {
//                    String btn_GPS_Status = "1";
//                    GPSConfirmation(btn_GPS_Status);
//                } else {
//                    String btn_GPS_Status = "0";
//                    GPSConfirmation(btn_GPS_Status);
//                }
//            }
//        });




        return view;
    }

    private void btnMethods(String getMotor_status, String getBtn_lock, String getGPS_status) {
        String statusOff = "0";
        String statusOn = "1";

        lockBtn.setOnClickListener(view -> {

            if (getBtn_lock.equals(statusOff)) {
                lockConfirmation(getBtn_lock);
                databaseReference.child("btn_Lock").setValue(statusOn);
                getMotorStatus();
//                    data.setBtn_Lock(statusOn);
            } else {
                databaseReference.child("btn_Lock").setValue(statusOff);
                getMotorStatus();
//                    data.setBtn_Lock(statusOff);
            }
        });

        motorStatusBtn.setOnClickListener(view -> {

            if (getMotor_status.equals(statusOff)) {
                databaseReference.child("btn_Motor_Status").setValue(statusOn);
                getMotorStatus();
//                    data.setBtn_Motor_Status(statusOn);
            } else {
                databaseReference.child("btn_Motor_Status").setValue(statusOff);
                getMotorStatus();
//                    data.setBtn_Motor_Status(statusOff);
            }
        });


        gpsBtn.setOnClickListener(view -> {

            GPSConfirmation(getGPS_status);
        });

        resetWiFiBtn.setOnClickListener(view -> {
          resetWiFiConfirmation();
        });
    }

    private void resetWiFiConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(true);
        builder.setTitle("Disconnect Wifi");
        builder.setMessage("Disconnect WiFi and Open Config Portal?");
        builder.setPositiveButton("Disconnect",
                (dialog, which) -> {

                    databaseReference.child("reset_device_connection").setValue("1");

                });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void lockConfirmation(String getBtn_lock) {
    }


    private void GPSConfirmation(String btn_GPS_Status) {
//        data.setBtn_GPS_Status(btn_GPS_Status);
        final String gpsOff = "0";
        final String gpsOn = "1";
        final String title1 = "Enable GPS";
        final String title2 = "Disable GPS";
        final String message = "Warning, enabling the GPS will switch the Serial Communication of the device to the GPS Module. Disabling the usage of the fingerprint scanner, change from Park" +
                " Mode to Ride Mode and unlocking the device via Mobile Application until the gps achieves trilateration. Do you want to enable GPS?";
        final String message2 = "Do you want to disable GPS?";

        if (btn_GPS_Status.equals(gpsOff)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setCancelable(true);
            builder.setTitle(title1);
            builder.setMessage(message);
            builder.setPositiveButton("Enable",
                    (dialog, which) -> {
                        databaseReference.child("btn_GPS_Enabler").setValue(gpsOn);
                        getMotorStatus();
                    });

            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setCancelable(true);
            builder.setTitle(title2);
            builder.setMessage(message2);
            builder.setPositiveButton("Disable",
                    (dialog, which) -> {
                        databaseReference.child("btn_GPS_Enabler").setValue(gpsOff);
                        getMotorStatus();
                    });

            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void getMotorStatus(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Data model = snapshot.getValue(Data.class);

                    getBtn_Alarm = model.getBtn_Alarm();
                    getMotor_Status = model.getBtn_Motor_Status();
                    getBtn_Lock = model.getBtn_Lock();
                    getBtn_GPS_Enabler = model.getBtn_GPS_Enabler();

                    setMotorStatus(getBtn_Alarm, getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler);
                    btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMotorStatus(String getBtn_Alarm, String getMotor_Status, String getBtn_Lock, String getGPS_Status) {

        if(this.getMotor_Status.equals(statusOff)) {
            tvMotorStatus.setText("PARKED");
            tvMotorStatus.setTextColor(Color.GREEN);
            motorStatusBtn.setText("Ride Mode");
        } else {
            tvMotorStatus.setText("RIDING");
            tvMotorStatus.setTextColor(Color.RED);
            motorStatusBtn.setText("Park Mode");
        }

        if(this.getBtn_Alarm.equals(statusOff)) {
            tvAlarmStatus.setText("OFF");
            tvAlarmStatus.setTextColor(Color.RED);
        } else {
            tvAlarmStatus.setText("ON");
            tvAlarmStatus.setTextColor(Color.GREEN);
        }

        if(this.getBtn_Lock.equals(statusOff)) {
            tvLockStatus.setText("OFF");
            tvLockStatus.setTextColor(Color.RED);
        } else {
            tvLockStatus.setText("ON");
            tvLockStatus.setTextColor(Color.GREEN);
        }

        if(this.getBtn_GPS_Enabler.equals(statusOff)) {
            tvGPSStatus.setText("OFF");
            tvGPSStatus.setTextColor(Color.RED);
            gpsBtn.setText("Enable GPS");
        } else {
            tvGPSStatus.setText("ON");
            tvGPSStatus.setTextColor(Color.GREEN);
            gpsBtn.setText("Disable GPS");
        }

    }




}