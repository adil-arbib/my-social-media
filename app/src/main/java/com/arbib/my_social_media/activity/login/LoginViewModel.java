package com.arbib.my_social_media.activity.login;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.dto.LoginUser;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<LoginUser> userMutableLiveData;
    public MutableLiveData<User> currentUser = new MutableLiveData<>();
    private Retrofit retrofit = RetrofitService.getRetrofit();
    private UserApi userApi = retrofit.create(UserApi.class);

    public MutableLiveData<LoginUser> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }



    public void onClick(View view) {
        LoginUser loginUser = new LoginUser(email.getValue(), password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    public void onRegisterClick(View view) {

    }


    public void login() {
        userApi.login(userMutableLiveData.getValue()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    currentUser.setValue(response.body());
                }else currentUser.setValue(null);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
