package com.example.company.thenewsroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.company.thenewsroom.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.rmiri.buttonloading.ButtonLoading;

public class SignIn extends AppCompatActivity {

    EditText username,password;
    ButtonLoading btnSignIn;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignIn.this,LoginScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        password = (MaterialEditText) findViewById(R.id.password);
        username = (MaterialEditText) findViewById(R.id.username);

        btnSignIn = findViewById(R.id.btnSignIn);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("user");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog= new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait!!!");

                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(username.getText().toString()).exists()) {
                            mDialog.dismiss();


                            User user = dataSnapshot.child(username.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(password.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign In Successful!!!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignIn.this, "Welcome "+user.getFirstname()+" "+user.getLastname(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this,MainActivity.class);
                                intent.putExtra("firstName",user.getFirstname());
                                intent.putExtra("lastName",user.getLastname());
                                intent.putExtra("password",user.getPassword());
                                intent.putExtra("userName",username.getText().toString());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
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
