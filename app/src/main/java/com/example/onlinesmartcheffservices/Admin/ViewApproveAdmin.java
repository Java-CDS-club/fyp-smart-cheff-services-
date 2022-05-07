package com.example.onlinesmartcheffservices.Admin;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.R;
import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewApproveAdmin extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar  ;
    DatabaseReference LaborShowFierbaseDatabase ;
    List<UserModelClass> UserLaborList ;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager   ;
    AccountsViewAdapterForDisable Adapter ;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu , menu);
        ///////////////////////////////////////////////////
        getMenuInflater().inflate(R.menu.sortaction , menu);
        MenuItem Newest = menu.findItem(R.id.newest);
        MenuItem Oldest = menu.findItem(R.id.oldest);
        Newest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(Adapter);
                return false;
            }
        });
        Oldest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setReverseLayout(false);
                linearLayoutManager.setStackFromEnd(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(Adapter);
                return false;
            }
        });
        ///////////////////////////////////////////////////
        MenuItem searchmenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchmenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Adapter.getFilter().filter(newText);
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchmenuItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.containerbtnstyle));
                    //     getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                }
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_acccount);
        init() ;

        toolbar = findViewById(R.id.toolbarlabcontr);
        toolbar.setTitle("Accounts");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.searchmenu);
    }


    private void init() {
        LaborShowFierbaseDatabase = FirebaseDatabase.getInstance().getReference("users");
        UserLaborList = new ArrayList<>( );
        recyclerView = findViewById(R.id.recyclelaborcontr);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        try{
            LaborShowFierbaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserLaborList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);

                    if(!usersModelClass.getUserType().equals("Admin") &&
                         usersModelClass.getIsApprove().equals("true")){
                            UserLaborList.add(usersModelClass);
                    }
                    }


                    try {
                        Adapter = new AccountsViewAdapterForDisable(ViewApproveAdmin.this, UserLaborList);
                        recyclerView.setAdapter(Adapter);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext() , e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}catch (Exception e){
            Toast.makeText(getApplicationContext() , "Net Slow to load data " + e.toString(), Toast.LENGTH_LONG ).show();
        }
    }
}
