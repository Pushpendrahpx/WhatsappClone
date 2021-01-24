package com.shareable.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1800);
        ImageView image= findViewById(R.id.imageView);
        image.setAnimation(fadeOut);

        // Very Important For Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreferences.getBoolean("SIGN_IN_STATUS", false)){
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
//                    Intent intent = new Intent(SplashActivity.this,CreateProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }




            }
        },SPLASH_SCREEN_TIMEOUT);

    }
}