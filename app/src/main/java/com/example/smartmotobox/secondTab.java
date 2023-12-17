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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class secondTab extends Fragment {

    Data data;
    Button lockBtn, motorStatusBtn, gpsBtn, resetWiFiBtn, changeNumberBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, connectionDBRef, volumeDBRef;
    TextView tvLockStatus, tvAlarmStatus, tvMotorStatus, tvConnectionStatus, tvGPSStatus, tvVolume;
    String getBtn_Alarm, getBtn_Lock, getMotor_Status, getBtn_GPS_Enabler, getVolume_Control;
    String statusOff = "0";
    String volume_0 = "60";
    String volume_1 = "61";
    String volume_2 = "62";
    String volume_3 = "63";
    String volume_4 = "64";
    String volume_5 = "65";
    String volume_6 = "66";
    String volume_7 = "70";
    SeekBar seekBar;
    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAINACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");
        connectionDBRef = firebaseDatabase.getReference("/Connection");
        volumeDBRef = firebaseDatabase.getReference("Volume");
        data = new Data();
//        testBtn = (Button) view.findViewById(R.id.testBtn);
        lockBtn = (Button) view.findViewById(R.id.lockBtn);
        motorStatusBtn = (Button) view.findViewById(R.id.ridingBtn);
        gpsBtn = (Button) view.findViewById(R.id.gpsBtn);
//        resetWiFiBtn = (Button) view.findViewById(R.id.resetWiFi);

        tvLockStatus = (TextView) view.findViewById(R.id.tvLockStatus_Holder);
        tvAlarmStatus = (TextView) view.findViewById(R.id.tvAlarmStatus_Holder);
        tvMotorStatus = (TextView) view.findViewById(R.id.tvMotorStatus_Holder);
        tvConnectionStatus = (TextView) view.findViewById(R.id.tvConnection_Holder);
        tvGPSStatus = (TextView) view.findViewById(R.id.tvGPSStatus_Holder);
        seekBar = (SeekBar) view.findViewById(R.id.volumeSeekBar);
        tvVolume = (TextView) view.findViewById(R.id.tvVolume_Holder);
        changeNumberBtn = (Button) view.findViewById(R.id.changeNumberBtn);


        getMotorStatus();
        btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control);


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

    private void btnMethods(String getMotor_status, String getBtn_lock, String getGPS_status, String getVolume_Control) {
        String statusOff = "0";
        String statusOn = "1";

        lockBtn.setOnClickListener(view -> {

            if (getBtn_lock.equals(statusOff)) {
//                lockConfirmation(getBtn_lock);
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

//        resetWiFiBtn.setOnClickListener(view -> {
//          resetWiFiConfirmation();
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int volume, boolean b) {

                if (volume == 0)
                {
                    volumeDBRef.child("Volume_Control").setValue("60");
                } else if (volume == 1)
                {
                    volumeDBRef.child("Volume_Control").setValue("61");

                } else if (volume == 2)
                {
                    volumeDBRef.child("Volume_Control").setValue("62");

                } else if (volume == 3)
                {
                    volumeDBRef.child("Volume_Control").setValue("63");

                } else if (volume == 4)
                {
                    volumeDBRef.child("Volume_Control").setValue("64");

                } else if (volume == 5)
                {
                    volumeDBRef.child("Volume_Control").setValue("65");

                } else if (volume == 6)
                {
                    volumeDBRef.child("Volume_Control").setValue("66");

                } else if (volume == 7)
                {
                    volumeDBRef.child("Volume_Control").setValue("67");

                }
                getMotorStatus();



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        changeNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String postTitle = "Change Number?";
                final String postMessage = "Enrollment in Process, Check your fingerprint scanner";

                LinearLayout layoutName = new LinearLayout(getContext());
                layoutName.setOrientation(LinearLayout.VERTICAL);

                final EditText etNumber = new EditText(getContext());
                etNumber.setHint("9276625575");
                layoutName.addView(etNumber);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(layoutName);
                builder.setTitle("Input Number (Start with +63 '9' ");
                builder.setCancelable(true);
                builder.setPositiveButton("Change Number",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newNumber = etNumber.getText().toString(); // variable to collect user input
                                databaseReference.child("numChange_confirmation").setValue("1");
                                databaseReference.child("mobile_number").setValue(newNumber);
                                Toast.makeText(getContext(), "Number Succesfully Changed!", Toast.LENGTH_LONG).show();

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
        });
    }

//    private void resetWiFiConfirmation() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setCancelable(true);
//        builder.setTitle("Disconnect Wifi");
//        builder.setMessage("Disconnect WiFi and Open Config Portal?");
//        builder.setPositiveButton("Disconnect",
//                (dialog, which) -> {
//
//                    databaseReference.child("reset_device_connection").setValue("1");
//
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//
//    }



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

    private void getVolumeValue(){

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
//                    setMotorStatus(getBtn_Alarm, getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control);
//                    btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control);

                    volumeDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Data model = snapshot.getValue(Data.class);

                                getVolume_Control = model.getVolume_Control();
                                setMotorStatus(getBtn_Alarm, getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control);
                                btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMotorStatus(String getBtn_Alarm, String getMotor_Status, String getBtn_Lock, String getGPS_Status, String getVolume_Control) {

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

        if (this.getVolume_Control.equals(volume_0)) {
            tvVolume.setText("0");
            tvVolume.setTextColor(Color.RED);

        } else if (this.getVolume_Control.equals(volume_1)) {
            tvVolume.setText("10");
            tvVolume.setTextColor(Color.RED);

        } else if (this.getVolume_Control.equals(volume_2)) {
            tvVolume.setText("20");
            tvVolume.setTextColor(Color.RED);

        } else if (this.getVolume_Control.equals(volume_3)) {
            tvVolume.setText("30");
            tvVolume.setTextColor(Color.YELLOW);

        } else if (this.getVolume_Control.equals(volume_4)) {
            tvVolume.setText("40");
            tvVolume.setTextColor(Color.YELLOW);

        } else if (this.getVolume_Control.equals(volume_5)) {
            tvVolume.setText("50");
            tvVolume.setTextColor(Color.GREEN);

        } else if (this.getVolume_Control.equals(volume_6)) {
            tvVolume.setText("60");
            tvVolume.setTextColor(Color.GREEN);

        } else {
            tvVolume.setText("70");
            tvVolume.setTextColor(Color.GREEN);

        }



    }




}