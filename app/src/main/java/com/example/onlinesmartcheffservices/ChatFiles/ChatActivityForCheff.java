package com.example.onlinesmartcheffservices.ChatFiles;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Model.Comment_Model_class;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivityForCheff extends AppCompatActivity {



    ImageView CommentPic ;
    List<Comment_Model_class> comment_model_classList ;
    String PersonKey , PersonPic , PersonName  ;

    FirebaseAuth mAuth ;

    RecyclerView recyclerView ;
    DatabaseReference CommentofPostDatabase ;
    EditText CommentText ;
    ImageView CommentSentbtn ;
    ImageView BackArrow ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding_related_comment);
        CommentSentbtn = findViewById(R.id.commentsentbtn) ;
        CommentText = findViewById(R.id.commentedittext) ;


        mAuth = FirebaseAuth.getInstance() ;

        CommentPic  =findViewById(R.id.commentpic) ;


        recyclerView = findViewById(R.id.comentrecylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        comment_model_classList = new ArrayList<>();
            CommentofPostDatabase = FirebaseDatabase.getInstance().getReference("comment").child( mAuth.getUid()+getIntent().getStringExtra("Pkey").toString()        );

        Toast.makeText(this, ""+getIntent().getStringExtra("Pkey") +"++++" + mAuth.getUid(), Toast.LENGTH_SHORT).show();

        Log.d("KeyForFind" , mAuth.getUid() + getIntent().getStringExtra("Pkey") );
        PersonKey = mAuth.getUid();
            PersonPic = getIntent().getStringExtra("personpic");
            PersonName = getIntent().getStringExtra("personname");

            Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("userpic")).into(CommentPic);


        CommentSentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CommentText.getText().toString().trim().isEmpty()){

                        String key = CommentofPostDatabase.push().getKey().toString();
                        Comment_Model_class comment_model_class = new
                                Comment_Model_class(key , getIntent().getStringExtra("Pkey") , PersonKey,
                                PersonName ,  PersonPic ,  CommentText.getText().toString().trim() ,
                                getCurrentDate() );
                        CommentofPostDatabase.child(key).setValue(comment_model_class);
                       CommentText.setText("");


                }else{
                    CommentText.setError("Field is Empty");
                }
            }
        });
        CommentofPostDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comment_model_classList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Comment_Model_class comment_model_class = dataSnapshot1.getValue(Comment_Model_class.class);
                    comment_model_classList.add(comment_model_class);
                }
                CommentDataAdapter commentDataAdapter = new CommentDataAdapter(ChatActivityForCheff.this,comment_model_classList);
                commentDataAdapter.setPic(PersonPic) ;
                recyclerView.setAdapter(commentDataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivityForCheff.this, ""+databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });









    }

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
