package com.example.smartmotobox;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class MapFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private Button limitMarkers_Btn, addMarkerTest_Btn;
    private TextView Test;
    private static int markerCountLimit = 0;
    private Marker mMarker;
    private static String mSelectedDate;
    private static int gpsListSize;
    private static int gpsmarkerlist_test = 0;
    private static boolean firstStartup = true;
    private static int ListTime2_oldSize = 0;
    private static int ListTime2_Size = 0;
    private static String currentDate;
    List<Marker> markers = new ArrayList<>();
    boolean isConnectedto;

/**
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

**/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Spinner FirebaseDB_Spinner = view.findViewById(R.id.spinner);
        List<String> getSpecificParentDate = new ArrayList<>();
        List<String> getAllParentDates = new ArrayList<>();
        Test = view.findViewById(R.id.Test);
        List<String> ListTime = new ArrayList<>();
        List<Double> ListLatitude = new ArrayList<>();
        List<Double> ListLongitude = new ArrayList<>();
        List<String> ListDates = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        limitMarkers_Btn = view.findViewById(R.id.limitMarkers_Btn);
//        addMarkerTest_Btn = view.findViewById(R.id.addMarkerTestBtn);



        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getAllParentDates);
        FirebaseDB_Spinner.setAdapter(adapter);

        /** Firebase Realtime Database Reference Initialization....
         * For Main GPS, Tracks onDataChange wherever the child is.
         * Without manual interference in the database then it will only get the latest data write. */
        DatabaseReference Firebase_GPSDataChange = FirebaseDatabase.getInstance().getReference().child("Location");
        /** For Spinner content (GPS History) */
        DatabaseReference FirebaseDB_GPSDate = FirebaseDatabase.getInstance().getReference().child("Location");
        /** Google Maps API Initialization */
        SupportMapFragment supportMapFragmentInitialization = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        isConnected();
//        addMarkerTest_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//
//                if (supportMapFragment != null) {
//                    supportMapFragment.getMapAsync(googleMap -> {
//
//                        if (gpsmarkerlist_test == 0) {
//                            googleMap.clear();
//                            gpsmarkerlist_test++;
//                            LatLng marker2 = new LatLng(014.59303, 121.101510);
//                            googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
//                            String snippet = "Latitude: " + 014.59303 + "\n" + "Longitude: " + 121.101501;
//                            mMarker = googleMap.addMarker(new MarkerOptions()
//                                    .position(marker2)
//                                    .title("Marker1")
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                                    .snippet(snippet));
//                            CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(014.59303, 121.101510), 17.5f);
//                            googleMap.moveCamera(point);
//                            return;
//
//                        }
//
//                        if (gpsmarkerlist_test == 1){
//                            gpsmarkerlist_test++;
//                        LatLng marker2 = new LatLng(014.592818, 121.10162);
//                        googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
//                        String snippet = "Latitude: " + 014.592818 + "\n" + "Longitude: " + 121.101620;
//                        mMarker = googleMap.addMarker(new MarkerOptions()
//                                .position(marker2)
//                                .title("Marker2")
//                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                                .snippet(snippet));
//                        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(014.592818, 121.101620), 17.5f);
//                        googleMap.moveCamera(point);
//                            return;
//                        }
//
//                        if (gpsmarkerlist_test == 2) {
//                            gpsmarkerlist_test = 0;
//                            LatLng marker2 = new LatLng(014.593388, 121.101590);
//                            googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
//                            String snippet = "Latitude: " + 014.593388 + "\n" + "Longitude: " + 121.101590;
//                            mMarker = googleMap.addMarker(new MarkerOptions()
//                                    .position(marker2)
//                                    .title("Marker3")
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                                    .snippet(snippet));
//                            CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(014.593388, 121.101590), 17.5f);
//                            googleMap.moveCamera(point);
//                            return;
//                        }
//
//            });
//
//                }
//            }
//        });
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
                    ListTime.clear();
                    getSpecificParentDate.clear();
                    if(snapshot.exists()) {
                        /**
                         * postSnapshot.getKey() retrieves the postSnapshot children names (Date) e.g. (6-16-2023);
                         * imported SimpleDateFormat to determine the current Date.
                         * The main idea is:
                         *      If the currentDate is equal to the latest postSnapshot children name with the same date
                         *      then it will create a query pointing to that specific Date Node and retrieve the Marker Data.
                         */
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            String parent = childSnapshot.getKey();
                            ListDates.add(convertDatev2(parent));

                            String myDate = java.text.DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().getTime());
                            //Test.setText(convertDateFormatv2(parent) + " | " + myDate);
                            for(String s : ListDates) {
                                if (myDate.equals(s)) {
                                    if (s != null) {
                                        DatabaseReference Firebase_GPSCurrentDate = FirebaseDB_GPSDate.child(convertDateFormatv2(s));
                                        Firebase_GPSCurrentDate.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                /**
                                                 * at this point the query points to the child node with the same name as the value of the currentDate variable.
                                                 * So snapshot will display only the date name (Parent).
                                                 * DataSnapshot snapshot = Dates (6-16-2023, 6-17-2023) etc.
                                                 */
                                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                                                    /**
                                                     * Need an enhanced for loop to increment through the snapshot (Parent Node/Date).
                                                     * This will retrieve children of the Date nodes e.g. nodes that start with "-NDY..."
                                                     */
                                                    Location model = postSnapshot.getValue(Location.class);
                                                    /**
                                                     *
                                                     */
                                                    assert model != null;
                                                    String LatitudeStr = model.getLatitude();
                                                    String LongitudeStr = model.getLongitude();
                                                    String Time = model.getTime();
                                                    //Test.setText(convertDateFormatv2(s) + " | " + myDate);


                                                    if (LatitudeStr == null || LongitudeStr == null || Time == null) {

                                                        break;

                                                    } else {

                                                        Double Latitude = Double.valueOf(LatitudeStr);
                                                        Double Longitude = Double.valueOf(LongitudeStr);

//                                                ListTime2.add(Time);
//
//                                                 int ListTime2_oldSize = ListTime2.size();
//
//                                                 if(ListTime2_Size != ListTime2_oldSize) {
//                                                 addMarker(Latitude, Longitude, Time, convertDate(parent));
//                                                    ListTime2_Size = ListTime2_oldSize;
//                                                 }
                                                        addMarker(Latitude, Longitude, Time, s);
                                                        int pos = getAllParentDates.indexOf(myDate);
                                                        FirebaseDB_Spinner.setSelection(pos);



                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }
                            }

                        }





                    }
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



