package com.arbib.my_social_media.activity.register;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.login.LoginActivity;

import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.databinding.ActivityRegisterBinding;
import com.arbib.my_social_media.dto.RegisterDto;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    private final Calendar myCalendar = Calendar.getInstance();
    private UserApi userApi;
    private Retrofit retrofit;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = registerBinding.getRoot();
        setContentView(view);


        retrofit = RetrofitService.getRetrofit();
        userApi = retrofit.create(UserApi.class);



        datePickerListener(); // date picker listener for birthday field








        intent = new Intent(this, LoginActivity.class);
        registerBinding.txtLogin.setOnClickListener(v -> {
            startActivity(intent);
            finish();
        });


        registerBinding.btnRegister.setOnClickListener(v -> {
            if(checkInputs()) {
                registerUser();
            }
        });



    }

    private void registerUser()  {

        userApi.register(new RegisterDto(
                registerBinding.edtFirstName.getText().toString(),
                registerBinding.edtLastName.getText().toString(),
                registerBinding.edtEmail.getText().toString(),
                registerBinding.edtPassword.getText().toString(),
                registerBinding.edtBirthday.getText().toString()
        )).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    Toast.makeText(RegisterActivity.this,
                            "Registered Successfully !!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(RegisterActivity.this,
                            "Email already taken !!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void clearErrors(TextInputLayout ... inputLayouts) {
        for (TextInputLayout input : inputLayouts) {
            input.setError(null);
        }
    }

    private boolean checkInputs() {

        boolean valid = true;

        clearErrors(registerBinding.textFieldFirstName,
                registerBinding.textFieldLastName,
                registerBinding.textFieldEmail,
                registerBinding.textFieldPass,
                registerBinding.textFieldPassConfirm,
                registerBinding.textFieldBirthday);

        if(registerBinding.edtFirstName.getText().toString().isEmpty()) {
            registerBinding.textFieldFirstName.setError("Enter firstname !!");
            valid = false;
        }if(registerBinding.edtLastName.getText().toString().isEmpty()) {
            registerBinding.textFieldLastName.setError("Enter lastname !!");
            valid = false;
        }if(registerBinding.edtEmail.getText().toString().isEmpty()) {
            registerBinding.textFieldEmail.setError("Enter email !!");
            valid = false;
        }if(!registerBinding.edtEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            registerBinding.textFieldEmail.setError("Invalid email !!");
            valid = false;
        }if(registerBinding.edtPassword.getText().toString().isEmpty()) {
            registerBinding.textFieldPass.setError("Enter password !!");
            valid = false;
        }if(registerBinding.edtPasswordConfirm.getText().toString().isEmpty()) {
            registerBinding.textFieldPassConfirm.setError("Enter password !!");
            valid = false;
        }if(registerBinding.edtBirthday.getText().toString().isEmpty()) {
            registerBinding.textFieldBirthday.setError("Enter birthday !!");
            valid = false;
        }if(!registerBinding.edtPassword.getText().toString()
                .equals(registerBinding.edtPasswordConfirm.getText().toString())) {
            registerBinding.textFieldPassConfirm.setError("Password not matched !!");
            valid = false;
        }

        return valid;

    }


    private void datePickerListener() {
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };

        registerBinding.edtBirthday.setOnClickListener(
                view -> {
                    DatePickerDialog dialog = new DatePickerDialog(
                            RegisterActivity.this,
                            R.style.DialogTheme,
                            date,
                            myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                });
    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        registerBinding.edtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }
}