package com.example.onlinesmartcheffservices.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Model.Response;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {
    Context mContext ;
    List<Response> responseList;

    Response response ;
    public ResponseAdapter(Context mContext, List<Response> responseList) {
        this.mContext = mContext;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.responselayout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseAdapter.ViewHolder holder, int position) {
         Response notification = responseList.get(position);

        holder.Day.setText(notification.getDays());
        holder.Location.setText(notification.getLocation());
        holder.Name.setText(notification.getNamepersonwanttohire()+" req  " + notification.getNameHire() );
        holder.Yoursalery.setText(notification.getHireSalery());
        holder.setPrice.setText(notification.getHireSetPrice());
        Picasso.with(mContext).load(notification.getPicturewhowanttohire()).fit().centerCrop().into(holder.notifcationImage);
        holder.responseStatus.setText(notification.getRespose());
        holder.skill.setText(notification.getHireSkills());
    }
          @Override
    public int getItemCount() {
        return responseList.size();
    }

    public String GetNodeAtPosition(int adapterPosition) {
        response = responseList.get(adapterPosition);
        String key = response.getResposnsenotifcationkey();
        return key ;

    }

    public void DeleteNodeByPosition(String getNodeAtPosition) {
        DatabaseReference ResponseDataBaseFirebase = FirebaseDatabase.getInstance().getReference("response").child(response.getLoggerKey());
        ResponseDataBaseFirebase.child(getNodeAtPosition).removeValue();

    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ImageView notifcationImage = itemView.findViewById(R.id.picwhowanttohire); ;
        TextView Name  = itemView.findViewById(R.id.nameofreqperson);
        TextView Location = itemView.findViewById(R.id.notificationlocation);
        TextView Yoursalery  = itemView.findViewById(R.id.yoursalery);
        TextView Day   = itemView.findViewById(R.id.days);
        TextView setPrice   = itemView.findViewById(R.id.notificationsetrupees);
        Switch aSwitch  = itemView.findViewById(R.id.acceptswtich);
        TextView responseStatus = itemView.findViewById(R.id.responsestatus);
         TextView skill = itemView.findViewById(R.id.hireskill);

    }
}
