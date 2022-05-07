package com.example.onlinesmartcheffservices.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CheffAdapter extends RecyclerView.Adapter<CheffAdapter.ViewHolder> implements Filterable {



    public Context mContext ;
    public List<UserModelClass> list ;
    public List<UserModelClass> mlistFull ;



    public CheffAdapter(Context mContext, List<UserModelClass> list) {
        this.mContext = mContext;
        this.list = list;
        mlistFull = new ArrayList<>() ;
        mlistFull.addAll(list);

    }

    @NonNull
    @Override
    public CheffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.usersidelists , null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheffAdapter.ViewHolder holder, int position) {
        final UserModelClass usersModelClass= list.get(position);
        holder.UserNameTextView.setText(usersModelClass.getUserName());
        holder.EmailTextview.setText(usersModelClass.getEmails());
        Picasso.with(mContext).load(usersModelClass.getProfilePhoto()).fit().centerCrop().into(holder.imageView);
        holder.MobileTextView.setText(usersModelClass.getPhone());
        holder.SkillsTextView.setText(usersModelClass.getIsOnline());
        holder.RupeesTextView.setText(usersModelClass.getCnic() + " Rs/- ");
        holder.Status.setText(usersModelClass.getRating());


        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent i = new Intent(mContext , MapsActivityViewLocation.class);
//                    i.putExtra("longi" , usersModelClass.getLongitude());
//                    i.putExtra("lat" , usersModelClass.getLattitude());
//                    mContext.startActivity(i);
            }
        });



//        CalculatorReviewOnByOnUSer calculatorReviewOnByOnUSer = new CalculatorReviewOnByOnUSer
//                (usersModelClass.getPushid());
//        calculatorReviewOnByOnUSer.setLayoutByHolder(holder);
//



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

        TextView Status  = itemView.findViewById(R.id.descriptionv);



    }

    @Override
    public Filter getFilter() {
        return Dataresult;
    }

    private  Filter Dataresult = new Filter() {
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
