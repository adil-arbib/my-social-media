package com.arbib.my_social_media.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.friends.FriendsFragment;
import com.arbib.my_social_media.adapters.ViewPagerAdapter;
import com.arbib.my_social_media.databinding.ActivityLoginBinding;
import com.arbib.my_social_media.databinding.ActivityMainBinding;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.utils.Constants;
import com.arbib.my_social_media.utils.UserSingleton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private ViewPagerAdapter viewPagerAdapter;
    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        currentUser = UserSingleton.getUser();


        viewPagerAdapter = new ViewPagerAdapter(this);
        mainBinding.viewPager.setAdapter(viewPagerAdapter);

        mainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainBinding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mainBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mainBinding.tabLayout.getTabAt(position).select();
            }
        });




    }

}