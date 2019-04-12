package com.ets.android_team.farmApp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.MobileAds;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_TIMER = 2500;
    private Handler timerHandler;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        MobileAds.initialize(this, "ca-app-pub-6702076183097498~9274122854");

        startSplashAnimations();

        timerHandler = new Handler();

        timerHandler.postDelayed(timerRunnable = new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIMER);
    }

    private void startSplashAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        RelativeLayout splashLayout = findViewById(R.id.splash_layout);
        splashLayout.clearAnimation();
        splashLayout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView appLogo = findViewById(R.id.app_logo);
        appLogo.clearAnimation();
        appLogo.startAnimation(anim);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Destroy Handler and de-attach occupied thread..
        timerHandler.removeCallbacks(timerRunnable);

    }
}
