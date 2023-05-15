package com.example.gymquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 2500;

    TextView appName;
    ImageView appTitle;
    TextView developedByLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appName = findViewById(R.id.appName);
        appTitle = findViewById(R.id.appTitle);
        developedByLine = findViewById(R.id.developedByLine);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeInAnimation.setDuration(1000);

        appTitle.setAnimation(getDelayAnimation(fadeInAnimation, 0));
        appName.setAnimation(getDelayAnimation(fadeInAnimation, 500));
        developedByLine.setAnimation(getDelayAnimation(fadeInAnimation, 1000));

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fadeInAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIMER);
    }

    private Animation getDelayAnimation(Animation animation, int delay) {
        animation.setStartOffset(delay);
        return animation;
    }
}
