package com.example.onlinesmartcheffservices.Customer;

import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.ChatFiles.ChatActivity;
import com.example.onlinesmartcheffservices.MapPackage.MapsActivityViewLocation;
import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowCheffAdapterForChat extends RecyclerView.Adapter<ShowCheffAdapterForChat.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<UserModelClass> list ;
    public List<UserModelClass> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    public ShowCheffAdapterForChat(Activity mContext, List<UserModelClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
    public  void set(){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
                LoggerDetail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                       UserModelClass usersModelClass = snapshot.getValue(UserModelClass.class);
                       if(usersModelClass.getUUID().equals(mAuth.getUid())){
                           LoggerName  =  usersModelClass.getUserName() ;
                           LoggerPicture = usersModelClass.getProfilePhoto() ;
                           LoggerSkills = usersModelClass.getUserType();
                       }
                   }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
     }
                });
        }
 @NonNull
    @Override
    public ShowCheffAdapterForChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowCheffAdapterForChat.ViewHolder holder, int position) {
        final UserModelClass usersModelClass= list.get(position);
        holder.UserNameTextView.setText(usersModelClass.getUserName());
        try {
            holder.EmailTextview.setMovementMethod(new ScrollingMovementMethod());
        }catch (Exception e){
            Toast.makeText(mContext, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        holder.EmailTextview.setText(usersModelClass.getEmails());
        Picasso.with(mContext).load(usersModelClass.getProfilePhoto()).fit().centerCrop().into(holder.imageView);
        holder.MobileTextView.setText(usersModelClass.getPhone());
        holder.SkillsTextView.setText(usersModelClass.getUserName());
        holder.RupeesTextView.setText(usersModelClass.getUserName());
        if(usersModelClass.getIsApprove().equals("true")){
            holder.Status.setText("Authanticate Cheff");
        }else{
            holder.Status.setText("UnAuthanticate Cheff");

        }

        //        holder.itemView.setOnClickListener(new View.OnClickListener() {
      //      @Override
    //        public void onClick(View v) {
  //              if(usersModelClass.getUserType().equals("Cheff")){
//                    Intent intent = new Intent(mContext , HireActivity.class);
//        intent.putExtra("Phone" , usersModelClass.getPhone()) ;
//        intent.putExtra("Latitude" , usersModelClass.getLattitude());
//        intent.putExtra("Longitude" , usersModelClass.getLongitude());
//        intent.putExtra("PicUri" , usersModelClass.getPicUri());
//        intent.putExtra("Cnic" , usersModelClass.getCnic());
//        intent.putExtra("status" , usersModelClass.getStatus());
//        intent.putExtra("Skill" , usersModelClass.getSkills());
//        intent.putExtra("city" , usersModelClass.getCity());
//        intent.putExtra("age" , usersModelClass.getAge());
//        intent.putExtra("price" , usersModelClass.getPrice());
//        intent.putExtra("username" , usersModelClass.getUserName());
//        intent.putExtra("pushid" , usersModelClass.getPushid());
//        intent.putExtra("Email" , usersModelClass.getEmail());
//
//        intent.putExtra("loggerkey" , LoggerKey);
//        intent.putExtra("loggername" , LoggerName);
//        intent.putExtra("loggerpicture" ,LoggerPicture);
//        intent.putExtra("loggerskill" ,LoggerSkills);
//


   //                 mContext.startActivity(intent);
    //            }
  //          }
//        });

        holder.location.setImageResource(R.drawable.ic_message_black);



        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(mContext , ChatActivity.class);
                    i.putExtra("longi" , usersModelClass.getLongitude());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("personpic" , LoggerPicture);
                    i.putExtra("personname" , LoggerName);
                    i.putExtra("Pkey" , usersModelClass.getUUID());
                    i.putExtra("userpic" , usersModelClass.getProfilePhoto());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("name" , usersModelClass.getUserName());
                    mContext.startActivity(i);
                }

        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String GetNodeAtPosition(int adapterPosition) {
        UserModelClass usersModelClass = list.get(adapterPosition);
        String Number = usersModelClass.getPhone();
        return Number;
    }



public class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        ImageView imageView = itemView.findViewById(R.id.viewimg);
        ImageView location = itemView.findViewById(R.id.locationview);
        TextView EmailTextview = itemView.findViewById(R.id.viewemail);
        TextView UserNameTextView = itemView.findViewById(R.id.viewusername);
        TextView MobileTextView = itemView.findViewById(R.id.viewmobile);
        TextView SkillsTextView = itemView.findViewById(R.id.viewtype);
        TextView RupeesTextView = itemView.findViewById(R.id.viewruppes);

        TextView Status = itemView.findViewById(R.id.descriptionv);

        CircularImageView circularImageView = itemView.findViewById(R.id.onli);
    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private Filter Dataresult = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<UserModelClass> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (UserModelClass usersModelClass : mlistFull){
                    if(usersModelClass.getUserName().toLowerCase().contains(Characters)){
                        FilterList.add(usersModelClass);
                    }
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = FilterList ;
            return filterResults ;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends UserModelClass>) results.values);
            notifyDataSetChanged();
        }


    };


}
