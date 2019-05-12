package com.ets.android_team.farmApp;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ets.android_team.farmApp.Dialogs.CounterDialog;
import com.ets.android_team.farmApp.Dialogs.InfoDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private final String INFO_TAG = "infoDialog";
    private final String COUNTER_TAG = "infoDialog";
    private static String DEVICE_ID;
    private final String DEVICES_DB = "devices";


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DialogFragment infoDialog;
    private DialogFragment counterDialog;
    private Button infoBtn;
    private Button counterBtn;
    //Firebase DB
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private ArrayList<String> devicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseMessaging.getInstance().subscribeToTopic("messages");

        //Firebase DB
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference();
        devicesList = new ArrayList<>();

        //Fetching Device identifier ID
        DEVICE_ID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        callDevicesDatabase();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Assigning all used objects to their views
        tabLayout = findViewById(R.id.tab_layout);


        //Adding Three tabs to the screen
        tabLayout.addTab(tabLayout.newTab().setText(R.string.vege_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fruits_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.offers_tab));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Setting up the View Pager that allows flipping activity fragments horizontally like a page
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Pop up info dialog
        infoBtn = findViewById(R.id.info_btn);
        infoDialog = new InfoDialog();


        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog();
            }
        });

        //Pop up info dialog
        counterBtn = findViewById(R.id.counter_btn);
        counterDialog = new CounterDialog();
        counterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCounterDialog();
            }
        });


    }

    void showInfoDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(INFO_TAG);
        if (prev != null) {
            transaction.remove(prev);
        }

        transaction.add(infoDialog, INFO_TAG).commit();

    }

    void showCounterDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(COUNTER_TAG);
        if (prev != null) {
            transaction.remove(prev);
        }

        transaction.add(counterDialog, COUNTER_TAG).commit();

    }


    private void callDevicesDatabase() {
        mRef.child(DEVICES_DB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Reset List of products
                devicesList.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        for (DataSnapshot db : dataSnapshot.getChildren()) {
            String device = db.getKey();
            devicesList.add(device);
        }

        if (!deviceIsRegistered()) {
            registerDevice();
        }
    }

    private boolean deviceIsRegistered() {
        for (String deviceId : devicesList) {
            if (deviceId.equals(DEVICE_ID)) {
                return true;
            }
        }

        return false;
    }

    //Push device ID to DB
    private void registerDevice() {
        mRef.child(DEVICES_DB).child(DEVICE_ID).setValue(0);
    }


}
