package com.example.android_team.farmapp.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_team.farmapp.OfferModel;
import com.example.android_team.farmapp.OfferRecyclerAdapter;
import com.example.android_team.farmapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersFragment extends Fragment {

    private final String OFFERS_DB = "offer";


    //Firebase DB
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private ArrayList<OfferModel> offersList;
    private RecyclerView recyclerView;
    private OfferRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private ImageView noOffers;
    private Drawable noOfferIcon;
    private TextView noOfferTv;

    public OffersFragment() {
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
        View v = inflater.inflate(R.layout.fragment_offers, container, false);

        FirebaseApp.initializeApp(getActivity());
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();

        offersList = new ArrayList<>();

        callDatabae();

        recyclerView = v.findViewById(R.id.offers_rv);

        adapter = new OfferRecyclerAdapter(this.getActivity(), offersList);

        recyclerView.setAdapter(adapter);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        noOffers = v.findViewById(R.id.no_offers_iv);

        noOfferIcon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_info_large);

        noOfferIcon.setBounds(1, 0, noOfferIcon.getMinimumWidth(), noOfferIcon.getMinimumHeight());

        noOffers.setImageDrawable(noOfferIcon);

        noOfferTv = v.findViewById(R.id.no_offers_tv);

        return v;
    }


    private void callDatabae() {
        mRef.child(OFFERS_DB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Reset List of products
                offersList.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot db : dataSnapshot.getChildren()) {
            OfferModel model = db.getValue(OfferModel.class);
            offersList.add(model);
            adapter.notifyDataSetChanged();

            Log.i("Statuss", model.getImg_url());
        }

        if (offersList.isEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            noOffers.setVisibility(View.VISIBLE);
            noOfferTv.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noOffers.setVisibility(View.INVISIBLE);
            noOfferTv.setVisibility(View.INVISIBLE);
        }
    }


}
