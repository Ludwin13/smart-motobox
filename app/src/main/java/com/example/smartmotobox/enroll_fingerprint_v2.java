package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class enroll_fingerprint_v2 extends AppCompatActivity {

    Button finger1, finger2, finger3, finger4, finger5, finger6, finger7, finger8, finger9, finger10, backBtn;
    DatabaseReference firebaseDB_getFingerPrintData, firebaseDB_Data;
    String getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status;
    String Enrolled = "1";
    String notEnrolled = "0";
    String finished = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_fingerprint);

        finger1 = (Button) findViewById(R.id.finger1);
        finger2 = (Button) findViewById(R.id.finger2);
        finger3 = (Button) findViewById(R.id.finger3);
        finger4 = (Button) findViewById(R.id.finger4);
        finger5 = (Button) findViewById(R.id.finger5);
        finger6 = (Button) findViewById(R.id.finger6);
        finger7 = (Button) findViewById(R.id.finger7);
        finger8 = (Button) findViewById(R.id.finger8);
        finger9 = (Button) findViewById(R.id.finger9);
        finger10 = (Button) findViewById(R.id.finger10);
        backBtn = (Button) findViewById(R.id.backBtn);

        firebaseDB_getFingerPrintData = FirebaseDatabase.getInstance().getReference("Fingerprint");
        firebaseDB_Data = FirebaseDatabase.getInstance().getReference("Data");

        /**
         * Need to create a limit for fingerprint enrollment
         * Main idea is to create multiple child nodes in the realtime database named Fingerprint_0, Fingerprint_1... so on and so forth
         * each child nodes have sub-children with Status and Time (Time Enrolled).
         * Multiple textviews are used to display for the user to see.
         * A clickable button is used to instruct the fingerprint module to start the enrollment process (changing the btn_Enroll from 0 to 1).
         * But conditions need to be passed before this to happen.
         * There should be atleast one fingerprint node with a sub-child Status = "0" to indicate it that a fingerprint hasn't been used yet.
         * If condition is met then change the btn_Enroll from 0 to 1
         * Else, display a toast (temporarily, preferrably use a popup message for user to be able to read it) indicating that fingerprint enrollment is in process.
         */


        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat1.format(Calendar.getInstance().getTime());

        getFingerprintData();
        buttonMethods();
        deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);

    }

    private void confirmDeleteAlert(String title, String message, String child, String value, String ID_Value, String process_child, String process_value) {
        final String postTitle = "Status";
        final String postMessage = "Deletion in Process, Please wait for audio cue from device";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        postConfirmationAlert(child, value, process_child, process_value, postTitle, postMessage);

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

    private void confirmEnrollAlert(String title, String message, String child, String value, String ID_Value, String process_child, String process_value) {
        final String postTitle = "Status";
        final String postMessage = "Enrollment in Process, Check your fingerprint scanner";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseDB_Data.child("finger_address").setValue(ID_Value);
                        firebaseDB_Data.child("btn_Enroll").setValue("1");
                        Toast.makeText(enroll_fingerprint_v2.this, "Enrollment In Process, check Fingerprint Scanner.", Toast.LENGTH_LONG).show();
                        postConfirmationAlert(child, value, process_child, process_value, postTitle, postMessage);


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

    private void postConfirmationAlert(String child, String value, String process_child, String process_value, String postTitle, String postMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(enroll_fingerprint_v2.this);
        builder.setCancelable(true);
        builder.setTitle(postTitle);
        builder.setMessage(postMessage);

        AlertDialog dialog = builder.create();
        dialog.show();

        firebaseDB_Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Data model = snapshot.getValue(Data.class);
                    String finished_Enroll = model.getBtn_Enroll();
                    String finished_Delete = model.getBtn_Delete();
                    if (finished_Enroll.equals(finished)) {
                        getFingerprintData();
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_Data.child("btn_Enroll").setValue("0");
                        firebaseDB_Data.child("finger_address").setValue("00");
                        dialog.hide();
                        refreshActivity();
                    }

                    if (finished_Delete.equals(finished)) {
                        getFingerprintData();
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_getFingerPrintData.child(process_child).setValue(process_value);
                        firebaseDB_Data.child("btn_Delete").setValue("0");
                        firebaseDB_Data.child("finger_address").setValue("00");
                        dialog.hide();
                        refreshActivity();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void deleteFingerID(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status) {
        final String enroll_Title = "Enroll";
        final String delete_Title = "Delete";
        final String enroll_Message = "Enroll Fingerprint ID #";
        final String delete_Message = "Delete Fingerprint ID #";
        final String enrollValue = "1";
        final String deleteValue = "0";

        finger1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger1_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "1?","Finger1_Status", deleteValue, "11", "btn_Delete", "1");
                }
                if(getFinger1_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "1?","Finger1_Status", enrollValue, "11","btn_Enroll" ,"0");
                }
            }
        });

        finger2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger2_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "2?","Finger2_Status", deleteValue, "13", "btn_Delete", "1");
                }
                if(getFinger2_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "2?","Finger2_Status", enrollValue, "13","btn_Enroll" ,"0");
                }

            }
        });

        finger3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger3_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "3?","Finger3_Status", deleteValue, "15", "btn_Delete", "1");
                }
                if(getFinger3_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "3?","Finger3_Status", enrollValue, "15","btn_Enroll" ,"0");
                }
            }
        });

        finger4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger4_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "4?","Finger4_Status", deleteValue, "17", "btn_Delete", "1");
                }
                if(getFinger4_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "4?","Finger4_Status", enrollValue, "17","btn_Enroll" ,"0");
                }
            }
        });

        finger5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger5_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "5?","Finger5_Status", deleteValue, "19", "btn_Delete", "1");
                }
                if(getFinger5_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "5?","Finger5_Status", enrollValue, "19","btn_Enroll" ,"0");
                }

            }
        });

        finger6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger6_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "6?","Finger6_Status", deleteValue, "21", "btn_Delete", "1");
                }
                if(getFinger6_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "6?","Finger6_Status", enrollValue, "21","btn_Enroll" ,"0");
                }

            }
        });

        finger7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger7_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "7?","Finger7_Status", deleteValue, "23", "btn_Delete", "1");
                }
                if(getFinger7_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "7?","Finger7_Status", enrollValue, "23","btn_Enroll" ,"0");
                }

            }
        });

        finger8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger8_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "8?","Finger8_Status", deleteValue, "25", "btn_Delete", "1");
                }
                if(getFinger8_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "8?","Finger8_Status", enrollValue, "25","btn_Enroll" ,"0");
                }

            }
        });

        finger9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger9_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "9?","Finger9_Status", deleteValue, "27", "btn_Delete", "1");
                }
                if(getFinger9_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "9?","Finger9_Status", enrollValue, "27","btn_Enroll" ,"0");
                }

            }
        });
        finger10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger10_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "10?","Finger10_Status", deleteValue, "29", "btn_Delete", "1");
                }
                if(getFinger10_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "10?","Finger10_Status", enrollValue, "29","btn_Enroll" ,"0");
                }

            }
        });

    }

    private void buttonMethods() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(enroll_fingerprint_v2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void enrollFreeFingerprint() {
//        if(this.getFinger1_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 1 "+getFinger1_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger1_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("11");
//
//        } else if (this.getFinger2_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 2 "+getFinger2_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger2_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("13");
//
//        } else if (this.getFinger3_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 3 "+getFinger3_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger3_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("15");
//
//        } else if (this.getFinger4_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 4 "+getFinger4_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger4_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("17");
//
//        } else if (this.getFinger5_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 5 "+getFinger5_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger5_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("19");
//
//        } else if (this.getFinger6_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 6 "+getFinger6_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger6_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("21");
//
//        } else if (this.getFinger7_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 7 "+getFinger7_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger7_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("23");
//
//        } else if (this.getFinger8_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 8 "+getFinger8_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger8_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("25");
//
//        } else if (this.getFinger9_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 9 "+getFinger9_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger9_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("27");
//
//        } else if (this.getFinger10_Status.equals(notEnrolled)) {
//            Toast.makeText(enrollFingerprint.this, "finger 10 "+getFinger10_Status, Toast.LENGTH_SHORT).show();
//            firebaseDB_getFingerPrintData.child("Finger10_Status").setValue("1");
//            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("29");
//        }
//    }

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
            finger1.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger1.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger2_Status.equals(notEnrolled)) {
            finger2.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger2.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger3_Status.equals(notEnrolled)) {
            finger3.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger3.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger4_Status.equals(notEnrolled)) {
            finger4.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger4.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger5_Status.equals(notEnrolled)) {
            finger5.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger5.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger6_Status.equals(notEnrolled)) {
            finger6.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger6.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger7_Status.equals(notEnrolled)) {
            finger7.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger7.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger8_Status.equals(notEnrolled)) {
            finger8.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger8.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger9_Status.equals(notEnrolled)) {
            finger9.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger9.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }

        if(this.getFinger10_Status.equals(notEnrolled)) {
            finger10.setBackgroundColor((getResources().getColor(R.color.teal_200)));
        } else {
            finger10.setBackgroundColor((getResources().getColor(R.color.purple_200)));
        }
    }

    private void refreshActivity() {
        finish();
        startActivity(getIntent());
    }
}
