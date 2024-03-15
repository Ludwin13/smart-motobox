package com.example.smartmotobox;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class fourthTab extends Fragment {

    TextView finger1Status, finger2Status, finger3Status, finger4Status, finger5Status, finger6Status, finger7Status, finger8Status, finger9Status, finger10Status;
    DatabaseReference firebaseDB_getFingerPrintData, firebaseDB_Data, getFingerprintData2, firebaseDB_Connection;
    String getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status,
    getFinger1_Time, getFinger2_Time, getFinger3_Time, getFinger4_Time, getFinger5_Time, getFinger6_Time, getFinger7_Time, getFinger8_Time, getFinger9_Time, getFinger10_Time,
    getFinger1_Date, getFinger2_Date, getFinger3_Date, getFinger4_Date, getFinger5_Date, getFinger6_Date, getFinger7_Date, getFinger8_Date, getFinger9_Date, getFinger10_Date,
    getFinger10_Description, getFinger1_Description, getFinger2_Description, getFinger3_Description, getFinger4_Description, getFinger5_Description, getFinger6_Description, getFinger7_Description, getFinger8_Description, getFinger9_Description;
    ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    EditText txt; // user input bar

    boolean isConnectedto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth_tab, container, false);

        finger1Status = (TextView) view.findViewById(R.id.finger1Status);
        finger2Status = (TextView) view.findViewById(R.id.finger2Status);
        finger3Status = (TextView) view.findViewById(R.id.finger3Status);
        finger4Status = (TextView) view.findViewById(R.id.finger4Status);
        finger5Status = (TextView) view.findViewById(R.id.finger5Status);
        finger6Status = (TextView) view.findViewById(R.id.finger6Status);
        finger7Status = (TextView) view.findViewById(R.id.finger7Status);
        finger8Status = (TextView) view.findViewById(R.id.finger8Status);
        finger9Status = (TextView) view.findViewById(R.id.finger9Status);
        finger10Status = (TextView) view.findViewById(R.id.finger10Status);

        firebaseDB_getFingerPrintData = FirebaseDatabase.getInstance().getReference("Fingerprint");
        firebaseDB_Data = FirebaseDatabase.getInstance().getReference("Data");
        firebaseDB_Connection = FirebaseDatabase.getInstance().getReference("Connection");

         getFingerprintData();



