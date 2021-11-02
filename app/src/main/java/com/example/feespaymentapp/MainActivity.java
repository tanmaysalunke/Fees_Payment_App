package com.example.feespaymentapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email_txt, pwd_txt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        email_txt = findViewById(R.id.email_txt);
        pwd_txt = findViewById(R.id.pwd_txt);

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(this);

        mAuth= FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email= email_txt.getText().toString().trim();
        String password= pwd_txt.getText().toString().trim();

        if(email.isEmpty()){
            email_txt.setError("Username is required!");
            email_txt.requestFocus();
        }
        if(password.isEmpty()){
            pwd_txt.setError("Password is required!");
            pwd_txt.requestFocus();
        }
        if(password.length() < 6){
            pwd_txt.setError("Password should have at least 6 characters!");
            pwd_txt.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

                assert user != null;
                if(user.isEmailVerified()) {
                    //redirect to user profile
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    //Toast.makeText(MainActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();

                }else{
                    user.sendEmailVerification();
                    Toast.makeText(MainActivity.this, "Check you email to verify your account!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
            }
        });
    }
}