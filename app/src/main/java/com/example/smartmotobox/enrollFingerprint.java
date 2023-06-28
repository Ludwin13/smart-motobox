package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class enrollFingerprint extends AppCompatActivity {

    TextView fingerprint_0, fingerprint_1, fingerprint_2, fingerprint_3, fingerprint_4, fingerprint_5, fingerprint_6, fingerprint_7, fingerprint_8, fingerprint_9;
    Button enrollBtn;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_fingerprint);

        fingerprint_0 = (TextView) findViewById(R.id.fingerprint_0);
        fingerprint_1 = (TextView) findViewById(R.id.fingerprint_1);
        fingerprint_2 = (TextView) findViewById(R.id.fingerprint_2);
        fingerprint_3 = (TextView) findViewById(R.id.fingerprint_3);
        fingerprint_4 = (TextView) findViewById(R.id.fingerprint_4);
        fingerprint_5 = (TextView) findViewById(R.id.fingerprint_5);
        fingerprint_6 = (TextView) findViewById(R.id.fingerprint_6);
        fingerprint_7 = (TextView) findViewById(R.id.fingerprint_7);
        fingerprint_8 = (TextView) findViewById(R.id.fingerprint_8);
        fingerprint_9 = (TextView) findViewById(R.id.fingerprint_9);

        enrollBtn = (Button) findViewById(R.id.enrollBtn);


        DatabaseReference firebaseDB_getFingerPrintData = FirebaseDatabase.getInstance().getReference("Fingerprint");


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

        firebaseDB_getFingerPrintData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    fingerprintModel model = snapshot.getValue(fingerprintModel.class);

                    String NotEnrolled = "0";

                    String getFinger0_Status = model.getFinger0_Status();
                    String getFinger1_Status = model.getFinger1_Status();
                    String getFinger2_Status = model.getFinger2_Status();
                    String getFinger3_Status = model.getFinger3_Status();
                    String getFinger4_Status = model.getFinger4_Status();
                    String getFinger5_Status = model.getFinger5_Status();
                    String getFinger6_Status = model.getFinger6_Status();
                    String getFinger7_Status = model.getFinger7_Status();
                    String getFinger8_Status = model.getFinger8_Status();
                    String getFinger9_Status = model.getFinger9_Status();


                    if(getFinger0_Status.equals(NotEnrolled)) {
                        fingerprint_0.setText("Not Enrolled");
                        fingerprint_0.setTextColor(Color.RED);
                    } else {
                        fingerprint_0.setText("Enrolled");
                        fingerprint_0.setTextColor(Color.GREEN);
                    }

                    if(getFinger1_Status.equals(NotEnrolled)) {
                        fingerprint_1.setText("Not Enrolled");
                        fingerprint_1.setTextColor(Color.RED);
                    } else {
                        fingerprint_1.setText("Enrolled");
                        fingerprint_1.setTextColor(Color.GREEN);
                    }

                    if(getFinger2_Status.equals(NotEnrolled)) {
                        fingerprint_2.setText("Not Enrolled");
                        fingerprint_2.setTextColor(Color.RED);
                    } else {
                        fingerprint_2.setText("Enrolled");
                        fingerprint_2.setTextColor(Color.GREEN);
                    }

                    if(getFinger3_Status.equals(NotEnrolled)) {
                        fingerprint_3.setText("Not Enrolled");
                        fingerprint_3.setTextColor(Color.RED);
                    } else {
                        fingerprint_3.setText("Enrolled");
                        fingerprint_3.setTextColor(Color.GREEN);
                    }

                    if(getFinger4_Status.equals(NotEnrolled)) {
                        fingerprint_4.setText("Not Enrolled");
                        fingerprint_4.setTextColor(Color.RED);
                    } else {
                        fingerprint_4.setText("Enrolled");
                        fingerprint_4.setTextColor(Color.GREEN);
                    }

                    if(getFinger5_Status.equals(NotEnrolled)) {
                        fingerprint_5.setText("Not Enrolled");
                        fingerprint_5.setTextColor(Color.RED);
                    } else {
                        fingerprint_5.setText("Enrolled");
                        fingerprint_5.setTextColor(Color.GREEN);
                    }

                    if(getFinger6_Status.equals(NotEnrolled)) {
                        fingerprint_6.setText("Not Enrolled");
                        fingerprint_6.setTextColor(Color.RED);
                    } else {
                        fingerprint_6.setText("Enrolled");
                        fingerprint_6.setTextColor(Color.GREEN);
                    }

                    if(getFinger7_Status.equals(NotEnrolled)) {
                        fingerprint_7.setText("Not Enrolled");
                        fingerprint_7.setTextColor(Color.RED);
                    } else {
                        fingerprint_7.setText("Enrolled");
                        fingerprint_7.setTextColor(Color.GREEN);
                    }

                    if(getFinger8_Status.equals(NotEnrolled)) {
                        fingerprint_8.setText("Not Enrolled");
                        fingerprint_8.setTextColor(Color.RED);
                    } else {
                        fingerprint_8.setText("Enrolled");
                        fingerprint_8.setTextColor(Color.GREEN);
                    }

                    if(getFinger9_Status.equals(NotEnrolled)) {
                        fingerprint_9.setText("Not Enrolled");
                        fingerprint_9.setTextColor(Color.RED);
                    } else {
                        fingerprint_9.setText("Enrolled");
                        fingerprint_9.setTextColor(Color.GREEN);
                    }

//                    String getFinger0_Time = model.getFinger0_Time();
//                    String getFinger1_Time = model.getFinger1_Time();
//                    String getFinger2_Time = model.getFinger2_Time();
//                    String getFinger3_Time = model.getFinger3_Time();
//                    String getFinger4_Time = model.getFinger4_Time();
//                    String getFinger5_Time = model.getFinger5_Time();
//                    String getFinger6_Time = model.getFinger6_Time();
//                    String getFinger7_Time = model.getFinger7_Time();
//                    String getFinger8_Time = model.getFinger8_Time();
//                    String getFinger9_Time = model.getFinger9_Time();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
