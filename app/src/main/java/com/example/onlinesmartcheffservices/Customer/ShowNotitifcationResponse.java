package com.example.onlinesmartcheffservices.Customer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Cheff.NotificationViewAdapter;
import com.example.onlinesmartcheffservices.Model.Notification;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ShowNotitifcationResponse extends AppCompatActivity {
    List<Notification> notificationslist;
    DatabaseReference NotifcationHire;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__notification);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.archinotificationview);
        notificationslist = new ArrayList<>();
        NotifcationHire = FirebaseDatabase.getInstance().getReference("response" );
      //  Toast.makeText(getApplicationContext(), getIntent().getStringExtra("push"), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotifcationHire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationslist.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notification notification = dataSnapshot1.getValue(Notification.class);
                //    Toast.makeText(getApplicationContext(), notification.getNameHire(), Toast.LENGTH_LONG).show();
                    if(notification.getLoggerKey().equals(mAuth.getUid())){
                    notificationslist.add(notification);}

                }
                final NotificationViewAdapter Adapter = new NotificationViewAdapter(notificationslist, getApplicationContext());
                recyclerView.setAdapter(Adapter);
                final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        new RecyclerViewSwipeDecorator.Builder(ShowNotitifcationResponse.this , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
                                .addSwipeRightBackgroundColor(ContextCompat.getColor(ShowNotitifcationResponse.this , R.color.colorAccent))
                                .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                                .addBackgroundColor(ContextCompat.getColor(ShowNotitifcationResponse.this , R.color.colorAccent))
                                .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp).create().decorate();
                        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        viewHolder.itemView.setBackgroundColor(Color.parseColor("#fe104d"));
                        Adapter.setLaborIDRequreidForDelete( getIntent().getStringExtra("push"));
                        Adapter.GetNodeAtPosition(viewHolder.getAdapterPosition());
                    }
                });
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}