//
//                        Test.setText(substringYear + "|" + substringMonth + "|" + substringDay);
                        getAllParentDates.add(convertDatev2(parent));
                        adapter.notifyDataSetChanged();
                        /**
                         * Need to create a code to zoom in on the newest received location from the Database
                         * Problem is the app crashes whenever new data is inserted to the database.
                         * Maybe use SQLite? It probably won't still fix the issue since new inserted data technically doesn't exist yet (null).
                         * Need to refresh the app to successfully read it.
                         * Maybe an app refresher?
                         */
                        String myDate = java.text.DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().getTime());


                        int pos = getAllParentDates.indexOf(myDate);
                        FirebaseDB_Spinner.setSelection(pos);

                        FirebaseDB_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String selectedDate = FirebaseDB_Spinner.getItemAtPosition(i).toString();
                                currentDate = FirebaseDB_Spinner.getItemAtPosition(i).toString();
//                                Test.setText(convertDateFormatv2(selectedDate));
                                gpsListSize = 0;

                                if (selectedDate.equals("--SELECT HISTORY--")) {
                                    if (supportMapFragmentInitialization != null) {
                                        supportMapFragmentInitialization.getMapAsync(googleMap -> {
                                            googleMap.clear();
                                        });
                                    }

                                } else {
                                    //String date = dateFormat.format(Calendar.getInstance().getTime());
                                    //Test.setText(convertDateFormat(date));
                                    if (supportMapFragmentInitialization != null) {
                                        supportMapFragmentInitialization.getMapAsync(googleMap -> {
                                            googleMap.clear();
                                            DatabaseReference FirebaseDB_GPSDateMarkerData = FirebaseDB_GPSDate.child(convertDateFormatv2(selectedDate));

                                            FirebaseDB_GPSDateMarkerData.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                    ListTime.clear();
                                                    ListLongitude.clear();
                                                    ListLatitude.clear();
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



                                                    for(DataSnapshot postSnapshot1 : snapshot1.getChildren()) {
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
                                                        if(postSnapshot1.exists()) {
                                                            Location model = postSnapshot1.getValue(Location.class);

                                                            /**
                                                             * I've also added an if-else statement to determine if the postSnapshot has an existing value.
                                                             * if it exists then it will go through the Location Class
                                                             * which the mdoel class will be assigned with the values retrieved from the postSnapshot.
                                                             * Double Latitude = 14.222
                                                             * Double Longitude = 121.222
                                                             * String Time = "16:16:16"
                                                             */


                                                            assert model != null;
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
//
                                                                ListTime.add(Time);
                                                                ListLongitude.add(Longitude);
                                                                ListLatitude.add(Latitude);
                                                                mSelectedDate = selectedDate;
                                                                gpsListSize = ListTime.size();
//                                                                markerCountSize = ListTime.size();
//                                                                finalMarkerCounter = markerCountSize - markerCountLimit;
//                                                                Test.setText(String.valueOf(ListLongitude)); addMarker(Latitude, Longitude, Time, myDate);
//                                                                addMarkerv2(Latitude, Longitude, Time, selectedDate, ListTime.size());

                                                            }
                                                        }

                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        });
                                    }
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        firebaseDatabase = FirebaseDatabase.getInstance();

        limitMarkers_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myDate = java.text.DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().getTime());
                Test.setText(myDate + " " + currentDate);
                if (myDate.equals(currentDate)) {
                    Toast.makeText(getActivity(), "Cannot limit markers on current date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int MarkersCount = 0;
                    final String postTitle = "Set Marker Limit Display";

                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);

                    final EditText etMarkerLimit = new EditText(getContext());
                    etMarkerLimit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etMarkerLimit.setHint(gpsListSize+" stored Markers on "+mSelectedDate);
                    layoutName.addView(etMarkerLimit);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(layoutName);
                    builder.setTitle("Set Marker Limit Display");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    markerCountLimit = Integer.parseInt(etMarkerLimit.getText().toString());
                                    Collections.reverse(ListTime);
                                    Collections.reverse(ListLatitude);
                                    Collections.reverse(ListLongitude);
                                    if (markerCountLimit > ListTime.size()) {
                                        Toast.makeText(getActivity(), "Set limit exceeds stored marker count", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        for (int i = 0; i < markerCountLimit; i++) {
                                            addMarkerv2(ListLatitude.get(i), ListLongitude.get(i), ListTime.get(i), mSelectedDate);
                                        }
                                    }


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

            }
        });
        initSupportMapFragment();
        return view;
    }

    private String convertDateFormat(String selectedDate) {

        if (selectedDate.contains("January")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("January", "1") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("January", "1") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("February")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("February", "2") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("February", "2") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("March")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("March", "3") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("March", "3") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("April")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("April", "4") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("April", "4") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("May")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("May", "5") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("May", "5") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("June")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("June", "6") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("June", "6") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("July")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("July", "7") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("July", "7") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("August")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("August", "8") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("August", "8") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("September")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("September", "9") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("September", "9") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("October")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("October", "10") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("October", "10") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("November")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("November", "11") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("November", "11") + "-" +selectedDate.substring(0,4);
            }
        } else if (selectedDate.contains("December")) {
            if (selectedDate.substring(6,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(8).replace("December", "12") + "-" +selectedDate.substring(0,4);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(9).replace("December", "12") + "-" +selectedDate.substring(0,4);
            }
        }

        return null;
    }

    private String convertDateFormatv2(String selectedDate) {
        //January #, #### = 14 | Month(0, 7) | Day(8, 9) | Year(11)
        //February #, #### = 15 | Month(0, 8) | Day(9, 10) | Year(12)
        //March #, #### = 12 | Month(0, 5) | Day(6, 7) | Year(9)
        //April #, #### = 12 | Month(0, 5) | Day(6, 7) | Year(9)
        //May #, #### = 10 | Month(0, 3) | Day(4, 5) | Year(7)
        //June #, #### = 11 | Month(0, 4) | Day(5, 6) | Year(8)
        //July #, #### = 11 | Month(0, 4) | Day(5, 6) | Year(8)
        //August #, #### = 13 | Month(0, 6) | Day(7, 8) | Year(10)
        //September #, #### = 16 | Month(0, 9) | Day(10, 11) | Year(13)
        //October #, #### = 14 | Month(0, 7) | Day(8, 9) | Year(11)
        //November #, #### = 15 | Month(0, 8) | Day(9, 10) | Year (12)
        //December #, #### = 15 | Month(0, 8) | Day(9, 10) | Year (12)

        //January ##, #### = 15 | Month(0, 7) | Day(8, 10) | Year(12)
        //February ##, #### = 16 | Month(0, 8) | Day(9, 11) | Year(13)
        //March ##, #### = 13 | Month(0, 5) | Day(6, 8) | Year(10)
        //April ##, #### = 13 | Month(0, 5) | Day(6, 8) | Year(10)
        //May ##, #### = 11 | Month(0, 3) | Day(4, 6) | Year(8)
        //June ##, #### = 12 | Month(0, 4) | Day(5, 7) | Year(9)
        //July ##, #### = 12 | Month(0, 4) | Day(5, 7) | Year(9)
        //August ##, #### = 14 | Month(0, 6) | Day(7, 9) | Year(11)
        //September ##, #### = 17 | Month(0, 9) | Day(10, 12) | Year(14)
        //October ##, #### = 15 | Month(0, 7) | Day(8, 10) | Year(12)
        //November ##, #### = 16 | Month(0, 8) | Day(9, 11) | Year (13)
        //December ##, #### = 16 | Month(0, 8) | Day(9, 11) | Year (13)



        if (selectedDate.contains("January")) {
            if (selectedDate.substring(10, 11).contains(" ")) {
                //day-month-year
                return selectedDate.substring(8, 9) + "-" + selectedDate.substring(0, 7).replace("January", "1") + "-" +selectedDate.substring(11);
            } else {
                return selectedDate.substring(8, 10) + "-" + selectedDate.substring(0, 7).replace("January", "1") + "-" +selectedDate.substring(12);
            }
        } else if (selectedDate.contains("February")) {
            if (selectedDate.substring(11, 12).contains(" ")) {
                //day-month-year
                return selectedDate.substring(9, 10) + "-" + selectedDate.substring(0, 8).replace("February", "2") + "-" +selectedDate.substring(12);
            } else {
                return selectedDate.substring(9, 11) + "-" + selectedDate.substring(0, 8).replace("February", "2") + "-" +selectedDate.substring(13);
            }
        } else if (selectedDate.contains("March")) {
            if (selectedDate.substring(8, 9).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(0, 5).replace("March", "3") + "-" +selectedDate.substring(9);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(0, 5).replace("March", "3") + "-" +selectedDate.substring(10);
            }
        } else if (selectedDate.contains("April")) {
            if (selectedDate.substring(8, 9).contains(" ")) {
                //day-month-year
                return selectedDate.substring(6,7) + "-" + selectedDate.substring(0, 5).replace("April", "4") + "-" +selectedDate.substring(9);
            } else {
                return selectedDate.substring(6,8) + "-" + selectedDate.substring(0, 5).replace("April", "4") + "-" +selectedDate.substring(10);
            }
        } else if (selectedDate.contains("May")) {
            if (selectedDate.substring(6,7).contains(" ")) {
                //day-month-year
                return selectedDate.substring(4, 5) + "-" + selectedDate.substring(0, 3).replace("May", "5") + "-" +selectedDate.substring(7);
            } else {
                return selectedDate.substring(4, 6) + "-" + selectedDate.substring(0, 3).replace("May", "5") + "-" +selectedDate.substring(8);
            }
        } else if (selectedDate.contains("June")) {
            if (selectedDate.substring(7,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(5, 6) + "-" + selectedDate.substring(0, 4).replace("June", "6") + "-" +selectedDate.substring(8);
            } else {
                return selectedDate.substring(5, 7) + "-" + selectedDate.substring(0, 4).replace("June", "6") + "-" +selectedDate.substring(9);
            }
        } else if (selectedDate.contains("July")) {
            if (selectedDate.substring(7,8).contains(" ")) {
                //day-month-year
                return selectedDate.substring(5, 6) + "-" + selectedDate.substring(0, 4).replace("July", "7") + "-" +selectedDate.substring(8);
            } else {
                return selectedDate.substring(5, 7) + "-" + selectedDate.substring(0, 4).replace("July", "7") + "-" +selectedDate.substring(9);
            }
        } else if (selectedDate.contains("August")) {
            if (selectedDate.substring(8, 9).contains(" ")) {
                //day-month-year
                return selectedDate.substring(7, 8) + "-" + selectedDate.substring(0, 6).replace("August", "8") + "-" +selectedDate.substring(10);
            } else {
                return selectedDate.substring(7, 9) + "-" + selectedDate.substring(9).replace("August", "8") + "-" +selectedDate.substring(13);
            }
        } else if (selectedDate.contains("September")) {
            if (selectedDate.substring(12, 13).contains(" ")) {
                //day-month-year
                return selectedDate.substring(10, 11) + "-" + selectedDate.substring(0, 9).replace("September", "9") + "-" +selectedDate.substring(13);
            } else {
                return selectedDate.substring(10, 12) + "-" + selectedDate.substring(0, 9).replace("September", "9") + "-" +selectedDate.substring(14);
            }
        } else if (selectedDate.contains("October")) {
            if (selectedDate.substring(10, 11).contains(" ")) {
                //day-month-year
                return selectedDate.substring(8, 9) + "-" + selectedDate.substring(0, 7).replace("October", "10") + "-" +selectedDate.substring(11);
            } else {
                return selectedDate.substring(8, 10) + "-" + selectedDate.substring(0, 7).replace("October", "10") + "-" +selectedDate.substring(12);
            }
        } else if (selectedDate.contains("November")) {
            if (selectedDate.substring(11, 12).contains(" ")) {
                //day-month-year
                return selectedDate.substring(9, 10) + "-" + selectedDate.substring(0, 8).replace("November", "11") + "-" +selectedDate.substring(12);
            } else {
                return selectedDate.substring(9, 11) + "-" + selectedDate.substring(0, 8).replace("November", "11") + "-" +selectedDate.substring(13);
            }
        } else if (selectedDate.contains("December")) {
            if (selectedDate.substring(11, 12).contains(" ")) {
                //day-month-year
                return selectedDate.substring(9, 10) + "-" + selectedDate.substring(0, 8).replace("December", "12") + "-" +selectedDate.substring(12);
            } else {
                return selectedDate.substring(9, 11) + "-" + selectedDate.substring(0, 8).replace("December", "12") + "-" +selectedDate.substring(13);
            }
        }

        return null;
    }

    private String convertDate(String parent) {
        int parentLength = parent.length();
        String substringDay = "";
        String substringMonth = "";
        String substringYear = "";
        String convertedDate = "";

        if (parentLength == 10) {
            substringDay = parent.substring(0, 2);
            substringMonth = parent.substring(3, 5);
            substringYear = parent.substring(6);
            // 10-12-2024 = 10

            if (substringMonth.equals("10")) {
                return substringYear + ", " + substringDay + " " + substringMonth.replace("10", "October");
            } else if (substringMonth.equals("11")) {
                return substringYear + ", " + substringDay + " " + substringMonth.replace("11", "November");
            } else if (substringMonth.equals("12")) {
                return substringYear + ", " + substringDay + " " + substringMonth.replace("12", "December");
            }

        }

        if (parentLength == 9) {

            substringDay = parent.substring(0, 2);
            substringMonth = parent.substring(3, 5);
            substringYear = parent.substring(5);


            if (substringDay.contains("-")) {
                //1-12-2024
                substringDay = parent.substring(0, 1);
                substringMonth = parent.substring(2, 4);

                if (substringMonth.equals("10")) {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("10", "October");
                } else if (substringMonth.equals("11")) {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("11", "November");
                } else if (substringMonth.equals("12")) {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("12", "December");
                }

            }

            if (substringMonth.contains("-")) {
                //10-1-2024
                substringMonth = parent.substring(3, 4);

                if (substringMonth.charAt(0) == '1') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("1", "January");
                } else if (substringMonth.charAt(0) == '2') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("2", "February");
                } else if (substringMonth.charAt(0) == '3') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("3", "March");
                } else if (substringMonth.charAt(0) == '4') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("4", "April");
                } else if (substringMonth.charAt(0) == '5') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("5", "May");
                } else if (substringMonth.charAt(0) == '6') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("6", "June");
                } else if (substringMonth.charAt(0) == '7') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("7", "July");
                } else if (substringMonth.charAt(0) == '8') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("8", "August");
                } else if (substringMonth.charAt(0) == '9') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("9", "September");
                }
            }

