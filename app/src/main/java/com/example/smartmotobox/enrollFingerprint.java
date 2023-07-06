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

public class enrollFingerprint extends AppCompatActivity {

    TextView  fingerprint_1, fingerprint_2, fingerprint_3, fingerprint_4, fingerprint_5, fingerprint_6, fingerprint_7, fingerprint_8, fingerprint_9, fingerprint_10;
    Button enrollBtn, backBtn;
    DatabaseReference firebaseDB_getFingerPrintData, firebaseDB_setBtn_Enroll;
    String getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status;
    Data data;
    String finger1, finger2, finger3, finger4, finger5, finger6, finger7, finger8, finger9, finger10;
    String Enrolled = "1";
    String notEnrolled = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_fingerprint);

        fingerprint_1 = (TextView) findViewById(R.id.fingerprint_1);
        fingerprint_2 = (TextView) findViewById(R.id.fingerprint_2);
        fingerprint_3 = (TextView) findViewById(R.id.fingerprint_3);
        fingerprint_4 = (TextView) findViewById(R.id.fingerprint_4);
        fingerprint_5 = (TextView) findViewById(R.id.fingerprint_5);
        fingerprint_6 = (TextView) findViewById(R.id.fingerprint_6);
        fingerprint_7 = (TextView) findViewById(R.id.fingerprint_7);
        fingerprint_8 = (TextView) findViewById(R.id.fingerprint_8);
        fingerprint_9 = (TextView) findViewById(R.id.fingerprint_9);
        fingerprint_10 = (TextView) findViewById(R.id.fingerprint_10);

        backBtn = (Button) findViewById(R.id.backBtn);

        firebaseDB_getFingerPrintData = FirebaseDatabase.getInstance().getReference("Fingerprint");
        firebaseDB_setBtn_Enroll = FirebaseDatabase.getInstance().getReference("Data");


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


