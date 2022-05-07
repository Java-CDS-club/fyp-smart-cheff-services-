package com.example.onlinesmartcheffservices.Cheff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Customer.ShowCheffAdapterForChat;
import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ShowCustomerForChat extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar  ;
    DatabaseReference CheffFirebaseDatabase;
    List<UserModelClass> UserLaborList ;
    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager   ;
    ShowCustomerAdapterForChat Adapter ;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


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
                try {
                    Adapter.getFilter().filter(newText);
                }catch (Exception e){

                }
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
  //                  getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.containerbtnstyle));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cheff);

        init() ;

        toolbar = findViewById(R.id.toolbarlab);
        toolbar.setTitle("Cheff");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.searchmenu);
    }


    private void init() {
    CheffFirebaseDatabase = FirebaseDatabase.getInstance().getReference("users");
    UserLaborList = new ArrayList<>( );
    recyclerView = findViewById(R.id.recyclelabor);
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        try{
     CheffFirebaseDatabase.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             UserLaborList.clear();
             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                 UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);
                 if(usersModelClass.getUserType().equals("Customer")){
                 UserLaborList.add(usersModelClass);}
             }
             try {
                 Adapter = new ShowCustomerAdapterForChat(ShowCustomerForChat.this, UserLaborList);
                 Adapter.set();
                 recyclerView.setAdapter(Adapter);


                 final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT) {
                     @Override
                     public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                         return false;
                     }
                     @Override
                     public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

//                         new RecyclerViewSwipeDecorator.Builder(ShowLaborActivity.this , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
//                                 .addSwipeRightBackgroundColor(ContextCompat.getColor(ShowLaborActivity.this , R.color.green))
//                                 .addSwipeRightActionIcon(R.drawable.ic_call_black_24dp).addSwipeLeftBackgroundColor((ContextCompat.getColor(getApplicationContext() , R.color.yello))).addSwipeLeftActionIcon(R.drawable.ic_call_black_24dp)
//                                 .addSwipeLeftBackgroundColor(ContextCompat.getColor(ShowLaborActivity.this , R.color.yello))
//                                 .addSwipeLeftActionIcon(R.drawable.ic_message_black_24dp).create().decorate();





                         super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                     }
                     @Override
                     public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                      if(direction == 8) {
                          viewHolder.itemView.setBackgroundColor((Color.GREEN));
                          Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                          CAllByPhone(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));
                          recyclerView.refreshDrawableState();
                      }else{
                          Toast.makeText(ShowCustomerForChat.this, "Message Sent", Toast.LENGTH_SHORT).show();

                          MessageOnMbile(Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));

                          recyclerView.setAdapter(Adapter);
                          recyclerView.refreshDrawableState();

                      }
                     }
                 });
                 itemTouchHelper.attachToRecyclerView(recyclerView);




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



    private void MessageOnMbile(String getNodeAtPosition) {
        String message = "i Want to Hire for day time job Are you want to work plz contact me " + getNodeAtPosition ;
        String number =getNodeAtPosition ; ;
        SmsManager smsManager =  SmsManager.getDefault();

        if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(number)) {

            try {

                smsManager.sendTextMessage(number, null, message, null, null);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "MEssage Send " + e.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
            //   Toast.makeText(getApplicationContext(), "MEssage Send ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Message Is Empty", Toast.LENGTH_LONG).show();

        }
    }

    private void CAllByPhone(String getNodeAtPosition) {

        Toast.makeText(getApplicationContext() , getNodeAtPosition , Toast.LENGTH_LONG).show();

        if(!checkPermission()){
            requestPermission();
        }

        // "tel:0377778888" fromat //
        if(!getNodeAtPosition.isEmpty()){
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"  +getNodeAtPosition));
            if (ActivityCompat.checkSelfPermission(ShowCustomerForChat.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);}
        else {
        }



        recyclerView.setAdapter(Adapter);

    }




    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ShowCustomerForChat.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(ShowCustomerForChat.this,new String[]{Manifest.permission.CALL_PHONE}, 1);

    }

}
