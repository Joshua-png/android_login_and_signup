package com.example.refresher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 2000;

    //Animation Variables
    Animation topAnimation,bottomAnimation;
    ImageView logo;
    TextView deliveries, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes the bottom bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        deliveries = findViewById(R.id.deliveries);
        slogan = findViewById(R.id.slogan);

        logo.setAnimation(topAnimation);
        deliveries.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        //Handling delay process
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
            //when the user presses the back button it shouldn't go back to the splash screen
            finish();
        }, SPLASH_SCREEN);

    }
}
