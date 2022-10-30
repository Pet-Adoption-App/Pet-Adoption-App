package com.company.petadoptionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class LostPetFragment extends Fragment {
    RecyclerView recyclerView;
    Lost_Pet_Adapter mainAdapter;

    public LostPetFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lost_pet, container, false);
        recyclerView=view.findViewById(R.id.rv_lost_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Pet_Model> options =
                new FirebaseRecyclerOptions.Builder<Pet_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lost_Approved_req"), Pet_Model.class)
                        .build();

        mainAdapter= new Lost_Pet_Adapter(options);
        recyclerView.setAdapter(mainAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}