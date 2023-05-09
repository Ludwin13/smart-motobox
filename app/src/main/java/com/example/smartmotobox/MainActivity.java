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

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout_btn, unlock, lock, riding, parked, biometric, btnLoc1, btnLoc2, btnLoc3;
    TextView user_details;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRefLat, dbRefLon, dbRefTime;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    tabAdapter tabAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.myActionBar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);

        //***************************TEST****************************************//

        btnLoc1 = (Button) findViewById(R.id.btnLoc1);
        btnLoc2 = (Button) findViewById(R.id.btnLoc2);
        btnLoc3 = (Button) findViewById(R.id.btnLoc3);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRefLat = firebaseDatabase.getReference("/Location/MarkerLat");
        dbRefLon = firebaseDatabase.getReference("/Location/MarkerLon");
        dbRefTime = firebaseDatabase.getReference("/Location/MarkerTime");

        //***************************TEST****************************************//

        FragmentManager fragmentManager = getSupportFragmentManager();
        tabAdapter = new tabAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(tabAdapter);
        viewPager2.setUserInputEnabled(false);

        tabLayout.addTab(tabLayout.newTab().setText("MAP"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTROL"));
        tabLayout.addTab(tabLayout.newTab().setText("NOTIFICATIONS"));


        //***************************TEST****************************************//

        btnLoc1.setOnClickListener(view -> {

            dbRefLat.child("Latitude").setValue("14.589699096190826");
            dbRefLon.child("Longitude").setValue("121.10027442687233");
            dbRefTime.child("Time").setValue("09:59:00.54");

        });

        btnLoc2.setOnClickListener(view -> {

            dbRefLat.child("Latitude").setValue("14.589423949727653");
            dbRefLon.child("Longitude").setValue("121.1009154748915");
            dbRefTime.child("Time").setValue("10:02:00.54");

        });

        btnLoc3.setOnClickListener(view -> {

            dbRefLat.child("Latitude").setValue("14.589164377278017");
            dbRefLon.child("Longitude").setValue("121.10246042715704");
            dbRefTime.child("Time").setValue("10:07:00.54");

        });

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