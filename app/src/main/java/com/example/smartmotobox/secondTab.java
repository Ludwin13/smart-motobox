package com.example.smartmotobox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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

import java.lang.ref.WeakReference;

public class secondTab extends Fragment {

    Data data;
    Button lockBtn, motorStatusBtn, gpsBtn, changeNumberBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, connectionDBRef, volumeDBRef, firebaseDB_Connection, firebaseDB_Alarm;
    TextView tvLockStatus, tvAlarmStatus, tvMotorStatus, tvConnectionStatus, tvGPSStatus, tvVolume;
    String getBtn_Alarm, getBtn_Lock, getMotor_Status, getBtn_GPS_Enabler, getVolume_Control, getMobile_Number, getConnection;
    String statusOff = "0";
    String volume_0 = "60";
    String volume_1 = "61";
    String volume_2 = "62";
    String volume_3 = "63";
    String volume_4 = "64";
    String volume_5 = "65";
    String volume_6 = "66";
    String isConnected = "1";
    String statusOn = "1";
    boolean isConnectedto;
    SeekBar seekBar;

    //ALL CODES FROM THIS TAB SHOULD BE PLACED IN MAINACTIVITY.CLASS SO IT UPDATES EVERYTIME WHEN SWITCHING TABS
    private static class MyHandler extends Handler {
    }

    private final Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_tab, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data");
        firebaseDB_Alarm = firebaseDatabase.getReference("Data");
        connectionDBRef = firebaseDatabase.getReference("/Connection");
        volumeDBRef = firebaseDatabase.getReference("Volume");
        data = new Data();
//        testBtn = (Button) view.findViewById(R.id.testBtn);
        lockBtn = (Button) view.findViewById(R.id.lockBtn);
        motorStatusBtn = (Button) view.findViewById(R.id.ridingBtn);
        gpsBtn = (Button) view.findViewById(R.id.gpsBtn);
        firebaseDB_Connection = firebaseDatabase.getReference("Connection");
//        resetWiFiBtn = (Button) view.findViewById(R.id.resetWiFi);

        tvLockStatus = (TextView) view.findViewById(R.id.tvLockStatus_Holder);
        tvAlarmStatus = (TextView) view.findViewById(R.id.tvAlarmStatus_Holder);
        tvMotorStatus = (TextView) view.findViewById(R.id.tvMotorStatus_Holder);
        tvConnectionStatus = (TextView) view.findViewById(R.id.tvConnection_Holder);
        tvGPSStatus = (TextView) view.findViewById(R.id.tvGPSStatus_Holder);
        seekBar = (SeekBar) view.findViewById(R.id.volumeSeekBar);
        tvVolume = (TextView) view.findViewById(R.id.tvVolume_Holder);
        changeNumberBtn = (Button) view.findViewById(R.id.changeNumberBtn);