//        enrollBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                firebaseDB_getFingerPrintData.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            fingerprintModel model = snapshot.getValue(fingerprintModel.class);
//
//                            String NotEnrolled = "0";
//                            String Enrolled = "1";
//
//                            String startEnroll = "1";
//
//                            String getFinger1_Status = model.getFinger1_Status();
//                            String getFinger2_Status = model.getFinger2_Status();
//                            String getFinger3_Status = model.getFinger3_Status();
//                            String getFinger4_Status = model.getFinger4_Status();
//                            String getFinger5_Status = model.getFinger5_Status();
//                            String getFinger6_Status = model.getFinger6_Status();
//                            String getFinger7_Status = model.getFinger7_Status();
//                            String getFinger8_Status = model.getFinger8_Status();
//                            String getFinger9_Status = model.getFinger9_Status();
//                            String getFinger10_Status = model.getFinger10_Status();
//
//                            if (getFinger10_Status.equals(Enrolled) && getFinger1_Status.equals(Enrolled) && getFinger2_Status.equals(Enrolled) && getFinger3_Status.equals(Enrolled) && getFinger4_Status.equals(Enrolled)
//                            && getFinger5_Status.equals(Enrolled) && getFinger6_Status.equals(Enrolled) && getFinger7_Status.equals(Enrolled) && getFinger8_Status.equals(Enrolled) && getFinger9_Status.equals(Enrolled)) {
//                                Toast.makeText(enrollFingerprint.this, "Fingerprint Slots Full", Toast.LENGTH_LONG).show();
//                            }
//
////                            else {
////                                firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue(startEnroll);
////                            }
//                            if (getFinger10_Status.equals(NotEnrolled) || getFinger1_Status.equals(NotEnrolled) || getFinger2_Status.equals(NotEnrolled) || getFinger3_Status.equals(NotEnrolled) || getFinger4_Status.equals(NotEnrolled)
//                                    && getFinger5_Status.equals(NotEnrolled) || getFinger6_Status.equals(NotEnrolled) || getFinger7_Status.equals(NotEnrolled) || getFinger8_Status.equals(NotEnrolled) || getFinger9_Status.equals(NotEnrolled))
//
//                            finger1 = getFinger1_Status + getFinger2_Status + getFinger3_Status + getFinger4_Status + getFinger5_Status
//                                    + getFinger6_Status + getFinger7_Status + getFinger8_Status + getFinger9_Status + getFinger10_Status;
//
//                                Toast.makeText(enrollFingerprint.this, ""+finger1, Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });

    }

    private void confirmDeleteAlert(String title, String message, String child, String value, String ID_Value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_setBtn_Enroll.child("btn_Delete").setValue(ID_Value);
                        getFingerprintData();
                        refreshActivity();

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

    private void confirmEnrollAlert(String title, String message, String child, String value, String ID_Value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseDB_getFingerPrintData.child(child).setValue(value);
                        firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue(ID_Value);
                        getFingerprintData();
                        refreshActivity();

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

    private void deleteFingerID(String getFinger1_Status, String getFinger2_Status, String getFinger3_Status, String getFinger4_Status, String getFinger5_Status, String getFinger6_Status, String getFinger7_Status, String getFinger8_Status, String getFinger9_Status, String getFinger10_Status) {
        String enroll_Title = "Enroll";
        String delete_Title = "Delete";
        String enroll_Message = "Enroll Fingerprint ID #";
        String delete_Message = "Delete Fingerprint ID #";
        String enrollValue = "1";
        String deleteValue = "0";



        fingerprint_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger1_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "1?","Finger1_Status", deleteValue, "11");
                }
                if(getFinger1_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "1?","Finger1_Status", enrollValue, "11");
                }
            }
        });

        fingerprint_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger2_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "2?","Finger2_Status", deleteValue, "13");
                }
                if(getFinger2_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "2?","Finger2_Status", enrollValue, "13");
                }

            }
        });

        fingerprint_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger3_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "3?","Finger3_Status", deleteValue, "15");
                }
                if(getFinger3_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "3?","Finger3_Status", enrollValue, "15");
                }
            }
        });

        fingerprint_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger4_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "4?","Finger4_Status", deleteValue, "17");
                }
                if(getFinger4_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "4?","Finger4_Status", enrollValue, "17");
                }
            }
        });

        fingerprint_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger5_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "5?","Finger5_Status", deleteValue, "19");
                }
                if(getFinger5_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "5?","Finger5_Status", enrollValue, "19");
                }

            }
        });

        fingerprint_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger6_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "6?","Finger6_Status", deleteValue, "21");
                }
                if(getFinger6_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "6?","Finger6_Status", enrollValue, "21");
                }

            }
        });

        fingerprint_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger7_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "7?","Finger7_Status", deleteValue, "23");
                }
                if(getFinger7_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "7?","Finger7_Status", enrollValue, "23");
                }

            }
        });

        fingerprint_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger8_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "8?","Finger8_Status", deleteValue, "25");
                }
                if(getFinger8_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "8?","Finger8_Status", enrollValue, "25");
                }

            }
        });

        fingerprint_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger9_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "9?","Finger9_Status", deleteValue, "27");
                }
                if(getFinger9_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "9?","Finger9_Status", enrollValue, "27");
                }

            }
        });
        fingerprint_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFinger10_Status.equals(Enrolled)) {
                    confirmDeleteAlert(delete_Title, delete_Message + "10?","Finger10_Status", deleteValue, "29");
                }
                if(getFinger10_Status.equals(notEnrolled)) {
                    confirmEnrollAlert(enroll_Title, enroll_Message + "10?","Finger10_Status", enrollValue, "29");
                }

            }
        });

    }

    private void buttonMethods() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(enrollFingerprint.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void enrollFreeFingerprint() {
        if(this.getFinger1_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 1 "+getFinger1_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger1_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("11");

        } else if (this.getFinger2_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 2 "+getFinger2_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger2_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("13");

        } else if (this.getFinger3_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 3 "+getFinger3_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger3_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("15");

        } else if (this.getFinger4_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 4 "+getFinger4_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger4_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("17");

        } else if (this.getFinger5_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 5 "+getFinger5_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger5_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("19");

        } else if (this.getFinger6_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 6 "+getFinger6_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger6_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("21");

        } else if (this.getFinger7_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 7 "+getFinger7_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger7_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("23");

        } else if (this.getFinger8_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 8 "+getFinger8_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger8_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("25");

        } else if (this.getFinger9_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 9 "+getFinger9_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger9_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("27");

        } else if (this.getFinger10_Status.equals(notEnrolled)) {
            Toast.makeText(enrollFingerprint.this, "finger 10 "+getFinger10_Status, Toast.LENGTH_SHORT).show();
            firebaseDB_getFingerPrintData.child("Finger10_Status").setValue("1");
            firebaseDB_setBtn_Enroll.child("btn_Enroll").setValue("29");
        }
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
            fingerprint_1.setText("Not Enrolled");
            fingerprint_1.setTextColor(Color.RED);
        } else {
            fingerprint_1.setText("Enrolled");
            fingerprint_1.setTextColor(Color.GREEN);
        }

        if(this.getFinger2_Status.equals(notEnrolled)) {
            fingerprint_2.setText("Not Enrolled");
            fingerprint_2.setTextColor(Color.RED);
        } else {
            fingerprint_2.setText("Enrolled");
            fingerprint_2.setTextColor(Color.GREEN);
        }

        if(this.getFinger3_Status.equals(notEnrolled)) {
            fingerprint_3.setText("Not Enrolled");
            fingerprint_3.setTextColor(Color.RED);
        } else {
            fingerprint_3.setText("Enrolled");
            fingerprint_3.setTextColor(Color.GREEN);
        }

        if(this.getFinger4_Status.equals(notEnrolled)) {
            fingerprint_4.setText("Not Enrolled");
            fingerprint_4.setTextColor(Color.RED);
        } else {
            fingerprint_4.setText("Enrolled");
            fingerprint_4.setTextColor(Color.GREEN);
        }

        if(this.getFinger5_Status.equals(notEnrolled)) {
            fingerprint_5.setText("Not Enrolled");
            fingerprint_5.setTextColor(Color.RED);
        } else {
            fingerprint_5.setText("Enrolled");
            fingerprint_5.setTextColor(Color.GREEN);
        }

        if(this.getFinger6_Status.equals(notEnrolled)) {
            fingerprint_6.setText("Not Enrolled");
            fingerprint_6.setTextColor(Color.RED);
        } else {
            fingerprint_6.setText("Enrolled");
            fingerprint_6.setTextColor(Color.GREEN);
        }

        if(this.getFinger7_Status.equals(notEnrolled)) {
            fingerprint_7.setText("Not Enrolled");
            fingerprint_7.setTextColor(Color.RED);
        } else {
            fingerprint_7.setText("Enrolled");
            fingerprint_7.setTextColor(Color.GREEN);
        }

        if(this.getFinger8_Status.equals(notEnrolled)) {
            fingerprint_8.setText("Not Enrolled");
            fingerprint_8.setTextColor(Color.RED);
        } else {
            fingerprint_8.setText("Enrolled");
            fingerprint_8.setTextColor(Color.GREEN);
        }

        if(this.getFinger9_Status.equals(notEnrolled)) {
            fingerprint_9.setText("Not Enrolled");
            fingerprint_9.setTextColor(Color.RED);
        } else {
            fingerprint_9.setText("Enrolled");
            fingerprint_9.setTextColor(Color.GREEN);
        }

        if(this.getFinger10_Status.equals(notEnrolled)) {
            fingerprint_10.setText("Not Enrolled");
            fingerprint_10.setTextColor(Color.RED);
        } else {
            fingerprint_10.setText("Enrolled");
            fingerprint_10.setTextColor(Color.GREEN);
        }
    }

    private void refreshActivity() {
        finish();
        startActivity(getIntent());
    }
}
