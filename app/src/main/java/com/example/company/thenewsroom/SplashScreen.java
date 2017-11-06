package com.example.company.thenewsroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jatin on 5/11/17.
 */

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Animation animation,animation1;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        textView = (TextView)findViewById(R.id.text);
        imageView = (ImageView)findViewById(R.id.imageView);

        animation = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        animation1 = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        textView.setAnimation(animation);
        imageView.setAnimation(animation1);

        intent = new Intent(this,MainActivity.class);

        Thread timer = new Thread(){
            public void run()
            {
                try{
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
