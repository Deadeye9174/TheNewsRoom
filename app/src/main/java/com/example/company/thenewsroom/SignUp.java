package com.example.company.thenewsroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.company.thenewsroom.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.rmiri.buttonloading.ButtonLoading;

public class SignUp extends AppCompatActivity {

    MaterialEditText firstname,lastname,username,password,phone;
    ButtonLoading btnSignUp;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone =  findViewById(R.id.phonenumber);
        password =  findViewById(R.id.password);
        username = findViewById(R.id.username);

        btnSignUp = findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog= new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait!!!");

                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Username number already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User(firstname.getText().toString(),lastname.getText().toString(),phone.getText().toString(),password.getText().toString());
                            table_user.child(username.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up successful!!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this,SignIn.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