        isConnected();
        if (isConnectedto) {

            firebaseDB_Alarm.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Data model = snapshot.getValue(Data.class);

                        assert model != null;
                        getBtn_Alarm = model.getBtn_Alarm();

                        if (getBtn_Alarm.equals(statusOn)) {
                            tvAlarmStatus.setText("ON");
                            tvAlarmStatus.setTextColor(Color.GREEN);
                            MyRunnable myRunnable = new MyRunnable();
                            new Thread(myRunnable).start();

                        } else {
                            tvAlarmStatus.setText("OFF");
                            tvAlarmStatus.setTextColor(Color.RED);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            firebaseDB_Connection.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Connection connection = snapshot.getValue(Connection.class);

                        if (connection != null) {
                            getConnection = connection.getConnection();
                        }
                        if (getConnection.equals(isConnected)) {
                            tvConnectionStatus.setText("Connected");
                            tvConnectionStatus.setTextColor(Color.GREEN);

                        } else {
                            tvConnectionStatus.setText("Disconnected");
                            tvConnectionStatus.setTextColor(Color.RED);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        getMotorStatus();
        btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control, getMobile_Number);

        return view;
    }

    private void btnMethods(String getMotor_status, String getBtn_lock, String getGPS_status, String getVolume_Control, String getMobile_Number) {
        String statusOff = "0";
        String statusOn = "1";

        lockBtn.setOnClickListener(view -> {
            isConnected();

            if (isConnectedto) {
                FirebaseDB_Connection();

                if (getBtn_lock.equals(statusOff)) {
                    if (getConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        databaseReference.child("btn_Lock").setValue(statusOn);
                        lockBtn.setText("UNLOCK");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    if (getConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        databaseReference.child("btn_Lock").setValue(statusOff);
                        lockBtn.setText("LOCK");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

//                    data.setBtn_Lock(statusOff);
                }
            } else {
                Toast.makeText(getActivity(), "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
            }
            getMotorStatus();

        });

        motorStatusBtn.setOnClickListener(view -> {
            isConnected();

            if (isConnectedto) {
                FirebaseDB_Connection();

                if (getMotor_status.equals(statusOff)) {
                    if (getConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        databaseReference.child("btn_Motor_Status").setValue(statusOn);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }

//                    data.setBtn_Motor_Status(statusOn);
                } else {
                    if (getConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        databaseReference.child("btn_Motor_Status").setValue(statusOff);
                        getMotorStatus();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

//                    data.setBtn_Motor_Status(statusOff);
                }
                getMotorStatus();
            } else {
                Toast.makeText(getActivity(), "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
            }
            getMotorStatus();
        });


        gpsBtn.setOnClickListener(view -> {
            isConnected();
            if (isConnectedto) {
                FirebaseDB_Connection();
                if (getConnection.equals(isConnected)) {
                    firebaseDB_Connection.child("Connection").setValue("0");
                    GPSConfirmation(getGPS_status);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setCancelable(true);
                    builder.setTitle("Device Connection");
                    builder.setMessage("Device is not Connected to the Internet");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    getMotorStatus();
                }
            } else {
                Toast.makeText(getActivity(), "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
                getMotorStatus();
            }

        });

//        resetWiFiBtn.setOnClickListener(view -> {
//          resetWiFiConfirmation();
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int volume, boolean b) {
                isConnected();
                if (isConnectedto) {
                    FirebaseDB_Connection();

                    if (volume == 0) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("60");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    } else if (volume == 1) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("61");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 2) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("62");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 3) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("63");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 4) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("64");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 5) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("65");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 6) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("66");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    } else if (volume == 7) {
                        if (getConnection.equals(isConnected)) {
                            firebaseDB_Connection.child("Connection").setValue("0");
                            volumeDBRef.child("Volume_Control").setValue("67");
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setCancelable(true);
                            builder.setTitle("Device Connection");
                            builder.setMessage("Device is not Connected to the Internet");

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }


                    }
                } else {
                    Toast.makeText(getActivity(), "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
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

        changeNumberBtn.setOnClickListener(view -> {
            isConnected();
            if (isConnectedto) {
                FirebaseDB_Connection();

                if (getConnection.equals(isConnected)) {
                    firebaseDB_Connection.child("Connection").setValue("0");

                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);

                    final EditText etNumber = new EditText(getContext());
                    etNumber.setHint("9276625575");
                    layoutName.addView(etNumber);

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setView(layoutName);
                    builder.setTitle("Input Number (Start with +63 '9'");
                    builder.setMessage("Saved Number: " + getMobile_Number);
                    builder.setCancelable(true);
                    builder.setPositiveButton("Change Number",
                            (dialog, which) -> {
                                String newNumber = etNumber.getText().toString(); // variable to collect user input
                                databaseReference.child("numChange_confirmation").setValue("1");
                                databaseReference.child("mobile_number").setValue(newNumber);
                                Toast.makeText(getContext(), "Enrollment In Process, check Fingerprint Scanner.", Toast.LENGTH_LONG).show();
                                postChangeNumberConfirmation();


                            });

                    builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setCancelable(true);
                    builder.setTitle("Device Connection");
                    builder.setMessage("Device is not Connected to the Internet");

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            } else {
                Toast.makeText(getActivity(), "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void postChangeNumberConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        builder.setTitle("Mobile Number Change");
        builder.setMessage("Currently changing the mobile number saved on the device, please wait.");

        AlertDialog dialog = builder.create();
        dialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String finished = "2";
                    Data model = snapshot.getValue(Data.class);
                    String finished_numChange = null;
                    if (model != null) {
                        finished_numChange = model.getNumChange_confirmation();
                    }
                    if (finished_numChange != null && finished_numChange.equals(finished)) {
                        databaseReference.child("numChange_confirmation").setValue("0");
                        Toast.makeText(getContext(), "Number Succesfully Changed!", Toast.LENGTH_LONG).show();
                        dialog.hide();
                        getMotorStatus();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void GPSConfirmation(String btn_GPS_Status) {
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

    private void getMotorStatus() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data model = snapshot.getValue(Data.class);

                    assert model != null;
                    getBtn_Alarm = model.getBtn_Alarm();
                    getMotor_Status = model.getBtn_Motor_Status();
                    getBtn_Lock = model.getBtn_Lock();
                    getBtn_GPS_Enabler = model.getBtn_GPS_Enabler();
                    getMobile_Number = model.getMobile_number();

                    firebaseDB_Connection.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Connection connection = snapshot.getValue(Connection.class);

                                if (connection != null) {
                                    getConnection = connection.getConnection();
                                }

                                volumeDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Data model = snapshot.getValue(Data.class);

                                            if (model != null) {
                                                getVolume_Control = model.getVolume_Control();
                                            }
                                            setMotorStatus(getBtn_Alarm, getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control, getMobile_Number);
                                            btnMethods(getMotor_Status, getBtn_Lock, getBtn_GPS_Enabler, getVolume_Control, getMobile_Number);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMotorStatus(String getBtn_Alarm, String getMotor_Status, String getBtn_Lock, String getGPS_Status, String getVolume_Control, String getMobile_Number) {

        if (this.getMotor_Status.equals(statusOff)) {
            tvMotorStatus.setText("PARKED");
            tvMotorStatus.setTextColor(Color.GREEN);
            motorStatusBtn.setText("Ride Mode");
        } else {
            tvMotorStatus.setText("RIDING");
            tvMotorStatus.setTextColor(Color.RED);
            motorStatusBtn.setText("Park Mode");
        }

        if (this.getBtn_Alarm.equals(statusOff)) {
            tvAlarmStatus.setText("OFF");
            tvAlarmStatus.setTextColor(Color.RED);
        } else {
            tvAlarmStatus.setText("ON");
            tvAlarmStatus.setTextColor(Color.GREEN);
        }

        if (this.getBtn_Lock.equals(statusOff)) {
            tvLockStatus.setText("OFF");
            tvLockStatus.setTextColor(Color.RED);
        } else {
            tvLockStatus.setText("ON");
            tvLockStatus.setTextColor(Color.GREEN);
        }

        if (this.getBtn_GPS_Enabler.equals(statusOff)) {
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

    private void FirebaseDB_Connection() {
        firebaseDB_Connection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Connection connection = snapshot.getValue(Connection.class);

                    getConnection = connection.getConnection();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        isConnectedto = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }

    public class MyRunnable implements Runnable {


        @Override
        public void run() {
                    mHandler.postDelayed(() -> {
                        firebaseDB_Alarm.child("btn_Alarm").setValue("0");
                        tvAlarmStatus.setText("OFF");
                        tvAlarmStatus.setTextColor(Color.RED);

                    }, 5000);

                }


            }

        }


//    public static class MyRunnable implements Runnable {
//        private final WeakReference<Activity> mActivity;
//
//        public MyRunnable(Activity activity) {
//            mActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void run() {
//            Activity activity = mActivity.get();
//            if (activity != null) {
//
//            }
//        }
//    }
//
//    private MyRunnable mRunnable = new MyRunnable(this);
//
//    public void setAlarmStatusOff() {
//        firebaseDB_Alarm.child("btn_Alarm").setValue("0");
//        tvAlarmStatus.setText("OFF");
//        tvAlarmStatus.setTextColor(Color.RED);
//
//        // Execute the Runnable in 2 seconds
//        mHandler.postDelayed(mRunnable, 2000);
//    }




