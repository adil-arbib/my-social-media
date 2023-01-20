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
import com.arbib.my_social_media.utils.Constants;
import com.arbib.my_social_media.utils.UserSingleton;
import com.google.gson.Gson;

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
        SharedPreferences sp =this.getSharedPreferences("pref", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sp.getString(Constants.CURRENT_USER, null);
        User user = gson.fromJson(json, User.class);

        Intent intent;

        if(user == null) {
            intent = new Intent(this, LoginActivity.class);

        }else {
            intent = new Intent(this, MainActivity.class);
            UserSingleton.setUser(user);
        }
        startActivity(intent);
        finish();

    }
}