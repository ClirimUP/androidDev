package com.example.find_your_bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SellYourBike extends AppCompatActivity implements View.OnClickListener {

    private TextView  registerUser;
    private EditText editTextemail, editTextpassword;
    Button verifyEmailbtn;
    TextView verifyEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_your_bike);


        mAuth = FirebaseAuth.getInstance();

        verifyEmailbtn = findViewById(R.id.VerifyButton);
        editTextemail = (EditText)findViewById(R.id.emailSellit);
        editTextpassword = (EditText)findViewById(R.id.password);
        registerUser = (Button)findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        if (!mAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(SellYourBike.this,"Email is not verified!", Toast.LENGTH_LONG).show();
        }

        verifyEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SellYourBike.this,"Verification Email Sent!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.registerUser:
                    registerUser();
                    break;

            }
        }

        private void registerUser(){
            String email = editTextemail.getText().toString();
            String password = editTextpassword.getText().toString();

            if (email.isEmpty()){
                editTextemail.setError("Email is required");
                editTextemail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextemail.setError("Please provide a valid email");
                editTextemail.requestFocus();
                return;
            }
            if (password.isEmpty()){
                editTextpassword.setError("Password required");
                editTextpassword.requestFocus();
                return;
            }
            if (password.length() < 6){
                editTextpassword.setError("Password should be longer than 6 characters");
                editTextpassword.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        User user = new User(email, password);

                        FirebaseDatabase.getInstance().getReference("User")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SellYourBike.this,"Registered!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(SellYourBike.this,"Failed to register!", Toast.LENGTH_LONG).show();
                                    }
                            }
                        });
                    }else {
                        Toast.makeText(SellYourBike.this,"Failed to register!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}