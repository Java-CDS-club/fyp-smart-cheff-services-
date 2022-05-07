package com.example.onlinesmartcheffservices.Cheff;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.ParseException;
import  java.util.Date;


import com.example.onlinesmartcheffservices.MapPackage.PostMapActivityLocation;
import com.example.onlinesmartcheffservices.Model.ItemClass;
import com.example.onlinesmartcheffservices.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AddDishes extends AppCompatActivity {

    FirebaseAuth mAuth ;
    FirebaseUser CurrentUser  ;
    DatabaseReference ItemDateBaseFirebase ;
    StorageReference  ItemStorageRef ;
    int MIN , HOURS ;
    String Time = "" ;
    String DateofDay = ""  ;
    String pic = "" ;
    String Longitude  = "" ;
    String Lattitude  = "" ;
    private int Year_x, Month_x, Dat_x;
    EditText TitleView, PriceView , DateView;
    ImageView  MapViewImageView , showimg;
    Uri picUri;
    Spinner ItemType ;
    Button SubmitPostBtn , ViewButton  , PostPictureView , UploadVedio ;
    public static final String DATE_FORMAT_1 = "hh:mm:ss a";
    public static final int DIALOG_ID = 0;

    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        init();
        setOnClickListenner( ) ;
        setOnItemselectedListenner() ;
        setOnSubmitClickListener() ;
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser() ;

      ;

    }

    private void setOnItemselectedListenner() {
        if(ItemType.getSelectedItem().toString().equals("recipie")){
            PostPictureView.setVisibility(View.INVISIBLE);
            UploadVedio.setVisibility(View.VISIBLE);
        }else{
            UploadVedio.setVisibility(View.INVISIBLE);
            PostPictureView.setVisibility(View.VISIBLE);
        }


        ItemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(ItemType.getSelectedItem().toString().equals("recipie")){
                    PostPictureView.setVisibility(View.GONE);
                    UploadVedio.setVisibility(View.VISIBLE);
                }else{
                    UploadVedio.setVisibility(View.GONE);
                    PostPictureView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setOnSubmitClickListener() {
        SubmitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TitleView.getText().toString().trim().isEmpty()){
                    TitleView.setError("This Field is Empty");
                    return;
                }

                if(picUri == null){
                    Toast.makeText(getApplicationContext(), "Choose Picture or Recipie", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(Lattitude.isEmpty()){
                    Snackbar.make(TitleView, "Select Location", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                return;
                }


                if(Longitude.isEmpty()){
                    Snackbar.make(TitleView, "Select Location", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                    return;
                }

                if(PriceView.getText().toString().isEmpty())
                {
                    PriceView.setError("This Field is Empty");
                    return;
                }
                if(Lattitude.isEmpty() || Longitude.isEmpty() ) {
                    Snackbar.make(TitleView, "Select Location", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                    return;
                }





                if(DateofDay.isEmpty()){
                    Snackbar.make(TitleView, "Select Date ", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                    return;
                }


                if(Integer.valueOf(Function(DateofDay , getCurrentDateOFDay()))<0){
                    Snackbar.make(TitleView, "Pleause Enter Valid Date", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                    return;
                }



                try {

        String ItemId = ItemDateBaseFirebase.push().getKey().toString();

UploadTaskFunctionF(

        ItemId, TitleView.getText().toString(),CurrentUser.getUid(), getAddress(Lattitude , Longitude), Lattitude,Longitude, Time, DateofDay, ItemType.getSelectedItem(),
        PriceView.getText()

);


                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "" + ex.toString(), Toast.LENGTH_SHORT).show();
                }












            }
        });
    }

    private void UploadTaskFunctionF(final String itemId, final String toString, final String uid, final String address, final String lattitude, final String longitude, final String time, final String dateofDay, final Object selectedItem, final Editable text) {

        ItemStorageRef.child(System.currentTimeMillis() + "." + getExtension(picUri)).putFile(picUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {





                    }
                },500);


                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        if(!uri.toString().isEmpty()){
                            ItemClass itemClass
                                    = new ItemClass(
                                     itemId,  toString,  uid,  address,  lattitude,  longitude,  time,  dateofDay,  selectedItem.toString(),  text.toString()
                            , uri.toString()

                            );
                            ItemDateBaseFirebase.child(itemId).setValue(itemClass);
                            Toast.makeText(AddDishes.this, "Item Uploaded", Toast.LENGTH_SHORT).show();

                        }


                        // here we will empty / /

                        TitleView.setText("");
                        PriceView.setText("");
                        DateView.setText("");
                        showimg.setVisibility(View.GONE);
                    }
                });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {



                double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);

            }
        });






    }

    private String  getAddress(String lattitude, String longitude) {
    return  "" ;
    }

    private void setOnClickListenner() {
        PostPictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPicture();
            }
        });
        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext() , ShowItemsToSheff.class);
                startActivity(i);
            }
        });
        (UploadVedio = findViewById(R.id.uploadvedio)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser() ;
            }
        });
        MapViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PostMapActivityLocation.class);
                startActivityForResult(i, 1);
            }
        });

        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
    }

    private void init() {
        PostPictureView = findViewById(R.id.postimageview);
        TitleView = findViewById(R.id.titleofpost);
        progressBar = findViewById(R.id.prog);
        PriceView = findViewById(R.id.princerange);
        ItemType = findViewById(R.id.description);
        DateView = findViewById(R.id.calender);
        MapViewImageView = findViewById(R.id.mappost);
        SubmitPostBtn = findViewById(R.id.submitpost);
        ViewButton = findViewById(R.id.viewpost);
        showimg = findViewById(R.id.postimageview1);

        Calendar calendar = Calendar.getInstance();
        Year_x = calendar.get(calendar.YEAR);
        Month_x = calendar.get(calendar.MONTH);
        Dat_x = calendar.get(calendar.DAY_OF_MONTH);
        MIN = calendar.get(calendar.MINUTE);
        HOURS = calendar.get(calendar.HOUR_OF_DAY);
        ItemDateBaseFirebase = FirebaseDatabase.getInstance().getReference("ItemDatabase") ;
        ItemStorageRef = FirebaseStorage.getInstance().getReference("ItemUpload") ;
    }

    private void FileChooser() {
        SelectVedio();
    }


    public void Showdialog() {
        DateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, Year_x, Month_x, Dat_x);
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int month , int dayOfMonth) {
            Year_x = year;
            Month_x = (month + 1);
            Dat_x = dayOfMonth;
            DateofDay = String.valueOf(Dat_x) + " " + String.valueOf(Month_x)+ " " + String.valueOf(Year_x) ;

            Toast.makeText(getApplicationContext(), "Date" + month, Toast.LENGTH_SHORT).show();

        TimePickerDialog    timePickerDialog =
                    new TimePickerDialog(AddDishes.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            Time =   getTimeFormated(hourOfDay,minute) ;

                            DateView.setText(DateofDay + " "  + Time);

                            //     Toast.makeText(context, getTimeFormated(hourOfDay,minute) + "", Toast.LENGTH_SHORT).show();

                        }
                    },HOURS,MIN,android.text.format.DateFormat.is24HourFormat(getApplicationContext()));
            timePickerDialog.show();
        }
    };
    private void SelectPicture() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 2);
    }
    private void SelectVedio() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), 2 );
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {


            Lattitude = data.getStringExtra("Lattitude") ;
            Longitude = data.getStringExtra("Longitude") ;
        }


        if (resultCode == RESULT_OK && requestCode == 2) {
            Toast.makeText(this, "inif" + requestCode , Toast.LENGTH_SHORT).show();



                        if(!ItemType.getSelectedItem().equals("recipie")) {
                            picUri = data.getData();
                            showimg.setVisibility(View.VISIBLE);

                            Picasso.with(getApplicationContext()).load(String.valueOf(picUri)).fit().into(showimg);
                        }else{
                            picUri = data.getData();
                            showimg.setImageResource(R.drawable.ved);
                        }




        }
    }
    public  String getCurrentDate()  {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    public  String getCurrentDateOFDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    private String getTimeFormated(int hr,int min) {
        java.sql.Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm:ss a");
        return formatter.format(tme);
    }
    @NotNull
    private  String Function(String  inp1 , String inp2){

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String inputString1 = inp1;
        String inputString2 = inp2;
        long diff = 0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            diff = date1.getTime() - date2.getTime();
            Log.d("Days: " , TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + "" ;
    }
   public void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}