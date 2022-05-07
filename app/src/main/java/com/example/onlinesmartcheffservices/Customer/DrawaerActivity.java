package com.example.onlinesmartcheffservices.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.onlinesmartcheffservices.Management.Login;
import com.example.onlinesmartcheffservices.Model.Response;
import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.example.onlinesmartcheffservices.UpdateProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DrawaerActivity extends AppCompatActivity {
    UserModelClass[] UserModel ;
     String Key;
     //ResponseAdapter  responseAdapter ;
    /////////
    // ///////////////////////////////////////////////////////////////////////////////////////
    NavigationView navigationView ;
    DrawerLayout mDrawer ;
    Toolbar toolbar ;
    LayoutInflater inflater ;
    LinearLayout AddViewLinearLayout ;
    ////////////////////////////////////////////////////////Layout Header files items /////////////////
    DatabaseReference UserFirebaseDatabasae ;
    SharedPreferences sp ;
    FirebaseAuth mAuth  ;
    //////////////////////////Header Layout Setting Varaible ////////////////////////////////////
   // userAuthentications userAuth = new userAuthentications();

    String UriForDashImag ;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_nav_drawer_layout);
       // sp = getSharedPreferences(LoginActivity.logindetail ,MODE_PRIVATE);
       // userAuth.setContext(DrawaerActivity.this);

        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbarid) ;
    mDrawer = findViewById(R.id.drawer_layout) ;
    AddViewLinearLayout = findViewById(R.id.linearlayoutaddview) ;
      setSupportActionBar(toolbar);
    navigationView = findViewById(R.id.nav_view) ;
    init() ;


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this , mDrawer , toolbar , R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close) ;
        mDrawer.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.update) {
                    AddViewLinearLayout.removeAllViews();
                    Intent intent = new Intent(getApplicationContext() , UpdateProfile.class);
                    intent.putExtra("push" ,mAuth.getUid() );
                    startActivity(intent);

                }
                if(menuItem.getItemId() == R.id.notification) {
                    AddViewLinearLayout.removeAllViews();
                    LayoutInflater inflater = getLayoutInflater() ;
                    View v =inflater.inflate(R.layout.responsenotification , null) ;
                    final RecyclerView recyclerView = v.findViewById(R.id.responsenotify);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DrawaerActivity.this));
                   DatabaseReference ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response");
                   final List<Response> responseList = new ArrayList<>();
                    ResponseDataBaseFirebase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            responseList.clear();
                       for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                           Response response = dataSnapshot1.getValue(Response.class);

                           if(response.getLoggerKey().equals(mAuth.getUid())){
                           responseList.add(response);}
                       }
                           ResponseAdapter   responseAdapter = new ResponseAdapter(getApplicationContext() , responseList);
                       recyclerView.setAdapter(responseAdapter);
                       // Item Touch helper swiping animation //
                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {


                                @Override
                                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                    return false;
                                }


                                @Override
                                public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                                    new RecyclerViewSwipeDecorator.Builder(DrawaerActivity.this , c ,recyclerView , viewHolder ,dX, dY , actionState , isCurrentlyActive )
                                            .addSwipeRightBackgroundColor(ContextCompat.getColor(DrawaerActivity.this , R.color.colorAccent))
                                            .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp)
                                            .addBackgroundColor(ContextCompat.getColor(DrawaerActivity.this , R.color.colorAccent))
                                            .addSwipeRightActionIcon(R.drawable.ic_delete_black_24dp).create().decorate();
                                    super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);




                                }

                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#fe104d"));
                                    responseAdapter.DeleteNodeByPosition(responseAdapter.GetNodeAtPosition(viewHolder.getAdapterPosition()));






                                }
                            });
                            itemTouchHelper.attachToRecyclerView(recyclerView);
                            //////////////////////////
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    }
                    );
                    AddViewLinearLayout.addView(v);
                }
                if(menuItem.getItemId() == R.id.dashboard) {
                    AddViewLinearLayout.removeAllViews();
                    AddDashboard() ;
                }
                if(menuItem.getItemId() == R.id.help) {
              //      AddViewLinearLayout.removeAllViews();
            //        startActivity(new Intent(getApplicationContext() , HelpActivityForGeneralKnowladge.class));
                }
                if(menuItem.getItemId() == R.id.exit) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.putExtra("finish", true);
                    mAuth.signOut();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities

                }

                if(menuItem.getItemId() == R.id.chat) {
                    Intent intent = new Intent(getApplicationContext(), ShowCheffForChat.class);
                    intent.putExtra(getIntent().getStringExtra("personpic") , getIntent().getStringExtra("personpic") ) ;
                    intent.putExtra(getIntent().getStringExtra("personname") , getIntent().getStringExtra("personname") ) ;
                    startActivity(intent);// To clean up all activities

                }


                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }



        }); }
    private void init() {
    UserFirebaseDatabasae = FirebaseDatabase.getInstance().getReference("users");
    UserModel = new UserModelClass[1] ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance() ;
        setHeaderData() ;
        AddDashboard() ;
    }
    private void setHeaderData() {

        try {
            LayoutInflater inflater = getLayoutInflater();
            final View v = inflater.inflate(R.layout.nav_header, null);
            final TextView EmailTextview = v.findViewById(R.id.emailhead);
            final TextView UserNameTextview = v.findViewById(R.id.userNameshow);
            final TextView TypeView = v.findViewById(R.id.skillshead);
            final ImageView ImageViewhead = v.findViewById(R.id.imghead);



            final String[] Name = new String[1];
            final String[] Email = new String[1];
            final String[] Skills = new String[1];
            final String[] Uril = new String[1];


UserFirebaseDatabasae.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

         for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
            UserModelClass usersModelClass = dataSnapshot1.getValue(UserModelClass.class);
             if(mAuth.getUid().equals(usersModelClass.getUUID())){
                 Name[0] = usersModelClass.getUserName() ;
                 Email[0] = usersModelClass.getEmails();
                 Skills[0] = usersModelClass.getCnic();
                 Uril[0] = usersModelClass.getProfilePhoto() ;
UriForDashImag = usersModelClass.getProfilePhoto();
                }

         }
        if(navigationView.getHeaderCount() == 0){

            if(  !Name[0].isEmpty() || !Email[0].isEmpty() || !Skills[0].isEmpty() || Uril[0].isEmpty() ){
            EmailTextview.setText(Name[0]);
            UserNameTextview.setText(Email[0]);
            TypeView.setText(Skills[0]);
            Picasso.with(DrawaerActivity.this).load(Uril[0]).fit().centerCrop().into(ImageViewhead);}
            navigationView.addHeaderView(v);
        }else{
            if(  !Name[0].isEmpty() || !Email[0].isEmpty() || !Skills[0].isEmpty() || Uril[0].isEmpty() ){
                EmailTextview.setText(Name[0]);
                UserNameTextview.setText(Email[0]);
                TypeView.setText(Skills[0]);

                Picasso.with(DrawaerActivity.this).load(Uril[0]).fit().centerCrop().into(ImageViewhead);}
        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});



        }catch (Exception e){
        Toast.makeText(getApplicationContext() , " " + e.toString() , Toast.LENGTH_LONG).show();
        }
    }
    private void AddDashboard() {
        LayoutInflater inflater = getLayoutInflater() ;
        View v =inflater.inflate(R.layout.dashboard , null) ;
        AddViewLinearLayout.addView(v);
        setListenerondashItem(v) ;
    }
    private void setListenerondashItem(View v) {
        Button Cheff , Profile ,LocalDishes  , Dishes   , NotificationView;

        ImageView imageView = findViewById(R.id.dashimg);
        Button PFView = v.findViewById(R.id.pfview);
        PFView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext() , UpdateActvity.class);
