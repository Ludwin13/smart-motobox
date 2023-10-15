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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

public class fourthTab extends Fragment {

    TextView finger1Status, finger2Status, finger3Status, finger4Status, finger5Status, finger6Status, finger7Status, finger8Status, finger9Status, finger10Status;
    DatabaseReference firebaseDB_getFingerPrintData, firebaseDB_Data, getFingerprintData2;
    String getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status;
    String Enrolled = "1";
    String notEnrolled = "0";
    String finished = "2";
    ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
    ArrayAdapter<CharSequence> adapter;
    EditText txt; // user input bar


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

        getFingerprintData();
        deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);

        return view;
    }

    private void deleteFingerID(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status) {
        final String enroll_Title = "Enroll";
        final String delete_Title = "Delete";
        final String enroll_Message = "Enroll Fingerprint ID #";
        final String delete_Message = "Delete Fingerprint ID #";
        final String enrollValue = "1";
        final String deleteValue = "0";



        finger1Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger1_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "1?","Finger1_Status", "Finger1_Description", "Finger1_DateTime", deleteValue, "11", "btn_Delete", "1");
                }
                if(getFinger1_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "1?","Finger1_Status","Finger1_Description", "Finger1_DateTime", enrollValue, "11","btn_Enroll" ,"0");
                }
            }
        });

        finger2Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger2_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "2?","Finger2_Status", "Finger2_Description","Finger2_DateTime",  deleteValue, "13", "btn_Delete", "1");
                }
                if(getFinger2_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "2?","Finger2_Status", "Finger2_Description","Finger2_DateTime",  enrollValue, "13","btn_Enroll" ,"0");
                }

            }
        });

        finger3Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger3_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "3?","Finger3_Status", "Finger3_Description","Finger3_DateTime",  deleteValue, "15", "btn_Delete", "1");
                }
                if(getFinger3_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "3?","Finger3_Status", "Finger3_Description","Finger3_DateTime",  enrollValue, "15","btn_Enroll" ,"0");
                }
            }
        });

        finger4Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger4_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "4?","Finger4_Status", "Finger4_Description","Finger4_DateTime",  deleteValue, "17", "btn_Delete", "1");
                }
                if(getFinger4_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "4?","Finger4_Status", "Finger4_Description","Finger4_DateTime",  enrollValue, "17","btn_Enroll" ,"0");
                }
            }
        });

        finger5Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger5_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "5?","Finger5_Status", "Finger5_Description","Finger5_DateTime",  deleteValue, "19", "btn_Delete", "1");
                }
                if(getFinger5_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "5?","Finger5_Status", "Finger5_Description","Finger5_DateTime",  enrollValue, "19","btn_Enroll" ,"0");
                }

            }
        });

        finger6Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger6_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "6?","Finger6_Status", "Finger6_Description","Finger6_DateTime",  deleteValue, "21", "btn_Delete", "1");
                }
                if(getFinger6_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "6?","Finger6_Status", "Finger6_Description", "Finger6_DateTime", enrollValue, "21","btn_Enroll" ,"0");
                }

            }
        });

        finger7Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger7_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "7?","Finger7_Status", "Finger7_Description","Finger7_DateTime",  deleteValue, "23", "btn_Delete", "1");
                }
                if(getFinger7_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "7?","Finger7_Status","Finger7_Description", "Finger7_DateTime",  enrollValue, "23","btn_Enroll" ,"0");
                }

            }
        });

        finger8Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger8_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "8?","Finger8_Status", "Finger8_Description","Finger8_DateTime",  deleteValue, "25", "btn_Delete", "1");
                }
                if(getFinger8_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "8?","Finger8_Status", "Finger8_Description","Finger8_DateTime",  enrollValue, "25","btn_Enroll" ,"0");
                }

            }
        });

        finger9Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger9_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "9?","Finger9_Status", "Finger9_Description","Finger9_DateTime",  deleteValue, "27", "btn_Delete", "1");
                }
                if(getFinger9_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "9?","Finger9_Status", "Finger9_Description","Finger9_DateTime",  enrollValue, "27","btn_Enroll" ,"0");
                }

            }
        });
        finger10Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger10_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "10?","Finger10_Status", "Finger10_Description","Finger10_DateTime",  deleteValue, "29", "btn_Delete", "1");
                }
                if(getFinger10_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "10?","Finger10_Status", "Finger10_Description","Finger10_DateTime",  enrollValue, "29","btn_Enroll" ,"0");
                }

            }
        });

    }

    private void confirmDeleteAlert(String title, String message, String child, String fingerDesc_child, String finger_DateTime, String value, String ID_Value, String process_child, String process_value) {
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
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_Data.child("btn_Delete").setValue("1");
                        firebaseDB_Data.child("finger_address").setValue(ID_Value);
                        postConfirmationAlert(child, fingerDesc_child, finger_DateTime, value, process_child, process_value, postTitle, postMessage, nullInput);

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

    private void confirmEnrollAlert(String title, String message, String child, String fingerDesc_child, String finger_DateTime, String value, String ID_Value, String process_child, String process_value) {
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
                        inputFingerDesc(title, message, child, fingerDesc_child, finger_DateTime, value, ID_Value, process_child, process_value);

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

    private void inputFingerDesc(String title, String message, String child, String fingerDesc_child, String finger_DateTime, String value, String ID_Value, String process_child, String process_value) {

        final String postTitle = "Status";
        final String postMessage = "Enrollment in Process, Check your fingerprint scanner";

        LinearLayout layoutName = new LinearLayout(getContext());
        layoutName.setOrientation(LinearLayout.VERTICAL);

        final EditText etFingerDesc = new EditText(getContext());
        etFingerDesc.setHint("Input Fingerprint Desc. e.g. Middle FInger");
//        LinearLayout.LayoutParams etFingerDescParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        final TextInputLayout  tiFingerDescLayout = new TextInputLayout(getContext());
//        LinearLayout.LayoutParams etFingerDescLayout_Params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        tiFingerDescLayout.setLayoutParams(etFingerDescLayout_Params);
//        etFingerDesc.setHint("Input Finger Description. e.g. Middle Finger");
//        tiFingerDescLayout.addView(etFingerDesc, etFingerDescParams);

//        View viewDivider = new View(getContext());
//        float dividerHeight = getResources().getDisplayMetrics().density * 1; // 1dp to pixels
//        viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) dividerHeight));
//        viewDivider.setBackgroundColor(Color.parseColor("#000000"));
//
//        layoutName.addView(viewDivider);
        layoutName.addView(etFingerDesc);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(layoutName);
        builder.setTitle("Finger Description");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fingerDescription = etFingerDesc.getText().toString(); // variable to collect user input
                        firebaseDB_Data.child("finger_address").setValue(ID_Value);
                        firebaseDB_Data.child("btn_Enroll").setValue("1");
                        Toast.makeText(getContext(), "Enrollment In Process, check Fingerprint Scanner.", Toast.LENGTH_LONG).show();
                        postConfirmationAlert(child, fingerDesc_child, finger_DateTime, value, process_child, process_value, postTitle, postMessage, fingerDescription);

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

    private void postConfirmationAlert(String child, String fingerDesc_child, String finger_DateTime, String value, String process_child, String process_value, String postTitle, String postMessage, String fingerDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(postTitle);
        builder.setMessage(postMessage);

        AlertDialog dialog = builder.create();
        dialog.show();

        firebaseDB_Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Data model = snapshot.getValue(Data.class);
                    String finished_Enroll = model.getBtn_Enroll();
                    String finished_Delete = model.getBtn_Delete();
                    if (finished_Enroll.equals(finished)) {
                        getFingerprintData();
                        String myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(fingerDesc_child).setValue(fingerDescription);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_getFingerPrintData.child(finger_DateTime).setValue(myDate);
                        firebaseDB_Data.child("btn_Enroll").setValue("0");
                        firebaseDB_Data.child("finger_address").setValue("00");
                        dialog.hide();
                    }

                    if (finished_Delete.equals(finished)) {
                        getFingerprintData();
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_getFingerPrintData.child(fingerDesc_child).setValue("");
                        firebaseDB_getFingerPrintData.child(finger_DateTime).setValue("");
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

                    fingerprintStatus(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status,
                            getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);

                    deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status,
                            getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void fingerprintStatus(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status) {

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


}