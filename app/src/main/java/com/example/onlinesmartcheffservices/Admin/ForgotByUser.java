package com.example.onlinesmartcheffservices.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ForgotByUser extends Fragment {
    List<UserModelClass> Disaprrove ;
    RecyclerView recyclerView ;
    public ForgotByUser() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = v.findViewById(R.id.songrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return v ;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disaprrove = new ArrayList<>();
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Disaprrove.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);
                    if(!userModelClass.getUserType().equals("Admin") && userModelClass.getIsApprove().equals("forgottrue"))
                    {
                        Disaprrove.add(userModelClass);
                    }
                }
                if(!(Disaprrove.size() < 1 ))
                {
                    AccountsViewAdapterForSentEmailPasswordToPhone accountsViewAdapter = new AccountsViewAdapterForSentEmailPasswordToPhone(getContext() , Disaprrove) ;
                    recyclerView.setAdapter(accountsViewAdapter);

                }
                else{
                    Toast.makeText(getContext(), "Forgot List zero", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}