package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    TextView retrieveDataTest;
    Button btnTest, btnTest2;
    Data data;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRefData, dbRefLoc_M1, dbRefLoc_M3, dbRefLoc1LatLon;
    List TestArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_fingerprint);

        retrieveDataTest = findViewById(R.id.retrieveDataTest);

        DatabaseReference getDataChange = FirebaseDatabase.getInstance().getReference("Alarm");
        DatabaseReference getMagneticStat = getDataChange.child("Magnetic_Stat");

        DatabaseReference getDateChild = FirebaseDatabase.getInstance().getReference("/Test/Location/");

        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
        String date = dateFormat1.format(Calendar.getInstance().getTime());

        List<Location> location = new ArrayList<>();
        List<String> test = new ArrayList<>();

        getDateChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds : snapshot.getChildren()) {
//                    Location location = ds.getValue(Location.class);
//                    locationArrayList.add(location);
//
//                    retrieveDataTest.setText(String.valueOf(locationArrayList));
//
//                }

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Location model = postSnapshot.getValue(Location.class);
                    location.add(model);

                    // this code gets the Location Dates
                    // just need to create a code to display the markers by
                        // selecting the dates retrieved by this code.
                    String parent = postSnapshot.getKey();
                    test.add(parent);

//                    retrieveDataTest.setText(""+model.getTime());

                    retrieveDataTest.setText(""+test);


                }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        getMagneticStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String magnetic = snapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
