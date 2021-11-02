package com.example.feespaymentapp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout select_branch;
    AutoCompleteTextView branch_txt;
    //String[] arrayList_branch;
    ArrayAdapter<String> arrayAdapter_branch;

    private FirebaseAuth mAuth;

    TextInputLayout select_year;
    AutoCompleteTextView year_txt;
    //String[] arrayList_year;
    ArrayAdapter<String> arrayAdapter_year;

    private EditText email_txt, user_name_txt, pwd_txt, s_id_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        select_branch= findViewById(R.id.branch);
        String []option= {"Comps", "IT", "EXTC", "Mech"};
        branch_txt= findViewById(R.id.branch_txt);
        arrayAdapter_branch= new ArrayAdapter<>(this, R.layout.list_item, option);
        branch_txt.setAdapter(arrayAdapter_branch);

        select_year= findViewById(R.id.year);
        String []option2={"FE", "SE", "TE", "BE"};
        year_txt= findViewById(R.id.year_txt);
        arrayAdapter_year= new ArrayAdapter<>(this, R.layout.list_item, option2);
        year_txt.setAdapter(arrayAdapter_year);

        Button registerUser = findViewById(R.id.register);
        registerUser.setOnClickListener(this);

        email_txt= findViewById(R.id.email_txt);
        user_name_txt= findViewById(R.id.user_name_txt);
        pwd_txt= findViewById(R.id.pwd_txt);
        s_id_txt= findViewById(R.id.s_id_txt);
        year_txt= findViewById(R.id.year_txt);
        branch_txt= findViewById((R.id.branch_txt));



    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.register){
            registerUser();
        }
    }

    public void registerUser(){
        String email= email_txt.getText().toString().trim();
        String username= user_name_txt.getText().toString().trim();
        String password= pwd_txt.getText().toString().trim();
        String s_id= s_id_txt.getText().toString().trim();
        String year= year_txt.getText().toString().trim();
        String branch= branch_txt.getText().toString().trim();

        if(email.isEmpty()){
            email_txt.setError("Email is required!");
            email_txt.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_txt.setError("Enter a valid Email!");
            email_txt.requestFocus();
        }
        if(username.isEmpty()){
            user_name_txt.setError("Username is required!");
            user_name_txt.requestFocus();
        }
        if(password.isEmpty()){
            pwd_txt.setError("Password is required!");
            pwd_txt.requestFocus();
        }
        if(password.length() < 6){
            pwd_txt.setError("Password should have at least 6 characters!");
            pwd_txt.requestFocus();
        }

        if(s_id.isEmpty()){
            s_id_txt.setError("Student ID is required!");
            s_id_txt.requestFocus();
        }
        if(year.isEmpty()){
            year_txt.setError("Year is required!");
            year_txt.requestFocus();
        }
        if(branch.isEmpty()){
            branch_txt.setError("Branch is required!");
            branch_txt.requestFocus();
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        User user= new User(email, username, password, s_id, year, branch);
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(RegisterUser.this, "Failed to register! Please try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterUser.this, "Failed to register! Please try again", Toast.LENGTH_LONG).show();
                    }
                });
    }
}