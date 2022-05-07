package com.example.onlinesmartcheffservices.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinesmartcheffservices.Model.Notification;
import com.example.onlinesmartcheffservices.Model.Response;
import com.example.onlinesmartcheffservices.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationViewAdapterForAdmin extends RecyclerView.Adapter<NotificationViewAdapterForAdmin.ViewHolder> {
    String LaborIdForRequiremdtoDeleteNotification ;
    List<Notification> notificationList ;
    Context mContext ;
    DatabaseReference ResponseDataBaseFirebase ;
    public NotificationViewAdapterForAdmin(List<Notification> notificationList, Context mContext) {
        this.notificationList = notificationList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public NotificationViewAdapterForAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_layout , null);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewAdapterForAdmin.ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);
        holder.Day.setText(notification.getDays());
        holder.Location.setText(notification.getLocation());
        holder.Name.setText(notification.getNamepersonwanttohire() + " Req  " + notification.getNamepersonwanttohire());
        holder.Yoursalery.setText(notification.getHireSalery());
        holder.setPrice.setText(notification.getHireSetPrice());
        Picasso.with(mContext).load(notification.getPicturewhowanttohire()).fit().centerCrop().into(holder.notifcationImage);
        holder.Skill.setText(notification.getLoggerSkill());
        PrettyTime p = new PrettyTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate = notification.getDate();
//            Date date = new Date(sdf.parse(strDate).getTime());
        //          holder.Date.setText(notification.getDate() + " " + p.format(date));
        //        Log.d("dddd",date.toString());
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){


                    Notification notification1 = new Notification(notification.getName(), notification.getHireSalery(),notification.getHireSetPrice() , notification.getPicturewhowanttohire(), notification.getDays()
                            , notification.getNamepersonwanttohire(), notification.getLocation(), notification.getLoggerKey() , notification.getLoggerSkill() , notification.getHirePrsonSkill(), notification.getDate() , notification.getNotkey()
                            , notification.getNotificationpersonkey()  , "true");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notification/"+notification.getNotificationpersonkey());
                    databaseReference.child(notification.getNotkey()).setValue(notification1);
                    Toast.makeText(mContext, "Request Approve To Buy Item", Toast.LENGTH_SHORT).show();

                }else{
                    ResponseDataBaseFirebase.removeValue();
                    Toast.makeText(mContext , "Reject" , Toast.LENGTH_LONG).show(); } }        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public String GetNodeAtPosition(int adapterPosition) {
    Notification    notificationfordelete = notificationList.get(adapterPosition);
        String key  = notificationfordelete.getNotkey();
        DatabaseReference DeleteNotifcationFromDatabase = FirebaseDatabase.getInstance().getReference("Notification").child(LaborIdForRequiremdtoDeleteNotification);
    //    Toast.makeText(mContext , notificationfordelete.getLoggerKey()  , Toast.LENGTH_LONG).show();
        DeleteNotifcationFromDatabase.child(key).removeValue();
        return  key ;
    }

    public void setLaborIDRequreidForDelete(String push) {
        LaborIdForRequiremdtoDeleteNotification = push;


    }



    public  class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(@NonNull View itemView)


        {

            super(itemView);
        }

        ImageView notifcationImage = itemView.findViewById(R.id.picwhowanttohire); ;
        TextView Name  = itemView.findViewById(R.id.nameofreqperson);
        TextView Location = itemView.findViewById(R.id.notificationlocation);
        TextView Yoursalery  = itemView.findViewById(R.id.yoursalery);
        TextView Day   = itemView.findViewById(R.id.days);
        TextView setPrice   = itemView.findViewById(R.id.notificationsetrupees);
        TextView Skill = itemView.findViewById(R.id.logskill);
        Switch aSwitch  = itemView.findViewById(R.id.acceptswtich);
        TextView Date  = itemView.findViewById(R.id.date);
    }

}
