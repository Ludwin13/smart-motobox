package com.example.smartmotobox;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
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

import org.w3c.dom.Text;

public class fourthTab extends Fragment {

    TextView finger1Status, finger2Status, finger3Status, finger4Status, finger5Status, finger6Status, finger7Status, finger8Status, finger9Status, finger10Status;
    DatabaseReference firebaseDB_getFingerPrintData, firebaseDB_Data;
    String getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status, getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status;
    String Enrolled = "1";
    String notEnrolled = "0";
    String finished = "2";

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

        return view;
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

//                    deleteFingerID(getFinger1_Status, getFinger2_Status, getFinger3_Status, getFinger4_Status, getFinger5_Status,
//                            getFinger6_Status, getFinger7_Status, getFinger8_Status, getFinger9_Status, getFinger10_Status);
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
            finger2Status.setText("Not Enrolled");
            finger2Status.setTextColor(Color.RED);
        } else {
            finger2Status.setText("Enrolled");
            finger2Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger3_Status.equals(notEnrolled)) {
            finger3Status.setText("Not Enrolled");
            finger3Status.setTextColor(Color.RED);
        } else {
            finger3Status.setText("Enrolled");
            finger3Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger4_Status.equals(notEnrolled)) {
            finger4Status.setText("Not Enrolled");
            finger4Status.setTextColor(Color.RED);
        } else {
            finger4Status.setText("Enrolled");
            finger4Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger5_Status.equals(notEnrolled)) {
            finger5Status.setText("Not Enrolled");
            finger5Status.setTextColor(Color.RED);
        } else {
            finger5Status.setText("Enrolled");
            finger5Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger6_Status.equals(notEnrolled)) {
            finger6Status.setText("Not Enrolled");
            finger6Status.setTextColor(Color.RED);
        } else {
            finger6Status.setText("Enrolled");
            finger6Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger7_Status.equals(notEnrolled)) {
            finger7Status.setText("Not Enrolled");
            finger7Status.setTextColor(Color.RED);
        } else {
            finger7Status.setText("Enrolled");
            finger7Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger8_Status.equals(notEnrolled)) {
            finger8Status.setText("Not Enrolled");
            finger8Status.setTextColor(Color.RED);
        } else {
            finger8Status.setText("Enrolled");
            finger8Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger9_Status.equals(notEnrolled)) {
            finger9Status.setText("Not Enrolled");
            finger9Status.setTextColor(Color.RED);
        } else {
            finger9Status.setText("Enrolled");
            finger9Status.setTextColor(Color.GREEN);
        }

        if(this.getFinger10_Status.equals(notEnrolled)) {
            finger10Status.setText("Not Enrolled");
            finger10Status.setTextColor(Color.RED);
        } else {
            finger10Status.setText("Enrolled");
            finger10Status.setTextColor(Color.GREEN);
        }
    }
}