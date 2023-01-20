package com.arbib.my_social_media.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.arbib.my_social_media.activity.profile.ProfileAboutFragment;
import com.arbib.my_social_media.activity.profile.ProfilePostsFragment;

public class ProfileViewPager extends FragmentStateAdapter {


    public ProfileViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProfilePostsFragment();
            case 1:
                return new ProfileAboutFragment();
            default:
                return new ProfilePostsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
