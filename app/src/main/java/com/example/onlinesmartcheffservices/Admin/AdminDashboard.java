package com.example.onlinesmartcheffservices.Admin;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinesmartcheffservices.Management.Login;
import com.example.onlinesmartcheffservices.R;
import com.example.onlinesmartcheffservices.UpdateProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
public class AdminDashboard extends AppCompatActivity
{
    Button ViewAccounts  , Update , ApproveAccounts , ApproveRequests ;  ;
    ImageView imageView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);
        (
        ViewAccounts = findViewById(R.id.viewaccounts))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent( getApplicationContext() , RequestTabLayout.class));
                    }
                });
                imageView = findViewById(R.id.dashimg);
                Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("uri")).into(imageView);
        (ApproveRequests = findViewById(R.id.requests))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext() , ViewAccountAndTheirRequests.class));
            }
        });
        ApproveAccounts = findViewById(R.id.approveaccounts);
        ApproveAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext() , ViewApproveAdmin.class));

            }
        });



        (Update = findViewById(R.id.profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , UpdateProfile.class);
                i.putExtra("type" , "Admin");
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(AdminDashboard.this)
                .setTitle("Logout")
                .setMessage("Are You Want Logout Account !")
                .setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                mAuth.signOut();
                                startActivity(new Intent( getApplicationContext() , Login.class));
                                finish();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
        alertDialog.show();
    }
}
