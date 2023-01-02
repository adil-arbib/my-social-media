package com.arbib.my_social_media.activity.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.login.LoginActivity;
import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadingActivity extends AppCompatActivity {
    private static final int TIME_OUT = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(this::checkLogin, TIME_OUT);

    }


    private void checkLogin() {
        SharedPreferences sp =this.getSharedPreferences("Login", MODE_PRIVATE);

        Long id = sp.getLong("id", -1);

        Intent intent;

        if(id == -1) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent = new Intent(this, MainActivity.class);
            Retrofit retrofit = RetrofitService.getRetrofit();
            UserApi userApi = retrofit.create(UserApi.class);
            userApi.getUser(id).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code() == 200) {
                        intent.putExtra("currentUser", response.body());
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }

    }
}