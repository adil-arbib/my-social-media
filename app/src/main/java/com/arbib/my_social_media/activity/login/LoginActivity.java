package com.arbib.my_social_media.activity.login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.activity.register.RegisterActivity;
import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.databinding.ActivityLoginBinding;
import com.arbib.my_social_media.databinding.ActivityRegisterBinding;
import com.arbib.my_social_media.dto.LoginUser;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;
import com.arbib.my_social_media.utils.Constants;
import com.arbib.my_social_media.utils.UserSingleton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding loginBinding;
    private ActivityResultLauncher<String> mTakePhoto;
    private final int STORAGE_PERMISSION_CODE = 1;
    private Intent intent;
    private UserApi userApi;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);


        // initialize userApi service
        retrofit = RetrofitService.getRetrofit();
        userApi = retrofit.create(UserApi.class);



        // redirect user to register page
        loginBinding.txtSingup.setOnClickListener(v -> {
            intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });


        // btn login listener
        loginBinding.btnLogin.setOnClickListener(v -> {
            if (checkInputs()) {
                loginBinding.progressBar.setVisibility(View.VISIBLE); // show progress bar
                loginBinding.btnLogin.setClickable(false); // set button not clickable until we got the response
                loginUser();
            }
        });



    }


    private void loginUser() {
        userApi.login(new LoginUser(loginBinding.edtEmail.getText().toString(),
                loginBinding.edtPassword.getText().toString()))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        loginBinding.progressBar.setVisibility(View.GONE);
                        loginBinding.btnLogin.setClickable(true);
                        if(response.code() == 200) {
                            User user = response.body();
                            saveUserToInternalStorage(user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            UserSingleton.setUser(user);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            loginBinding.textFieldPassword.setError("Password incorrect !!");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("error ", t.getMessage());
                    }
                });
    }

    private boolean checkInputs() {
        boolean valid = true;
        loginBinding.textFieldEmail.setError(null);
        loginBinding.textFieldPassword.setError(null);
        if(loginBinding.edtEmail.getText().toString().isEmpty()) {
            loginBinding.textFieldEmail.setError("Enter email");
            valid = false;
        }

        if(loginBinding.edtPassword.getText().toString().isEmpty()) {
            loginBinding.textFieldPassword.setError("Enter password");
            valid = false;
        }
        return valid;

    }


    private void saveUserToInternalStorage(User user) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("currentUser", json);
        prefsEditor.apply();
    }






}