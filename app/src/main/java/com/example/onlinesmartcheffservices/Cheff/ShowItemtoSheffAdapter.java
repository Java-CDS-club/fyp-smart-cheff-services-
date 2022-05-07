package com.example.onlinesmartcheffservices.Cheff;

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

import com.example.onlinesmartcheffservices.Customer.RecipieView;
import com.example.onlinesmartcheffservices.HireActivity.HireActivity;
import com.example.onlinesmartcheffservices.MapPackage.MapsActivityViewLocation;
import com.example.onlinesmartcheffservices.Model.ItemClass;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShowItemtoSheffAdapter extends RecyclerView.Adapter<ShowItemtoSheffAdapter.ViewHolder> implements Filterable {
    DatabaseReference LoggerDetail  ;
    public Activity mContext ;
    public List<ItemClass> list ;
    public List<ItemClass> mlistFull ;
    String LoggerKey , LoggerName , LoggerPicture  , LoggerSkills ;
    public ShowItemtoSheffAdapter(Activity mContext, List<ItemClass> list) {
        LoggerDetail = FirebaseDatabase.getInstance().getReference("users");
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);
    }
 @NonNull
    @Override
    public ShowItemtoSheffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowItemtoSheffAdapter.ViewHolder holder, int position) {
        final ItemClass usersModelClass= list.get(position);
        holder.UserNameTextView.setText(usersModelClass.getItem_Name());
        try {
            holder.EmailTextview.setMovementMethod(new ScrollingMovementMethod());
        }catch (Exception e){
            Toast.makeText(mContext, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
        holder.EmailTextview.setText(usersModelClass.getDishType());

        if(usersModelClass.getDishType().equals("item_picture")) {
            Picasso.with(mContext).load(usersModelClass.getUri()).fit().centerCrop().into(holder.imageView);
        }else{
            holder.imageView.setBackgroundResource(R.drawable.ved);
        }
        holder.MobileTextView.setText(usersModelClass.getDate());
        holder.SkillsTextView.setText(usersModelClass.getTime());
        holder.RupeesTextView.setText(usersModelClass.getPrice());
        holder.Status.setText(usersModelClass.getItem_Name());


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usersModelClass.getDishType().equals("recipie"))
                {

                    Intent i = new Intent(mContext , RecipieView.class);
                    i.putExtra("uri" ,usersModelClass.getUri());
                    mContext.startActivity(i);

                }else{


                    Intent i  = new Intent( mContext , HireActivity.class);
                    i.putExtra("uri" ,usersModelClass.getUri());
                    mContext.startActivity( i );


                }

            }
        });






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



        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(mContext , MapsActivityViewLocation.class);
                    i.putExtra("longi" , usersModelClass.getLongitude());
                    i.putExtra("lat" , usersModelClass.getLattitude());
                    i.putExtra("name" , usersModelClass.getItem_Name());
                    mContext.startActivity(i);
                }

        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setLoggerName(String loggername) {
     LoggerName = loggername ;

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

            List<ItemClass> FilterList = new ArrayList<>() ;

            if(constraint == null && constraint.length()==0){
                FilterList.addAll(mlistFull);
            }else{
                String Characters = constraint.toString().toLowerCase().trim() ;
                for (ItemClass usersModelClass : mlistFull){
                    if(usersModelClass.getItem_Name().toLowerCase().contains(Characters)){
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
            list.addAll((Collection<? extends ItemClass>) results.values);
            notifyDataSetChanged();
        }


    };


}
