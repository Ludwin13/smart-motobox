package com.example.smartmotobox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class thirdTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_tab, container, false);




        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<Item> item = new ArrayList<Item>();

        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));
        item.add(new Item("014303032023", "Tilt Activated", "03-03-2023", "01:43:07"));



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(getActivity().getApplicationContext(), item));




        return view;
    }
}