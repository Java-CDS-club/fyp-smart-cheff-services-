package com.example.onlinesmartcheffservices.Cheff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinesmartcheffservices.Management.Login;
import com.example.onlinesmartcheffservices.R;
import com.example.onlinesmartcheffservices.UpdateProfile;
import com.google.firebase.auth.FirebaseAuth;

public class CheffDashboard extends AppCompatActivity
{
    Button ViewDish  , Update , Chat  , AddDishess , ViewNotifications;  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheffdashboard);

        (Update = findViewById(R.id.profile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , UpdateProfile.class);
                i.putExtra("type" , "Admin");
                startActivity(i);
            }
        });


        (Chat = findViewById(R.id.chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowCustomerForChat.class);
                intent.putExtra(getIntent().getStringExtra("personpic") , getIntent().getStringExtra("personpic") ) ;
                intent.putExtra(getIntent().getStringExtra("personname") , getIntent().getStringExtra("personname") ) ;
                startActivity(intent);// To clean up all activities
            }
        });

        (ViewNotifications = findViewById(R.id.notification)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , ShowNotification.class);

                startActivity(i);
            }
        });




        (ViewDish = findViewById(R.id.viewdish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , ShowItemsToSheff.class);
                i.putExtra("type" , "Admin");
                startActivity(i);
            }
        });


        AddDishess = findViewById(R.id.adddishes) ;
        AddDishess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AddDishes.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(CheffDashboard.this)
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
