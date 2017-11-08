package com.example.company.thenewsroom;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.rmiri.buttonloading.ButtonLoading;

public class LoginScreen extends AppCompatActivity {

    ButtonLoading btnSignIn,btnSignUp;
    TextView txtSlogan;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        txtSlogan = findViewById(R.id.txtSlogan);

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Canterbury.ttf");
        txtSlogan.setTypeface(face);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
