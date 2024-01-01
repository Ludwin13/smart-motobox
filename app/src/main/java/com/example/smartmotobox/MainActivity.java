package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout_btn;
    TextView user_details;
    FirebaseUser user;
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

        DatabaseReference getDataChange = FirebaseDatabase.getInstance().getReference("/Alarm");
        DatabaseReference getMagneticStat = getDataChange.child("/Magnetic_Stat");
        DatabaseReference getAttemptStat = getDataChange.child("/Attempt_Stat");
        DatabaseReference getTiltStat = getDataChange.child("/Tilt_Stat");
        DatabaseReference disableGPS = FirebaseDatabase.getInstance().getReference("Data");
        disableGPS.child("btn_GPS_Enabler").setValue("0");

        getMagneticStat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String myDate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                String myTime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

                String magnetic = snapshot.getValue(String.class);
                String magneticOn = "1";
                String trigger = "Magnetic Switch Activated";

                if(Objects.requireNonNull(magnetic).equals(magneticOn)) {
                    notificationBuilder(trigger);
                    DatabaseReference historyChange = FirebaseDatabase.getInstance().getReference("History/NewData").child(myDate);

                    String key = historyChange.push().getKey();
//                    String key = setHistoryDataChange.push().getKey();

                    historyChange.child(key).child("Status").setValue(trigger);
                    historyChange.child(key).child("date").setValue(myDate);
                    historyChange.child(key).child("time").setValue(myTime);

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

                String myDate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                String myTime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

                String attempt = snapshot.getValue(String.class);
                String maxAttempt = "1";
                String trigger = "Max Attempt Reached";

                if(Objects.requireNonNull(attempt).equals(maxAttempt)) {
                    notificationBuilder(trigger);
                    DatabaseReference historyChange = FirebaseDatabase.getInstance().getReference("History/NewData").child(myDate);
                   String key = historyChange.child(myDate).push().getKey();

//                    String  = setHistoryDataChange.push().getKey();

                    historyChange.child(key).child("Status").setValue(trigger);
                    historyChange.child(key).child("date").setValue(myDate);
                    historyChange.child(key).child("time").setValue(myTime);

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
                String myDate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                String myTime = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

                String tilt = snapshot.getValue(String.class);
                String tiltOn = "1";
                String trigger = "Tilt Activated";

                if(Objects.requireNonNull(tilt).equals(tiltOn)) {
                    notificationBuilder(trigger);
                    DatabaseReference historyChange = FirebaseDatabase.getInstance().getReference("History/NewData").child(myDate);
                    String key = historyChange.child(myDate).push().getKey();

//                    String  = setHistoryDataChange.push().getKey();

                    historyChange.child(key).child("Status").setValue(trigger);
                    historyChange.child(key).child("date").setValue(myDate);
                    historyChange.child(key).child("time").setValue(myTime);

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
        tabLayout.addTab(tabLayout.newTab().setText("ALARM"));
        tabLayout.addTab(tabLayout.newTab().setText("FINGERPRINT"));


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

        logout_btn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), loginPage.class);
            startActivity(intent);
            finish();
        });


    }

    private void notificationBuilder(String alarmDescription) {
        Intent intent = new Intent(this, SplashScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID="MYCHANNEL";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            nm.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Device Alarm!")
                .setContentText(alarmDescription)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);




        nm.notify(1, builder.build());
    }





}