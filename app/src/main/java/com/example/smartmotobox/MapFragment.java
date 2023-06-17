package com.example.smartmotobox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class MapFragment extends Fragment {
    private DatabaseReference dbRefLatLonTime, dbRefLoc2LatLon, dbRefLat, dbRefLon, dbRefTime, dbGpsData;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Marker> markerList;

    // latVar1 and longVar2 should be read using .get() and onDataChange()
    // latLng database structure is unlimited, but Array for MapMarkers will be limited to 5 items.
    // latLngs.add(new LatLng(latVar1, longVar2));

    // Professors need 1 week worth of gps data to review, so my idea is:
        // create an Arraylist<> to store locations sent by the GPS module to the Realtime Database.
        // and increment them and display in a fourthFragment(HISTORY).
        // contents in a CardView or ListView will be Marker(incremented number), LatLng, and DateTime.

    // Arduino Code should only use the setString command, as much as possible.
        // GPS module can retrieve date and time when finding a location using the TinyGPS++ library.
        // Database structure can be named by concatenating the date and time of the retrieved GPS data
        // Similar to the History/Notifications block code in Kodular Creator
        // Problem will be retrieving the data and storing them in the array
            // Maybe I can use onDataChange and retrieve the latest value and store them in the array
            // To which I can retrieve it as soon as it is stored and display it on a map marker.
            // Maybe I do not need to limit the map markers. Maybe I just need to focus on how arraylist works!!!!!
            // create an array for the map markers and assign ID for them.
            // So for every change of the LatLng from the object child the previous marker will be erased.
            // Create a method to check if the object exists in the array, if it exists then only then it will replace


    // Firebase should still be working even if the application is disconnected to the internet.
        // Scratch that, previous application only works when user has been logged in.
        // Effectively getting notification even after minimizing the app (based on the android application life-cycle).
    //        latLngs.add(new LatLng(14,121));

    // Restructure to store map markers to an arraylist and then add to markers.
        // NodeMCU code should only setString to an existing child.
        // For every data change in the "Location" Object, we add the children "Latitude", "Longitude", "Date", and "Time"
            // to their own separate array, and create a marker for them.


    //Maybe need onDataChange to listen to changes in Realtime Database and update the Map Markers.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Spinner FirebaseDB_Spinner = view.findViewById(R.id.spinner);
        List<String> Date = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Date);
        FirebaseDB_Spinner.setAdapter(adapter);

        DatabaseReference FirebaseDB_GPSDate = FirebaseDatabase.getInstance().getReference().child("Test/Location");

        SupportMapFragment supportMapFragmentInitialization = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        FirebaseDB_GPSDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date.clear();



                for(DataSnapshot postSnapshot : snapshot.getChildren()) {

                    /**
                     *  this code gets the Location Dates
                     *  just need to create a code to display the markers by
                     *  selecting the dates retrieved by this code.
                     */

                    String parent = postSnapshot.getKey();
                    Date.add(parent);
                    adapter.notifyDataSetChanged();


                    /**
                     * Need to create a code to zoom in on the newest received location from the Database
                     * Problem is the app crashes whenever new data is inserted to the database.
                     * Maybe use SQLite? It probably won't still fix the issue since new inserted data technically doesn't exist yet (null).
                     * Need to refresh the app to successfully read it.
                     * Maybe an app refresher?
                     */

                    TextView Test = view.findViewById(R.id.Test);

                    FirebaseDB_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedDate = FirebaseDB_Spinner.getItemAtPosition(i).toString();



                            supportMapFragmentInitialization.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(@NonNull GoogleMap googleMap) {
                                    googleMap.clear();

                                    DatabaseReference FirebaseDB_GPSDateMarkerData = FirebaseDB_GPSDate.child(selectedDate);

                                    FirebaseDB_GPSDateMarkerData.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            List<Location> location = new ArrayList<>();
                                            location.clear();

                                            for(DataSnapshot postSnapshot : snapshot.getChildren()) {

                                                Location model = postSnapshot.getValue(Location.class);

                                                String LongitudeStr = model.getLongitude().trim(); //producing error!!
                                                String LatitudeStr = model.getLatitude().trim();
                                                String Time = model.getTime();


                                                Double Latitude = Double.valueOf(LatitudeStr);
                                                Double Longitude = Double.valueOf(LongitudeStr);

                                                Test.setText(Time);


                                                addMarker(Latitude, Longitude, Time, selectedDate);


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        firebaseDatabase = FirebaseDatabase.getInstance();

        dbRefLat = firebaseDatabase.getReference("/Location/MarkerLat");
        dbRefLon = firebaseDatabase.getReference("/Location/MarkerLon");
        dbRefTime = firebaseDatabase.getReference("/Location/MarkerTime");
        dbRefLatLonTime = firebaseDatabase.getReference("/Location");

        dbGpsData = firebaseDatabase.getReference("Location");

//        getLatLonTimeFirebase(new dataCallBack() {
//            @Override
//            public void onCallbackLatLonTime(double mLat, double mLon, String mTime) {
//                initSupportMapFragment(mLat, mLon, mTime);
//
//            }
//        });

        initSupportMapFragment();
        getGPSData();


        return view;
    }

    private void getGPSData() {

        dbGpsData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addMarker(Double Latitude, Double Longitude, String Time, String selectedDate) {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //Initialize variables for LatLng using the last detected location in firebase realtime database.
                LatLng marker2 = new LatLng(Latitude, Longitude);
                googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
                String snippet = "Latitude: " + Latitude + "\n" + "Longitude: " + Longitude + "\n" + "Time: " + Time;
                googleMap.addMarker(new MarkerOptions()
                        .position(marker2)
                        .title("" + selectedDate)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet(snippet));
            }
        });


    }



//    public interface dataCallBack {
//        void onCallbackLatLonTime(double mLat, double mLon, String mTime);
//
//
//    }
//
//    private void getLatLonTimeFirebase(dataCallBack dataCallBack) {
//
//        dbRefLatLonTime.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String lat = snapshot.child("/MarkerLat/Latitude").getValue(String.class);
//                double mLat = Double.parseDouble(lat);
//                String lon = snapshot.child("/MarkerLon/Longitude").getValue(String.class);
//                double mLon = Double.parseDouble(lon);
//                String mTime = snapshot.child("/MarkerTime/Time").getValue(String.class);
//                dataCallBack.onCallbackLatLonTime(mLat, mLon, mTime);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }

//    private void initSupportMapFragment(double mLat, double mLon, String mTime)

    private void initSupportMapFragment() {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //Initialize variables for LatLng using the last detected location in firebase realtime database.
                CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(014.576603, 121.101320), 15f);
                googleMap.moveCamera(point);
                googleMap.animateCamera(point);

                LatLng marker1 = new LatLng(014.576603, 121.101320);
                googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
                String snippet = "Latitude: 014.576603\n" + "Longitude: 121.101320\n" + "Time: 05:03:10";
                googleMap.addMarker(new MarkerOptions()
                        .position(marker1)
                        .title("Marker1")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet(snippet));
            }
        });

    }

    private void refreshMap() {

        SupportMapFragment supportMapFragmentInitialization = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        supportMapFragmentInitialization.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.clear();
            }
        });

    }


        // Need to retrieve gps data (/Location/Sub-Children) from  Firebase Realtime Database.
        // Create a clickable list containing the saved gps date from the database.
            // Upon selecting a date, display the history by adding all markers inside the Google Maps Fragment.
        // Will utilize map fragments
}