package com.example.smartmotobox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class thirdTab extends Fragment {

    //MAIN ACTIVITY OF RECYCLERVIEW

    TextView textView;
    MyAdapter myAdapter;
    FirebaseRecyclerOptions<Item> options;
    FirebaseRecyclerAdapter<Item, MyViewHolder> adapter;
    List<String> getAllParentDates = new ArrayList<>();
    List<String> getSpecificParentDate = new ArrayList<>();
    String selectedHistoryDate;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_tab, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<Item> item = new ArrayList<Item>();
//        myAdapter = new MyAdapter(getActivity().getApplicationContext(), item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(myAdapter);
        String myDate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        DatabaseReference setHistoryDataChange = FirebaseDatabase.getInstance().getReference("History");
        Spinner FirebaseDB_Spinner = view.findViewById(R.id.spinnerAlarmHistory);
        ArrayAdapter<String> spinnerAdapterAlarmHistory = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getAllParentDates);
        FirebaseDB_Spinner.setAdapter(spinnerAdapterAlarmHistory);

        DatabaseReference alarmHistory = FirebaseDatabase.getInstance().getReference().child("History/NewData");

        alarmHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    getAllParentDates.clear();

                    for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if(postSnapshot.exists()) {

                            String triggerDate = postSnapshot.getKey();
                            getAllParentDates.add(triggerDate);
                            spinnerAdapterAlarmHistory.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDB_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDate = FirebaseDB_Spinner.getItemAtPosition(i).toString();

                if (selectedDate == "--SELECT HISTORY--") {

                } else {
                    getAlarmHistory();
                    selectedHistoryDate = selectedDate;

                    DatabaseReference alarmHistorySelected = FirebaseDatabase.getInstance().getReference().child("History/NewData").child(selectedHistoryDate);

                    options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(alarmHistorySelected, Item.class).build();
                    adapter = new FirebaseRecyclerAdapter<Item, MyViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Item model) {
                            holder.notifTrigger.setText(""+model.getStatus());
                            holder.notifDate.setText(""+model.getDate());
                            holder.notifTime.setText(""+model.getTime());

                        }

                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
                            return new MyViewHolder(v);
                        }
                    };


                    adapter.startListening();
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        return view;
    }

    private void getAlarmHistory() {
        ArrayAdapter<String> spinnerAdapterAlarmHistory = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, getAllParentDates);
        DatabaseReference alarmHistory = FirebaseDatabase.getInstance().getReference().child("History/NewData");
        alarmHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    getAllParentDates.clear();
                    for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if(postSnapshot.exists()) {
                            String triggerDate = postSnapshot.getKey();
                            getAllParentDates.add(triggerDate);
                            spinnerAdapterAlarmHistory.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}