package com.arbib.my_social_media.activity.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.login.LoginActivity;
import com.arbib.my_social_media.databinding.FragmentMenuBinding;

import java.util.Objects;


public class MenuFragment extends Fragment {

    FragmentMenuBinding binding;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(getLayoutInflater());

        binding.iconLogout.setOnClickListener(v->{
            logout();
        });

        return binding.getRoot();
    }

    private void logout() {
        intent = new Intent(getContext(), LoginActivity.class);
        requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).edit().clear().apply();
        startActivity(intent);
    }
}