//            } else {
//                substringMonth = parent.substring(3, 5);
//                substringYear = parent.substring(5);
//
//                if (substringMonth.equals("10")) {
//                    substringMonth.replace("10", "October");
//                } else if (substringMonth.equals("11")) {
//                    substringMonth.replace("11", "November");
//                } else if (substringMonth.equals("12")) {
//                    substringMonth.replace("12", "December");
//                }
//            }

            }

            if (parent.length() == 8) {
                substringDay = parent.substring(0, 1);
                substringMonth = parent.substring(2, 3);
                substringYear = parent.substring(4);
                // 1-1-2024 = 8

                if (substringMonth.charAt(0) == '1') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("1", "January");
                } else if (substringMonth.charAt(0) == '2') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("2", "February");
                } else if (substringMonth.charAt(0) == '3') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("3", "March");
                } else if (substringMonth.charAt(0) == '4') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("4", "April");
                } else if (substringMonth.charAt(0) == '5') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("5", "May");
                } else if (substringMonth.charAt(0) == '6') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("6", "June");
                } else if (substringMonth.charAt(0) == '7') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("7", "July");
                } else if (substringMonth.charAt(0) == '8') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("8", "August");
                } else if (substringMonth.charAt(2) == '9') {
                    return substringYear + ", " + substringDay + " " + substringMonth.replace("9", "September");
                }

            }

            if (parent.equals("--SELECT HISTORY--")) {
                return parent;
            }

        convertedDate = substringYear + ", " + substringDay + " " + substringMonth;
        return convertedDate;
        }

    private String convertDatev2(String parent) {
        int parentLength = parent.length();
        String substringDay = "";
        String substringMonth = "";
        String substringYear = "";
        String convertedDate = "";

        if (parentLength == 10) {
            substringDay = parent.substring(0, 2);
            substringMonth = parent.substring(3, 5);
            substringYear = parent.substring(6);
            // 10-12-2024 = 10

            if (substringMonth.equals("10")) {
                return substringMonth.replace("10", "October") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.equals("11")) {
                return substringMonth.replace("11", "November") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.equals("12")) {
                return substringMonth.replace("12", "December") + " " +  substringDay + ", " + substringYear;
            }

        }

        if (parentLength == 9) {

            substringDay = parent.substring(0, 2);
            substringMonth = parent.substring(3, 5);
            substringYear = parent.substring(5);


            if (substringDay.contains("-")) {
                //1-12-2024
                substringDay = parent.substring(0, 1);
                substringMonth = parent.substring(2, 4);

                if (substringMonth.equals("10")) {
                    return substringMonth.replace("10", "October") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.equals("11")) {
                    return substringMonth.replace("11", "November") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.equals("12")) {
                    return substringMonth.replace("12", "December") + " " + substringDay + ", " + substringYear;
                }

            }

            if (substringMonth.contains("-")) {
                //10-1-2024
                substringMonth = parent.substring(3, 4);

                if (substringMonth.charAt(0) == '1') {
                    return substringMonth.replace("1", "January") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '2') {
                    return substringMonth.replace("2", "February") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '3') {
                    return substringMonth.replace("3", "March") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '4') {
                    return substringMonth.replace("4", "April") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '5') {
                    return substringMonth.replace("5", "May") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '6') {
                    return substringMonth.replace("6", "June") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '7') {
                    return substringMonth.replace("7", "July") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '8') {
                    return substringMonth.replace("8", "August") + " " + substringDay + ", " + substringYear;
                } else if (substringMonth.charAt(0) == '9') {
                    return substringMonth.replace("9", "September") + " " + substringDay + ", " + substringYear;
                }
            }

//            } else {
//                substringMonth = parent.substring(3, 5);
//                substringYear = parent.substring(5);
//
//                if (substringMonth.equals("10")) {
//                    substringMonth.replace("10", "October");
//                } else if (substringMonth.equals("11")) {
//                    substringMonth.replace("11", "November");
//                } else if (substringMonth.equals("12")) {
//                    substringMonth.replace("12", "December");
//                }
//            }

        }

        if (parent.length() == 8) {
            substringDay = parent.substring(0, 1);
            substringMonth = parent.substring(2, 3);
            substringYear = parent.substring(4);
            // 1-1-2024 = 8

            if (substringMonth.charAt(0) == '1') {
                return substringMonth.replace("1", "January") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '2') {
                return substringMonth.replace("2", "February") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '3') {
                return substringMonth.replace("3", "March") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '4') {
                return substringMonth.replace("4", "April") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '5') {
                return substringMonth.replace("5", "May") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '6') {
                return substringMonth.replace("6", "June") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '7') {
                return substringMonth.replace("7", "July") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '8') {
                return substringMonth.replace("8", "August") + " " + substringDay + ", " + substringYear;
            } else if (substringMonth.charAt(0) == '9') {
                return substringMonth.replace("9", "September") + " " + substringDay + ", " + substringYear;
            }

        }

        if (parent.equals("--SELECT HISTORY--")) {
            return parent;
        }

        convertedDate = substringMonth + " " + substringDay + "," + substringYear;
        return convertedDate;
    }

    private void addMarkerv2(Double Latitude, Double Longitude, String Time, String selectedDate) {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {

                        LatLng marker2 = new LatLng(Latitude, Longitude);
                        googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
                        String snippet = "Latitude: " + Latitude + "\n" + "Longitude: " + Longitude + "\n" + "Time: " + Time;
                        mMarker = googleMap.addMarker(new MarkerOptions()
                                .position(marker2)
                                .title("" + selectedDate)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                                .snippet(snippet));
                        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 17.5f);
                        googleMap.moveCamera(point);
            });

        }
    }

