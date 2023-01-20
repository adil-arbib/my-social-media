package com.arbib.my_social_media.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.arbib.my_social_media.activity.friends.FriendsFragment;
import com.arbib.my_social_media.activity.home.HomeFragment;
import com.arbib.my_social_media.activity.menu.MenuFragment;
import com.arbib.my_social_media.activity.notification.NotificationFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FriendsFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new MenuFragment();
            default:
                return new HomeFragment();
        }
    }



    @Override
    public int getItemCount() {
        return 4;
    }
}