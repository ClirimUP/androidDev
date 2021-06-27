package com.example.find_your_bike;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUP extends AppCompatActivity {

    EditText username, password, fullName, email;
    Button signup;
    TextView logintext;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.Password);
        signup = (Button)findViewById(R.id.signUPbutton);
        logintext = this.findViewById(R.id.LoginSignup);
        DB = new DBHelper(this);

        logintext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
           }
        });

       signup.setOnClickListener(new View.OnClickListener() {
       @Override
           public void onClick(View view) {
           String user = username.getText().toString();
           String pass = password.getText().toString();


           if (user.equals("") || pass.equals(""))
               Toast.makeText(SignUP.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
           else {
               if (pass.length() > 4) {
                   Boolean checkuser = DB.checkusername(user);
                   if (checkuser == false) {
                       Boolean insert = DB.insertData(user, pass);
                       if (insert == true) {
                           Toast.makeText(SignUP.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(getApplicationContext(), HomePage.class);
                           startActivity(intent);
                       } else {
                           Toast.makeText(SignUP.this, "Registration failed", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Toast.makeText(SignUP.this, "User already exists, please Signin.", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(SignUP.this, "Password should be loonger than 4 charactrers!", Toast.LENGTH_SHORT).show();
               }
           }
       }
   });



        TextView btn = findViewById(R.id.LoginSignup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUP.this, MainActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;

    }
}