package com.example.onlinesmartcheffservices.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.onlinesmartcheffservices.Model.UserModelClass;
import com.example.onlinesmartcheffservices.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestTabLayout extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tab_layout);
     init() ;
    }
    private void init() {
            ViewPager viewPager = findViewById(R.id.viewpager) ;
            TabLayout tabLayout = findViewById(R.id.tablayout)  ;
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new ApproveuserclassToApproveByAdmin() , "Disable");
            viewPagerAdapter.addFragment(new ApproveuserclassToDisapproveByAdmin() , "Approve");
            viewPagerAdapter.addFragment(new ForgotByUser() , "Forgot");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.disable);
            tabLayout.getTabAt(1).setIcon(R.drawable.approveicon);
            tabLayout.getTabAt(2).setIcon(R.drawable.forgotp);
        }catch (Exception ex){
            Toast.makeText(this, "" + ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  void getAccounts (){
    }

    public  static  class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragmentArrayList ;
        private ArrayList<String> fragmentArrayStringList ;
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragmentArrayStringList = new ArrayList<>();
            fragmentArrayList = new ArrayList<>();
        }
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        public void addFragment(Fragment fragment, String string){
            fragmentArrayList.add(fragment);
            fragmentArrayStringList.add(string);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentArrayStringList.get(position);
        }
        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}