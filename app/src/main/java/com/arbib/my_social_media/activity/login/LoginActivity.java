package com.arbib.my_social_media.activity.login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding loginBinding;
    private ActivityResultLauncher<String> mTakePhoto;
    private final int STORAGE_PERMISSION_CODE = 1;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_login);

        loginBinding.setLifecycleOwner(this);

        loginViewModel = ViewModelProviders.of(this)
                .get(LoginViewModel.class);

        loginBinding.setLoginViewModel(loginViewModel);





//        userApi.getUser(4L).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(response.code() == 200) {
//                    User user = response.body();
//                    Bitmap btm = BitmapConverter.convertStringToBitmap(user.getPicture());
//                    loginBinding.logo.setImageBitmap(btm);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.e("error ", t.getMessage());
//            }
//        });


//        loginBinding.permission.setOnClickListener(v -> {
//            if (ContextCompat.checkSelfPermission(LoginActivity.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//                mTakePhoto.launch("image/*");
//
//            } else {
//                requestStoragePermission();
//            }
//        });

//        mTakePhoto = registerForActivityResult(
//                new ActivityResultContracts.GetContent(),
//                result -> {
//                    File file = new File(RealPathUtil.getRealPath(LoginActivity.this, result));
//                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//
//                    userApi.updateImage(4L, filePart).enqueue(new Callback<Map<String, String>>() {
//                        @Override
//                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
//                            Log.d("result", response.body().get("result"));
//                        }
//
//                        @Override
//                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
//                            Log.e("error", t.getMessage());
//                        }
//                    });
//
//                });



        loginViewModel.getUser().observe(this, loginUser -> {
            System.out.println(loginUser);
            loginBinding.textFieldEmail.setError(null);
            loginBinding.textFieldPassword.setError(null);
            if (TextUtils.isEmpty(loginUser.getEmail())) {
                loginBinding.textFieldEmail.setError("Enter an E-Mail Address");
                loginBinding.edtEmail.requestFocus();
            }
            else if (!loginUser.isEmailValid()) {
                loginBinding.textFieldEmail.setError("Enter a Valid E-mail Address");
                loginBinding.edtEmail.requestFocus();
            }
            else if (TextUtils.isEmpty(loginUser.getPassword())) {
                loginBinding.textFieldPassword.setError("Enter a Password");
                loginBinding.edtPassword.requestFocus();
            }
            else {
                loginBinding.progressBar.setVisibility(View.VISIBLE);
                loginViewModel.login();
            }
        });

        loginViewModel.currentUser.observe(this, currentUser -> {
            loginBinding.progressBar.setVisibility(View.GONE);
            if(currentUser == null) {
                Toast.makeText(this, "Data invalid !!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "valid data !!", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putLong("id",currentUser.getId());
                editor.apply();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
                finish();
            }
        });




    }

//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//            new AlertDialog.Builder(LoginActivity.this)
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed because of this and that")
//                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(LoginActivity.this,
//                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
//                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
//                    .create().show();
//
//        } else {
//            ActivityCompat.requestPermissions(LoginActivity.this,
//                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


}