//    private String convertMilitarytoStandard(String time) {
//        String militaryTime  = time.substring(0, 3);
//        String standardTime = null;
//        String AMorPM = null;
//        if (militaryTime == "00") {
//            standardTime = "12";
//            AMorPM = "PM";
//        } else if (militaryTime == "13") {   standardTime = "01"; AMorPM = "PM"; }
//        else if (militaryTime == "14") {   standardTime = "02"; AMorPM = "PM";}
//        else if (militaryTime == "15") {   standardTime = "03"; AMorPM = "PM";}
//        else if (militaryTime == "16") {   standardTime = "04"; AMorPM = "PM";}
//        else if (militaryTime == "17") {   standardTime = "05"; AMorPM = "PM";}
//        else if (militaryTime == "18") {   standardTime = "06"; AMorPM = "PM";}
//        else if (militaryTime == "19") {   standardTime = "07"; AMorPM = "PM";}
//        else if (militaryTime == "20") {   standardTime = "08"; AMorPM = "PM";}
//        else if (militaryTime == "21") {   standardTime = "09"; AMorPM = "PM";}
//        else if (militaryTime == "22") {   standardTime = "10"; AMorPM = "PM";}
//        else if (militaryTime == "23") {   standardTime = "11"; AMorPM = "PM";}
//        else if (militaryTime == "24") {   standardTime = "12"; AMorPM = "AM";}
//
//        return standardTime + "|" +AMorPM;
//    }


    private void addMarker(Double Latitude, Double Longitude, String Time, String selectedDate) {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {

                    LatLng marker2 = new LatLng(Latitude, Longitude);
                    googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
                    String snippet1 = "Latitude: " + Latitude + "\n" + "Longitude: " + Longitude + "\n" + "Time: " + Time;
                    mMarker = googleMap.addMarker(new MarkerOptions()
                            .position(marker2)
                            .title("" + selectedDate)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .snippet(snippet1));
                    CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 17.5f);
                    googleMap.moveCamera(point);
                    googleMap.animateCamera(point);
                    markers.add(mMarker);

                    if (markers.size() >= 4) {
                        markers.get(0).remove();
                        markers.remove(0);
                    }

            });
            }
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

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(googleMap -> {
                //Initialize variables for LatLng using the last detected location in firebase realtime database.
                CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(014.576603, 121.101320), 15f);
                googleMap.moveCamera(point);
                googleMap.animateCamera(point);

                LatLng marker1 = new LatLng(014.576603, 121.101320);
                if (getActivity() != null) {
                    googleMap.setInfoWindowAdapter(new customInfoWindowAdapter(getActivity()));
                }
                String snippet = "Latitude: 014.576603\n" + "Longitude: 121.101320\n" + "Time: 05:03:10";
                googleMap.addMarker(new MarkerOptions()
                        .position(marker1)
                        .title("Marker1")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet(snippet));
            });
        }

    }

    private void isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        isConnectedto = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }


        // Need to retrieve gps data (/Location/Sub-Children) from  Firebase Realtime Database.
        // Create a clickable list containing the saved gps date from the database.
            // Upon selecting a date, display the history by adding all markers inside the Google Maps Fragment.
        // Will utilize map fragments
}