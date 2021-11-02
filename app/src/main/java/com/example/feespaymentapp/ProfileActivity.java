package com.example.feespaymentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.feespaymentapp.databinding.ActivityProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.feespaymentapp.databinding.ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_profile);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        String userID = user.getUid();

        final TextView nameTextView= findViewById(R.id.name_display_txt);
        final TextView emailTextView= findViewById(R.id.email_display_txt);
        final TextView s_idTextView= findViewById(R.id.st_id_display_txt);
        final TextView yearTextView= findViewById(R.id.year_display_txt);
        final TextView branchTextView= findViewById(R.id.branch_display_txt);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile= snapshot.getValue(User.class);

                if(userProfile!=null){
                    String name= userProfile.username;
                    String email= userProfile.email;
                    String s_id= userProfile.s_id;
                    String year= userProfile.year;
                    String branch= userProfile.branch;

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    s_idTextView.setText(s_id);
                    yearTextView.setText(year);
                    branchTextView.setText(branch);

//                    switch(ch)
//                    {
//                        case "FE":
//                            dueFees.setText(fees1);
//                            break;
//                        case "SE":
//                            dueFees.setText(fees2);
//                            break;
//                        case "TE":
//                            dueFees.setText(fees3);
//                            break;
//                        case "BE":
//                            dueFees.setText(fees4);
//                            break;
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something went Wrong!", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get menu inflater object.
        MenuInflater menuInflater = getMenuInflater();
        // Inflate the custom overflow menu
        menuInflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    /* When user select a menu item in the overflow menu xml file, this method will be invoked. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.logout_btn)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_profile);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}