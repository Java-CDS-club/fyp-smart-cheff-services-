package com.example.onlinesmartcheffservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {

    Boolean isExist ;
    Button Submit  ; 
    EditText Email ;
    DatabaseReference databaseReference ; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    
       Email = findViewById(R.id.d_email);
       Submit = findViewById(R.id.submit) ;
       databaseReference = FirebaseDatabase.getInstance().getReference("users") ; 
       Submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               
               if(Email.getText().toString ().isEmpty()){
                   Email.setError("Field is Empty");
                   return;  
               }
               
               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       isExist = false ;
                       for(DataSnapshot dataSnapshot : snapshot.getChildren())
                       {
                           UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);

                           if (!userModelClass.getUserType().equals("Admin") && userModelClass.getEmails().equals(Email.getText().toString ())
                           ) {

                               isExist = true ;
                               
                  UserModelClass updateUser = new UserModelClass(
                            userModelClass.getUUID(),   userModelClass.getUserName(),   userModelClass.getEmails(),
                          userModelClass.getPassword(),   userModelClass.getCnic(),   userModelClass.getProfilePhoto(),
                          userModelClass.getPhone() ,   userModelClass.getUserType()  ,
                            userModelClass.getLattitude() ,   userModelClass.getLongitude(),   "forgottrue" ,
                          userModelClass.getIsOnline() ,
                            userModelClass.getRating()
                  );
                  databaseReference.child(userModelClass.getUUID()).setValue(updateUser);

                           } 
                           
                           
                       }
                       if(isExist){
                           Snackbar.make(Email, "Request Sent to Admin", Snackbar.LENGTH_LONG)
                                   .setAction("CLOSE", new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                       }
                                   })
                                   .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark ))
                                   .show();

                       }else{
                           Snackbar.make(Email, "User Not Exist in Database", Snackbar.LENGTH_LONG)
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
       });
       
    
    }
}