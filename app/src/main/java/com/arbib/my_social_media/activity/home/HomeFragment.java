package com.arbib.my_social_media.activity.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.login.LoginActivity;
import com.arbib.my_social_media.activity.post.AddPostActivity;
import com.arbib.my_social_media.activity.profile.ProfileActivity;
import com.arbib.my_social_media.adapters.PostAdapter;
import com.arbib.my_social_media.databinding.FragmentHomeBinding;
import com.arbib.my_social_media.listeners.OnPostItemsListener;
import com.arbib.my_social_media.model.Like;
import com.arbib.my_social_media.model.Post;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.utils.BitmapConverter;
import com.arbib.my_social_media.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements OnPostItemsListener {

    private User currentUser;
    private FragmentHomeBinding binding;
    private Intent intent;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        getLoggedUser();
        showUserPic();

        binding.postsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.profileImage.setOnClickListener(v -> {
            intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra(Constants.USER_PROFILE, currentUser);
            startActivity(intent);
        });

        binding.edtPost.setOnClickListener(v->{
            intent = new Intent(getContext(), AddPostActivity.class);
            startActivity(intent);
        });




        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post(1L, "ibvfdahajd",null, "2002-10-10",null,
                new User(1L, "adil", "arbib",null,null,null,null,null)));
        posts.add(new Post(1L, "ibvfdahajd",null, "2002-10-10",null,
                new User(1L, "adil", "arbib",null,null,null,null,null)));

        PostAdapter postAdapter = new PostAdapter(posts, this, getContext() );


        binding.postsRecView.setAdapter(postAdapter);



        return binding.getRoot();
    }

    @Override
    public void onUserClick(User user) {

    }

    @Override
    public void onLikeClick(Like like) {

    }

    @Override
    public void onCommentsClick(Post post) {

    }

    private void showUserPic() {
        if(currentUser.getPicture() != null) {
            Bitmap btm = BitmapConverter.convertStringToBitmap(currentUser.getPicture());
            binding.profileImage.setImageBitmap(btm);
        }
    }


    private void getLoggedUser() {
        SharedPreferences preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
    }
}