//                intent.putExtra("push" ,Key );
//                startActivity(intent);
            }
        });

        Cheff = v.findViewById(R.id.cheff);
        Profile = v.findViewById(R.id.pfview);
        LocalDishes = v.findViewById(R.id.localdish);
        Dishes = v.findViewById(R.id.dishes);
        NotificationView = v.findViewById(R.id.notification);

        Cheff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , ShowCheff.class);
                i.putExtra("loggerkey" , Key);
                startActivity(i);
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(), ShowLaborActivity.class);
              //  i.putExtra("loggerkey" , Key);
            //    startActivity(i);
            }
        });
        LocalDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ShowArchitectureActivity.class);
//                i.putExtra("loggerkey" , Key);
//                startActivity(i);
            }
        });
        Dishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShowItems.class);
                i.putExtra("loggerkey" , mAuth.getUid());
                i.putExtra("loggername" , getIntent().getStringExtra("loggername"));
                startActivity(i);
            }
        });
         NotificationView = v.findViewById(R.id.notification);
        NotificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i  = new Intent(getApplicationContext() , CustomerPostActivity.class);
//                i.putExtra("loggerkey" , Key);
//                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do You Want to Logout")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        setResult(RESULT_OK, new Intent().putExtra("Logout", true));
                        try {
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext() , Login.class));
                            finish();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() , "net slow" , Toast.LENGTH_LONG).show();
                        }
                    }
                }).create().show();

    }
}