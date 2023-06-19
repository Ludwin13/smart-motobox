package com.example.smartmotobox;

import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
        List<String> getSpecificParentDate = new ArrayList<>();
        List<String> getAllParentDates = new ArrayList<>();
        TextView Test = view.findViewById(R.id.Test);
        SimpleDateFormat dateFormat = new SimpleDateFormat("M-dd-yyyy");



        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getAllParentDates);
        FirebaseDB_Spinner.setAdapter(adapter);

        /** Firebase Realtime Database Reference Initialization....
         * For Main GPS, Tracks onDataChange wherever the child is.
         * Without manual interference in the database then it will only get the latest data write. */
        DatabaseReference Firebase_GPSDataChange = FirebaseDatabase.getInstance().getReference().child("Test/Location");
        /** For Spinner content (GPS History) */
        DatabaseReference FirebaseDB_GPSDate = FirebaseDatabase.getInstance().getReference().child("Test/Location");
        /** Google Maps API Initialization */
        SupportMapFragment supportMapFragmentInitialization = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        Firebase_GPSDataChange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**
                 * .setText(snapshot) results to Location
                 * getting the snapshot.children (Location) will iterate through the Date Children
                 * .setText(postSnapshot) results to the last created date node. So if 6-16-2023 is the last in the JSON Structure then it will only display that.
                 * Need a way to retrieve Dates for the marker or maybe not?
                 *
                 * DataSnapshot snapshot = Location
                 */
                getSpecificParentDate.clear();
                if(snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if(postSnapshot.exists()) {
                            /**
                             * postSnapshot.getKey() retrieves the postSnapshot children names (Date) e.g. (6-16-2023);
                             * imported SimpleDateFormat to determine the current Date.
                             * The main idea is:
                             *      If the currentDate is equal to the latest postSnapshot children name with the same date
                             *      then it will create a query pointing to that specific Date Node and retrieve the Marker Data.
                             */
                            String parent = postSnapshot.getKey();
                            String date = dateFormat.format(Calendar.getInstance().getTime());


                            if (parent.equals(date)) {
                                String currentDate = parent;
                                DatabaseReference Firebase_GPSCurrentDate = FirebaseDB_GPSDate.child(currentDate);
                                Firebase_GPSCurrentDate.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        /**
                                         * at this point the query points to the child node with the same name as the value of the currentDate variable.
                                         * So snapshot will display only the date name (Parent).
                                         * DataSnapshot snapshot = Dates (6-16-2023, 6-17-2023) etc.
                                         */
                                        for(DataSnapshot postSnapshot : snapshot.getChildren()) {

                                            /**
                                             * Need an enhanced for loop to increment through the snapshot (Parent Node/Date).
                                             * This will retrieve children of the Date nodes e.g. nodes that start with "-NDY..."
                                             */
                                            Location model = postSnapshot.getValue(Location.class);
                                            /**
                                             *
                                             */

                                            String LatitudeStr = model.getLatitude();
                                            String LongitudeStr = model.getLongitude();
                                            String Time = model.getTime();



                                            if (LatitudeStr == null || LongitudeStr == null || Time == null) {
                                                break;
                                            } else {

                                                Double Latitude = Double.valueOf(LatitudeStr);
                                                Double Longitude = Double.valueOf(LongitudeStr);
                                                Test.setText(Latitude.toString());

                                                addMarker(Latitude, Longitude, Time, currentDate);


                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            } else {
                                Test.setText("SEX");
                            }




                        }

                    }

                }

//                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    if(postSnapshot.exists()) {
//                        Location model = postSnapshot.getValue(Location.class);
//
//                        Double Longitude = model.getLongitude(); //producing error!!
//                        Double Latitude = model.getLatitude();
//                        String Time = model.getTime();
//
//                        if (Longitude == null || Latitude == null || Time == null) {
//                            break;
//
//                        } else  {
////                            addMarker(Latitude, Longitude, Time, selectedDate);
//
//                        }
////                                                Double Latitude = Double.parseDouble(LatitudeStr);
//                    } else {
//                        // Do Nothing
//                    }
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDB_GPSDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getAllParentDates.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()) {

                    /**
                     *  this code gets the Location Dates
                     *  just need to create a code to display the markers by
                     *  selecting the dates retrieved by this code.
                     */

                    String parent = postSnapshot.getKey();
                    getAllParentDates.add(parent);
                    adapter.notifyDataSetChanged();
                    /**
                     * Need to create a code to zoom in on the newest received location from the Database
                     * Problem is the app crashes whenever new data is inserted to the database.
                     * Maybe use SQLite? It probably won't still fix the issue since new inserted data technically doesn't exist yet (null).
                     * Need to refresh the app to successfully read it.
                     * Maybe an app refresher?
                     */



                    FirebaseDB_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String selectedDate = FirebaseDB_Spinner.getItemAtPosition(i).toString();

                            if (selectedDate == "--SELECT HISTORY--") {

                            } else {

                                supportMapFragmentInitialization.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(@NonNull GoogleMap googleMap) {
                                        googleMap.clear();

                                        DatabaseReference FirebaseDB_GPSDateMarkerData = FirebaseDB_GPSDate.child(selectedDate);

                                        FirebaseDB_GPSDateMarkerData.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                /**
                                                 * DataSnapshot snapshot == every child node under the parent node from the FirebaseDB_GPSDateMarkerData
                                                 * So for example;
                                                 *      FirebaseDB_GPSDate == ("Test/Location");
                                                 *      selectedDate == ("6-16-2023);
                                                 *      FirebaseDB_GPSDateMarkerData = FirebaseDB_GPSDate.child(selectedDate);
                                                 *                                   = ("Test/Location").child("6-16"-2023);
                                                 *                                   = ("Test/Location/6-16-2023); == DataSnapshot snapshot
                                                 * So under that "Test/Location/6-16-2023" path, there's existing data which are also called child nodes.
                                                 * In my case the child nodes are each uniquely named with the starting "-NYD..."
                                                 * for every child nodes under that path, there are sub-child nodes structured from Latitude, Longitude, Time.
                                                 * For Example:
                                                 *      } Test
                                                 *          } Location
                                                 *              } 6-16-2023
                                                 *                  } -NYD...
                                                 *                      } Latitude = 14.222... (Number Object)
                                                 *                      } Longitude = 121.222... (Number Object)
                                                 *                      } Time = "16:16:16" (String Object)*/

                                                List<Location> location = new ArrayList<>();
                                                location.clear();

                                                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                                                    /**
                                                     * This enhanced for loop "for(DataSnapshot postSnapshot : snapshot.getChildren())"
                                                     * iterates through the snapshot ("Test/Location/6-16-2032) children
                                                     * which in this case the child nodes with unique keys starting with -NYD...
                                                     *
                                                     * the postSnapshot in the enhanced for loop then is assigned with the snapshot.getChildren() values
                                                     * through testing with TextView.setText(); the postSnapshot will contain the unique children's sub-child
                                                     * values for example:
                                                     *          } -NYD...
                                                     *             } Latitude = 14.222... (Number Object)
                                                     *             } Longitude = 121.222... (Number Object)
                                                     *             } Time = "16:16:16" (String Object)
                                                     *  postSnapshot will then be equal to:
                                                     *      postSnapshot == {Latitude=14.222,Longitude=121.222,Time=16:16:16}
                                                     */
                                                    if(postSnapshot.exists()) {
                                                        Location model = postSnapshot.getValue(Location.class);

                                                        /**
                                                         * I've also added an if-else statement to determine if the postSnapshot has an existing value.
                                                         * if it exists then it will go through the Location Class
                                                         * which the mdoel class will be assigned with the values retrieved from the postSnapshot.
                                                         * Double Latitude = 14.222
                                                         * Double Longitude = 121.222
                                                         * String Time = "16:16:16"
                                                         */


                                                        String LongitudeStr = model.getLongitude(); //producing error!!
                                                        String LatitudeStr = model.getLatitude();
                                                        String Time = model.getTime();





                                                        /**
                                                         * the methods model.getLongitude, model.getLatitude, and model.getTime();
                                                         * retrieves the current assigned value in the Location class assigns it in the Double Longitude, Latitude, and String Time.
                                                         */

                                                        if (LongitudeStr == null || LatitudeStr == null || Time == null) {
                                                            /**
                                                             * This code then checks if the variables are null. If they are null then it will break; Which will then loop over again
                                                             * to the onDataChange. Else, it will convert the variables to Double and call the method addMarker and assign the following variables to the required parameters.
                                                             */

                                                            break;

                                                        } else  {

                                                            Double Longitude = Double.valueOf(LongitudeStr);
                                                            Double Latitude = Double.valueOf(LatitudeStr);

                                                            addMarker(Latitude, Longitude, Time, selectedDate);

                                                        }
//                                                Double Latitude = Double.parseDouble(LatitudeStr);
                                                    } else {
                                                        // Do Nothing
                                                    }
                                                }
                                            }




                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                });

                            }


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

        return view;
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

                CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 17.5f);
                googleMap.moveCamera(point);
                googleMap.animateCamera(point);
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