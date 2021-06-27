package com.example.find_your_bike;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class WinIT extends AppCompatActivity {

    ImageView wheeling;
    String[] parts = {"WIN", "LOST","WIN", "LOST","WIN", "LOST","WIN", "LOST","WIN", "LOST","WIN", "LOST"};
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_it);

        wheeling = findViewById(R.id.wheel);
        textView = findViewById(R.id.txtv);

        Collections.reverse(Arrays.asList(parts));

    }

    public void spinWheel(View view){

        Random rr = new Random();
       final int degree = rr.nextInt(360);
        RotateAnimation rotateAnimation = new RotateAnimation(0,degree + 720,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);

        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                CalculatePoint(degree);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheeling.startAnimation(rotateAnimation);
    }
    public void CalculatePoint(int degree){
        int initialPoint = 0;
        int endPoint = 30;
        int i = 0;
        String res = null;

        do{
            if (degree >initialPoint && degree < endPoint){
                res = parts[i];
            }
            initialPoint += 30; endPoint += 30;
            i++;
        }while (res == null);

        textView.setText(res);
    }
}