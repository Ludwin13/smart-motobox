package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout_btn, unlock, lock, riding, parked, biometric, btnLoc1, btnLoc2, btnLoc3;
    TextView user_details;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRefLat, dbRefLon, dbRefTime, dbGPSData;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    tabAdapter tabAdapter;

    //ALL CODES FROM THE TAB SHOULD BE PLACED IN HERE SO IT UPDATES EVERYTIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);

        List<Item> item = new ArrayList<Item>();

        DatabaseReference getDataChange = FirebaseDatabase.getInstance().getReference("Alarm");
        DatabaseReference getMagneticStat = getDataChange.child("Magnetic_Stat");
        DatabaseReference getAttemptStat = getDataChange.child("Attempt_Stat");
        DatabaseReference getTiltStat = getDataChange.child("Tilt_Stat");

//        dbGPSData = firebaseDatabase.getReference("Location");

        DatabaseReference setHistoryDataChange = FirebaseDatabase.getInstance().getReference("History");


        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");


        getMagneticStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String time = timeFormat.format(Calendar.getInstance().getTime());
                String date = dateFormat.format(Calendar.getInstance().getTime());

                String magnetic = snapshot.getValue(String.class);
                String magneticOn = "1";
                String trigger = "Magnetic Switch Activated";

                if(magnetic.equals(magneticOn)) {

                    String key = setHistoryDataChange.push().getKey();

                    setHistoryDataChange.child(key).child("Status").setValue(trigger);
                    setHistoryDataChange.child(key).child("date").setValue(date);
                    setHistoryDataChange.child(key).child("time").setValue(time);

                    getMagneticStat.setValue("0");

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getAttemptStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String time = timeFormat.format(Calendar.getInstance().getTime());
                String date = dateFormat.format(Calendar.getInstance().getTime());

                String attempt = snapshot.getValue(String.class);
                String maxAttempt = "1";
                String trigger = "Max Attempt Reached";

                if(attempt.equals(maxAttempt)) {

                    String key = setHistoryDataChange.push().getKey();

                    setHistoryDataChange.child(key).child("Status").setValue(trigger);
                    setHistoryDataChange.child(key).child("date").setValue(date);
                    setHistoryDataChange.child(key).child("time").setValue(time);

                    getAttemptStat.setValue("0");

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getTiltStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String time = timeFormat.format(Calendar.getInstance().getTime());
                String date = dateFormat.format(Calendar.getInstance().getTime());

                String tilt = snapshot.getValue(String.class);
                String tiltOn = "1";
                String trigger = "Tilt Activated";

                if(tilt.equals(tiltOn)) {

                    String key = setHistoryDataChange.push().getKey();

                    setHistoryDataChange.child(key).child("Status").setValue(trigger);
                    setHistoryDataChange.child(key).child("date").setValue(date);
                    setHistoryDataChange.child(key).child("time").setValue(time);

                    getTiltStat.setValue("0");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        firebaseDatabase = FirebaseDatabase.getInstance();
//        dbRefLat = firebaseDatabase.getReference("/Location/MarkerLat");
//        dbRefLon = firebaseDatabase.getReference("/Location/MarkerLon");
//        dbRefTime = firebaseDatabase.getReference("/Location/MarkerTime");

        //***************************tabLayout****************************************//

        FragmentManager fragmentManager = getSupportFragmentManager();
        tabAdapter = new tabAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(tabAdapter);
        viewPager2.setUserInputEnabled(false);

        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTROL"));
        tabLayout.addTab(tabLayout.newTab().setText("NOTIFICATIONS"));


        //***************************TEST****************************************//

//        btnLoc1.setOnClickListener(view -> {
//
//            String key = dbGPSData.push().getKey();
//
//            dbGPSData.child(key).child("Latitude").setValue("14.589699096190826");
//            dbGPSData.child(key).child("Longitude").setValue("121.10027442687233");
//            dbGPSData.child(key).child("Time").setValue("09:59:00.54");
//
//        });
////
//        btnLoc2.setOnClickListener(view -> {
//
//            String key = dbGPSData.push().getKey();
//
//            dbGPSData.child(key).child("Latitude").setValue("14.589423949727653");
//            dbGPSData.child(key).child("Longitude").setValue("121.1009154748915");
//            dbGPSData.child(key).child("Time").setValue("10:02:00.54");
//
//
//        });
//
//        btnLoc3.setOnClickListener(view -> {
//
//            String key = dbGPSData.push().getKey();
//
//            dbGPSData.child(key).child("Latitude").setValue("14.589164377278017");
//            dbGPSData.child(key).child("Longitude").setValue("121.10246042715704");
//            dbGPSData.child(key).child("Time").setValue("10:07:00.54");
//
//
//        });

        //***************************TEST****************************************//


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


//        Fragment fragment = new MapFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("Location");
//
//        databaseReference.child("Latitude").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        databaseReference.child("Longitude").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        auth = FirebaseAuth.getInstance();
        logout_btn = findViewById(R.id.logout_btn);
        user_details = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), loginPage.class);
            startActivity(intent);
            finish();
        } else {
            user_details.setText(user.getEmail());
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), loginPage.class);
                startActivity(intent);
                finish();
            }
        });


    }
}