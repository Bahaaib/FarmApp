package com.example.android_team.farmapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_team.farmapp.ProductModel;
import com.example.android_team.farmapp.ProductRecyclerAdapter;
import com.example.android_team.farmapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FruitsFragment extends Fragment {

    private final String FRUITS_DB = "fruits";

    //Firebase DB
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private ArrayList<ProductModel> fruitsList;
    private RecyclerView recyclerView;
    private ProductRecyclerAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    public FruitsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fruits, container, false);

        FirebaseApp.initializeApp(getActivity());
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        fruitsList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.fruits_rv);

        //Retrieve from Firebase..
        callDatabae();

        //Passing the full list to the RecyclerView adapter to show them,
        // Passing the Activity context too letting the adapter know which Activity is calling in the whole App
        adapter = new ProductRecyclerAdapter(this.getActivity(), fruitsList);
        recyclerView.setAdapter(adapter);

        //Showing the RecyclerView Elements using the GridView Scheme, 2 Cards in each row, propagating vertically,
        //Wrapping all passed cards with no limit
        gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);


        return v;
    }

    private void callDatabae() {
        mRef.child(FRUITS_DB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Reset List of products
                fruitsList.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot db : dataSnapshot.getChildren()) {
            ProductModel model = db.getValue(ProductModel.class);
            fruitsList.add(model);
            adapter.notifyDataSetChanged();
            Log.i("Statuss", model.getName_ar() + " " + model.getAvailability());
        }
    }


}