//        deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status,
//                getFinger1_Time, getFinger2_Time, getFinger3_Time, getFinger4_Time, getFinger5_Time, getFinger6_Time, getFinger7_Time, getFinger8_Time, getFinger9_Time, getFinger10_Time,
//                getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status,
//                getFinger1_Description, getFinger2_Description, getFinger3_Description, getFinger4_Description, getFinger5_Description, getFinger6_Description, getFinger7_Description, getFinger8_Description, getFinger9_Description, getFinger10_Description);

        return view;
    }


    private void deleteFingerID(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status,
                                String getFinger1_Description, String getFinger2_Description, String getFinger3_Description, String getFinger4_Description, String getFinger5_Description, String getFinger6_Description, String getFinger7_Description, String getFinger8_Description, String getFinger9_Description, String getFinger10_Description,
                                String getFinger1_Date, String getFinger2_Date, String getFinger3_Date, String getFinger4_Date, String getFinger5_Date, String getFinger6_Date, String getFinger7_Date, String getFinger8_Date, String getFinger9_Date, String getFinger10_Date,
                                String getFinger1_Time, String getFinger2_Time, String getFinger3_Time, String getFinger4_Time, String getFinger5_Time, String getFinger6_Time, String getFinger7_Time, String getFinger8_Time, String getFinger9_Time, String getFinger10_Time
                                ) {

        final String enroll_Title = "Enroll";
        final String enroll_Message = "Enroll Fingerprint ID #";
        final String delete_Message = "Delete Fingerprint ID #";
        final String delete_Title = "Delete";
        final String deleteValue = "0";
        final String enrollValue = "1";
        String Enrolled = "1";
        String notEnrolled = "0";



            finger1Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger1_Status.equals(Enrolled)) {
                            enrolledFingerStatus("2", getFinger1_Description, getFinger1_Date, getFinger1_Time, "11", "Finger1_Status", "Finger1Description", "Finger1_Date", "Finger1_Time", "1");
                        }
                        if(getFinger1_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "1?","Finger1_Status", "Finger1_Description","Finger1_Date", "Finger1_Time",  enrollValue, "11","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            finger2Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger2_Status.equals(Enrolled)) {
                            enrolledFingerStatus("2", getFinger2_Description, getFinger2_Date, getFinger2_Time, "13", "Finger2_Status", "Finger2_Description", "Finger2_Date", "Finger2_Time", "1");
                        }
                        if(getFinger2_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "2?","Finger2_Status", "Finger2_Description","Finger2_Date", "Finger2_Time",  enrollValue, "13","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            finger3Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger3_Status.equals(Enrolled)) {
                            enrolledFingerStatus("3", getFinger3_Description, getFinger3_Date, getFinger3_Time, "15", "Finger3_Status", "Finger3_Description", "Finger3_Date", "Finger3_Time", "1");

                        }
                        if(getFinger3_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "3?","Finger3_Status", "Finger3_Description","Finger3_Date", "Finger3_Time",  enrollValue, "15","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            finger4Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger4_Status.equals(Enrolled)) {
                            enrolledFingerStatus("4", getFinger4_Description, getFinger4_Date, getFinger4_Time, "17", "Finger4_Status", "Finger4_Description", "Finger4_Date", "Finger4_Time", "1");
                        }
                        if(getFinger4_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "4?","Finger4_Status", "Finger4_Description","Finger4_Date", "Finger4_Time",  enrollValue, "17","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            finger5Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger5_Status.equals(Enrolled)) {
                            enrolledFingerStatus("5", getFinger5_Description, getFinger5_Date, getFinger5_Time, "19", "Finger5_Status", "Finger5_Description", "Finger5_Date", "Finger5Time", "1");
                        }
                        if(getFinger5_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "5?","Finger5_Status", "Finger5_Description","Finger5_Date", "Finger5_Time",  enrollValue, "19","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            finger6Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger6_Status.equals(Enrolled)) {
                            enrolledFingerStatus("6", getFinger6_Description, getFinger6_Date, getFinger6_Time, "21", "Finger6_Status", "Finger6_Description", "Finger3_Date", "Finger6_Time", "1");
                        }
                        if(getFinger6_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "6?","Finger6_Status", "Finger6_Description", "Finger6_Date", "Finger6_Time", enrollValue, "21","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            finger7Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger7_Status.equals(Enrolled)) {
                            enrolledFingerStatus("7", getFinger7_Description, getFinger7_Date, getFinger7_Time, "23", "Finger7_Status", "Finger7_Description", "Finger7_Date", "Finger7_Time", "1");
                        }
                        if(getFinger7_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "7?","Finger7_Status","Finger7_Description", "Finger7_Date", "Finger7_Time",  enrollValue, "23","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            finger8Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger8_Status.equals(Enrolled)) {
                            enrolledFingerStatus("8", getFinger8_Description, getFinger8_Date, getFinger8_Time, "25", "Finger8_Status", "Finger8_Description", "Finger8_Date", "Finger8_Time", "1");
                        }
                        if(getFinger8_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "8?","Finger8_Status", "Finger8_Description","Finger8_Date", "Finger8_Time",  enrollValue, "25","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            finger9Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger9_Status.equals(Enrolled)) {
                            enrolledFingerStatus("9", getFinger9_Description, getFinger9_Date, getFinger9_Time, "27", "Finger9_Status", "Finger9_Description", "Finger9_Date", "Finger9_Time", "1" );
                        }
                        if(getFinger9_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "9?","Finger9_Status", "Finger9_Description","Finger9_Date", "Finger9_Time",  enrollValue, "27","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            finger10Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isConnected();
                    if (isConnectedto) {
                        if (getFinger10_Status.equals(Enrolled)) {
                            enrolledFingerStatus("10", getFinger10_Description, getFinger10_Date, getFinger10_Time, "29", "Finger10_Status", "Finger10_Description", "Finger10_Date", "Finger10_Time", "1");
                        }
                        if(getFinger10_Status.equals(notEnrolled)) {
                            getConnectionStatusEnroll(enroll_Title, enroll_Message + "10?","Finger10_Status", "Finger10_Description","Finger10_Date", "Finger10_Time",  enrollValue, "29","btn_Enroll" ,"0");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Phone not Connected to the Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });



    }

    private void enrolledFingerStatus(String fingerID, String fingerDesc, String fingerDate, String fingerTime, String ID_value, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String process_value){

        String title = "Finger " + fingerID + " Status:";
        String fingerDesc_Content = "Description: " + fingerDesc;
        String fingerDate_Enrolled = "Date Enrolled: " + fingerDate;
        String fingerTime_Enrolled = "Time Enrolled: " + fingerTime;

        final String delete_Message = "Delete Fingerprint ID #";
        final String delete_Title = "Delete";
        final String deleteValue = "0";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(fingerDesc_Content + " \n" + fingerDate_Enrolled + "\n" + fingerTime_Enrolled);
        builder.setPositiveButton("Delete?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getConnectionStatusDelete(delete_Title, delete_Message + fingerID, child, fingerDesc_child, fingerDate_child, fingerTime_child,  deleteValue, ID_value, "btn_Delete", process_value);
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

    private void confirmDeleteAlert(String title, String message, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String ID_Value, String process_child, String process_value) {
        final String nullInput = "";
        final String postTitle = "Status";
        final String postMessage = "Deletion in Process, Please wait for audio cue from device";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseDB_Data.child("finger_address").setValue(ID_Value);
                        firebaseDB_Data.child("btn_Delete").setValue("1");
                        firebaseDB_Data.child("btn_Control").setValue("1");
                        postConfirmationAlert(child, fingerDesc_child, fingerDate_child, fingerTime_child, value, process_child, process_value, postTitle, postMessage, nullInput);
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

    private void confirmEnrollAlert(String title, String message, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String ID_Value, String process_child, String process_value) {
        final String postTitle = "Status";
        final String postMessage = "Enrollment in Process, Check your fingerprint scanner";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputFingerDesc(title, message, child, fingerDesc_child, fingerDate_child, fingerTime_child, value, ID_Value, process_child, process_value);
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

    private void inputFingerDesc(String title, String message, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String ID_Value, String process_child, String process_value) {

        final String postTitle = "Status";
        final String postMessage = "Enrollment in Process, Check your fingerprint scanner";

        LinearLayout layoutName = new LinearLayout(getContext());
        layoutName.setOrientation(LinearLayout.VERTICAL);

        final EditText etFingerDesc = new EditText(getContext());
        etFingerDesc.setHint("Input Fingerprint Desc. e.g. Middle Finger");
        layoutName.addView(etFingerDesc);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(layoutName);
        builder.setTitle("Finger Description");
        builder.setCancelable(true);
        builder.setPositiveButton("Enroll Finger Data",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fingerDescription = etFingerDesc.getText().toString(); // variable to collect user input
                        firebaseDB_Data.child("finger_address").setValue(ID_Value);
                        firebaseDB_Data.child("btn_Enroll").setValue("1");
                        firebaseDB_Data.child("btn_Control").setValue("1");
                        Toast.makeText(getContext(), "Enrollment In Process, check Fingerprint Scanner.", Toast.LENGTH_LONG).show();
                        postConfirmationAlert(child, fingerDesc_child, fingerDate_child, fingerTime_child, value, process_child, process_value, postTitle, postMessage, fingerDescription);

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

    private void postConfirmationAlert(String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String process_child, String process_value, String postTitle, String postMessage, String fingerDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle(postTitle);
        builder.setMessage(postMessage);

        AlertDialog dialog = builder.create();
        dialog.show();

        firebaseDB_Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data model = snapshot.getValue(Data.class);
                    String finished = "2";
                    String finished_Enroll = model.getBtn_Enroll();
                    String finished_Delete = model.getBtn_Delete();
                    if (finished_Enroll.equals(finished)) {
                        getFingerprintData();
                        String myDate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                        String myTime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(fingerDesc_child).setValue(fingerDescription);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_getFingerPrintData.child(fingerDate_child).setValue(myDate);
                        firebaseDB_getFingerPrintData.child(fingerTime_child).setValue(myTime);

                        firebaseDB_Data.child("btn_Enroll").setValue("0");
                        firebaseDB_Data.child("finger_address").setValue("00");
                        dialog.hide();
                    }

                    if (finished_Delete.equals(finished)) {
                        getFingerprintData();
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_getFingerPrintData.child(fingerDesc_child).setValue("");
                        firebaseDB_getFingerPrintData.child(fingerDate_child).setValue("");
                        firebaseDB_getFingerPrintData.child(fingerTime_child).setValue("");
                        firebaseDB_Data.child("btn_Delete").setValue("0");
                        firebaseDB_Data.child("finger_address").setValue("00");
                        dialog.hide();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFingerprintData() {

        firebaseDB_getFingerPrintData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    fingerprintModel model = snapshot.getValue(fingerprintModel.class);

                    String NotEnrolled = "0";

                    assert model != null;
                    //Finger1_Status - Finger10_Status in RTDB.


                    getFinger1_Status = model.getFinger1_Status();
                    getFinger2_Status = model.getFinger2_Status();
                    getFinger3_Status = model.getFinger3_Status();
                    getFinger4_Status = model.getFinger4_Status();
                    getFinger5_Status = model.getFinger5_Status();
                    getFinger6_Status = model.getFinger6_Status();
                    getFinger7_Status = model.getFinger7_Status();
                    getFinger8_Status = model.getFinger8_Status();
                    getFinger9_Status = model.getFinger9_Status();
                    getFinger10_Status = model.getFinger10_Status();

                    //Finger1_Description - Finger10_Description in RTDB

                    getFinger1_Description = model.getFinger1_Description();
                    getFinger2_Description = model.getFinger2_Description();
                    getFinger3_Description = model.getFinger3_Description();
                    getFinger4_Description = model.getFinger4_Description();
                    getFinger5_Description = model.getFinger5_Description();
                    getFinger6_Description = model.getFinger6_Description();
                    getFinger7_Description = model.getFinger7_Description();
                    getFinger8_Description = model.getFinger8_Description();
                    getFinger9_Description = model.getFinger9_Description();
                    getFinger10_Description = model.getFinger10_Description();

                    //Finger1_Time - Finger10_Time in RTDB.

                    getFinger1_Time = model.getFinger1_Time();
                    getFinger2_Time = model.getFinger2_Time();
                    getFinger3_Time = model.getFinger3_Time();
                    getFinger4_Time = model.getFinger4_Time();
                    getFinger5_Time = model.getFinger5_Time();
                    getFinger6_Time = model.getFinger6_Time();
                    getFinger7_Time = model.getFinger7_Time();
                    getFinger8_Time = model.getFinger8_Time();
                    getFinger9_Time = model.getFinger9_Time();
                    getFinger10_Time = model.getFinger10_Time();

                    //Finger1_Date - Finger10_Date in RTDB.

                    getFinger1_Date = model.getFinger1_Date();
                    getFinger2_Date = model.getFinger2_Date();
                    getFinger3_Date = model.getFinger3_Date();
                    getFinger4_Date = model.getFinger4_Date();
                    getFinger5_Date = model.getFinger5_Date();
                    getFinger6_Date = model.getFinger6_Date();
                    getFinger7_Date = model.getFinger7_Date();
                    getFinger8_Date = model.getFinger8_Date();
                    getFinger9_Date = model.getFinger9_Date();
                    getFinger10_Date = model.getFinger10_Date();

                        fingerprintStatus(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status,
                                getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);

                        deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status,
                                getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status,
                                getFinger1_Description, getFinger2_Description, getFinger3_Description, getFinger4_Description, getFinger5_Description,
                                getFinger6_Description, getFinger7_Description, getFinger8_Description, getFinger9_Description, getFinger10_Description,
                                getFinger1_Date, getFinger2_Date, getFinger3_Date, getFinger4_Date, getFinger5_Date,
                                getFinger6_Date, getFinger7_Date, getFinger8_Date, getFinger9_Date, getFinger10_Date,
                                getFinger1_Time, getFinger2_Time, getFinger3_Time, getFinger4_Time, getFinger5_Time,
                                getFinger6_Time, getFinger7_Time, getFinger8_Time, getFinger9_Time, getFinger10_Time
                        );


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getConnectionStatusDelete(String title, String message, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String ID_Value, String process_child, String process_value) {
        /**
         * Call this method instead of enrolledFingerStatus();
         * if Connection == 1 then;
         *      set Connection = 0 and call inputFingerStatus();
         *     else:
         *      show an AlertDialog.
         */

        firebaseDB_Connection.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Connection connection = snapshot.getValue(Connection.class);

                    String internetConnection = connection.getConnection();
                    String connectedSSID = connection.getSSID();

                    String isConnected = "1";
                    String notConnected = "0";

                    if (internetConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        confirmDeleteAlert(title, message, child, fingerDesc_child, fingerDate_child, fingerTime_child, value, ID_Value, process_child, process_value);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getConnectionStatusEnroll(String title, String message, String child, String fingerDesc_child, String fingerDate_child, String fingerTime_child, String value, String ID_Value, String process_child, String process_value) {
        /**
         * Call this method instead of enrolledFingerStatus();
         * if Connection == 1 then;
         *      set Connection = 0 and call inputFingerStatus();
         *     else:
         *      show an AlertDialog.
         */

        firebaseDB_Connection.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Connection connection = snapshot.getValue(Connection.class);

                    String internetConnection = connection.getConnection();
                    String connectedSSID = connection.getSSID();

                    String isConnected = "1";
                    String notConnected = "0";

                    if (internetConnection.equals(isConnected)) {
                        firebaseDB_Connection.child("Connection").setValue("0");
                        confirmEnrollAlert(title, message, child, fingerDesc_child, fingerDate_child, fingerTime_child, value, ID_Value, process_child, process_value);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Device Connection");
                        builder.setMessage("Device is not Connected to the Internet");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void fingerprintStatus(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status) {
    String notEnrolled = "0";
        if(this.getFinger1_Status.equals(notEnrolled)) {
            finger1Status.setText("+ Add a new Fingerprint");
            finger1Status.setTextColor(Color.RED);
        } else {
            finger1Status.setText("Enrolled");
            finger1Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger2_Status.equals(notEnrolled)) {
            finger2Status.setText("+ Add a new Fingerprint");
            finger2Status.setTextColor(Color.RED);
        } else {
            finger2Status.setText("Enrolled");
            finger2Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger3_Status.equals(notEnrolled)) {
            finger3Status.setText("+ Add a new Fingerprint");
            finger3Status.setTextColor(Color.RED);
        } else {
            finger3Status.setText("Enrolled");
            finger3Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger4_Status.equals(notEnrolled)) {
            finger4Status.setText("+ Add a new Fingerprint");
            finger4Status.setTextColor(Color.RED);
        } else {
            finger4Status.setText("Enrolled");
            finger4Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger5_Status.equals(notEnrolled)) {
            finger5Status.setText("+ Add a new Fingerprint");
            finger5Status.setTextColor(Color.RED);
        } else {
            finger5Status.setText("Enrolled");
            finger5Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger6_Status.equals(notEnrolled)) {
            finger6Status.setText("+ Add a new Fingerprint");
            finger6Status.setTextColor(Color.RED);
        } else {
            finger6Status.setText("Enrolled");
            finger6Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger7_Status.equals(notEnrolled)) {
            finger7Status.setText("+ Add a new Fingerprint");
            finger7Status.setTextColor(Color.RED);
        } else {
            finger7Status.setText("Enrolled");
            finger7Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger8_Status.equals(notEnrolled)) {
            finger8Status.setText("+ Add a new Fingerprint");
            finger8Status.setTextColor(Color.RED);
        } else {
            finger8Status.setText("Enrolled");
            finger8Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger9_Status.equals(notEnrolled)) {
            finger9Status.setText("+ Add a new Fingerprint");
            finger9Status.setTextColor(Color.RED);
        } else {
            finger9Status.setText("Enrolled");
            finger9Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger10_Status.equals(notEnrolled)) {
            finger10Status.setText("+ Add a new Fingerprint");
            finger10Status.setTextColor(Color.RED);
        } else {
            finger10Status.setText("Enrolled");
            finger10Status.setTextColor(Color.GREEN);
        }
    }

    private void isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        isConnectedto = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }
}