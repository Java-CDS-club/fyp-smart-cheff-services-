package com.example.onlinesmartcheffservices.Management;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinesmartcheffservices.Admin.AdminDashboard;
import com.example.onlinesmartcheffservices.Cheff.CheffDashboard;
import com.example.onlinesmartcheffservices.Customer.DrawaerActivity;
import com.example.onlinesmartcheffservices.ForgotPassword;

import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Login extends AppCompatActivity {
    ProgressDialog pd ;
    public static final String TAG = "Error" ;
    private FirebaseAuth mAuth;
    TextView Register ;
    Button LoginBtn ;
    EditText Emails , Password ;
    TextView forgotpasswordvar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init() ;
        set_Listenner() ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void set_Listenner() {
       forgotpasswordvar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext() , ForgotPassword.class));
           }
       });
     
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Signup.class));
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Emails.getText().toString().isEmpty()){
                    Emails.setText("Field is Empty");
                    return;
                }
                if(Password.getText().toString().isEmpty()){
                    Password.setError("Field is Empty");
                    return;
                }
                if(Password.getText().toString().length()<8){
                    Password.setError("password length must be 8 digit");
                    return;
                }
                ////////////////////////////////////////////
                pd = new ProgressDialog(Login.this);
                pd.setMessage("loading");
                pd.show();
                //////////////////////////////////////////
                siginWithEmailPasswordFunction( Emails.getText().toString()  , Password.getText().toString());
            }
        });
    }

    private void siginWithEmailPasswordFunction(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(Emails, "User Not Exist in Database", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                            pd.dismiss();
                            updateUI(null);
                        }
                    }
                    private void updateUI(final FirebaseUser user) {
                        if (user != null) {
                            // Name, email address, and profile photo Url
                            String key = user.getUid() ;
                            final Boolean[] isExisUser = {false};
                            DatabaseReference UserDatabaseRef = FirebaseDatabase.getInstance().getReference("users") ;
                            UserDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);
                                        if(mAuth.getUid().toString().equals(usersModelClass.getUUID())){
                                            pd.dismiss();
                                            if(
                                                    (usersModelClass.getUserType().equals("Cheff") && usersModelClass.getIsApprove().equals("true"))
                                                    ||
                                            (usersModelClass.getUserType().equals("Cheff") && usersModelClass.getIsApprove().equals("forgottrue"))
                                            ) {
                                                Intent  i = new Intent(getApplicationContext() , CheffDashboard.class);
                                                i.putExtra("nid" ,  usersModelClass.getUUID()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("nname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                startActivity(i);
                                                finish();
                                            }
                                            if(
                                                    (        usersModelClass.getUserType().equals("Customer") && usersModelClass.getIsApprove().equals("true"))||
                                            (usersModelClass.getUserType().equals("Customer") && usersModelClass.getIsApprove().equals("forgottrue"))
                                            
                                            ) {
                                                pd.dismiss();
                                                Emails.setText("");
                                                Password.setText("");
                                                Intent  i = new Intent(getApplicationContext() , DrawaerActivity.class);
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personname" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("personpic" ,  usersModelClass.getProfilePhoto()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                startActivity(i);
                                                finish();
                                            }
                                            if(
                                                    (usersModelClass.getUserType().equals("Admin") && usersModelClass.getIsApprove().equals("true"))

                                            ) {
                                                Emails.setText("");
                                                Password.setText("");
                                                Intent  i = new Intent(getApplicationContext() , AdminDashboard.class);
                                                i.putExtra("loggername" ,  usersModelClass.getUserName()) ;
                                                i.putExtra("type" ,  usersModelClass.getUserType()) ;
                                                i.putExtra("uri" ,  usersModelClass.getProfilePhoto()) ;
                                                startActivity(i);
                                                finish();
                                            }
                                            isExisUser[0] = true ;
                                        }
                                    }
                                    if(isExisUser[0]==false){
                                        pd.dismiss();
                                        Snackbar.make(Emails, "User Not Exist in Database", Snackbar.LENGTH_LONG)
                                                .setAction("CLOSE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                .show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                });
    }

    private void init() {
        Register = findViewById(R.id.simplereg) ;
        LoginBtn = findViewById(R.id.login) ;
        Emails =   findViewById(R.id.d_email) ;
        Password = findViewById(R.id.d_pass) ;
        mAuth = FirebaseAuth.getInstance();
        forgotpasswordvar = findViewById(R.id.forgotpassword) ;
    }
}