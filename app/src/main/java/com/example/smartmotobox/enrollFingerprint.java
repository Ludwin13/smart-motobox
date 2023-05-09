package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.time.Instant;
import java.time.LocalTime;

public class enrollFingerprint extends AppCompatActivity {

    TextView retrieveDataTest;
    Button btnTest, btnTest2;
    Data data;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRefData, dbRefLoc_M1, dbRefLoc_M3, dbRefLoc1LatLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_fingerprint);


    }
}
