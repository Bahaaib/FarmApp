package com.example.android_team.farmapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Assigning all used objects to their views
        tabLayout = findViewById(R.id.tab_layout);


        //Adding Three tabs to the screen
        tabLayout.addTab(tabLayout.newTab().setText(R.string.offers_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fruits_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.vege_tab));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Setting up the View Pager that allows flipping activity fragments horizontally like a page
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        //Set Viewpager initially to Vegetables tab
        viewPager.setCurrentItem(2);

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


    }



}
