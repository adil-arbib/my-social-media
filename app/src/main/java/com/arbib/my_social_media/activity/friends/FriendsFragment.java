package com.arbib.my_social_media.activity.friends;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.adapters.UserAdapter;
import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.databinding.FragmentFriendsBinding;
import com.arbib.my_social_media.databinding.FragmentHomeBinding;
import com.arbib.my_social_media.dto.FollowDto;
import com.arbib.my_social_media.listeners.OnUserClickListener;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;
import com.arbib.my_social_media.utils.UserSingleton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendsFragment extends Fragment implements OnUserClickListener {

    FragmentFriendsBinding binding;
    private UserAdapter adapter;
    private UserApi userApi;
    private ArrayList<User> suggestionFriends;
    private static int page = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(getLayoutInflater());
        userApi = RetrofitService.getRetrofit().create(UserApi.class);
        suggestionFriends = new ArrayList<>();

        initRecyclerView();
        scrollListener();
        fetchSuggested(page);
        page+=10;




        return binding.getRoot();
    }


    private void scrollListener() {
        binding.rcviewUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    fetchSuggested(page);
                    page+=10;
                }
            }
        });
    }

    private void initRecyclerView() {
        adapter = new UserAdapter(suggestionFriends, this);
        binding.rcviewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcviewUsers.setAdapter(adapter);
    }


    private void fetchSuggested(int page) {
        binding.progressBar.setVisibility(View.VISIBLE);
        userApi.getSuggestionsFriends(UserSingleton.getUser().getId(), page).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code() == 200){
                    adapter.setData(new ArrayList<>(response.body()));
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }

    @Override
    public void onUserClick(User user) { }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFollowClick(User user, int pos) {
        FollowDto followDto = new FollowDto(UserSingleton.getUser().getId(), user.getId());
        if(user.isFollowing()) {
            userApi.addFollowing(followDto).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("code : ",response.code()+"");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("error : ",t.getMessage());
                }
            });
            new Handler().postDelayed(() -> {
                if(user.isFollowing()) {
                    suggestionFriends.remove(user);
                    adapter.notifyItemRemoved(pos);
                }
            },10000);

        }else{
            userApi.removeFollowing(followDto).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code()==200){
                        Log.i("response : ",response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}