package com.arbib.my_social_media.activity.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.activity.login.LoginActivity;
import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.adapters.ProfileViewPager;
import com.arbib.my_social_media.adapters.ViewPagerAdapter;
import com.arbib.my_social_media.api.UserApi;
import com.arbib.my_social_media.databinding.ActivityProfileBinding;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;
import com.arbib.my_social_media.utils.BitmapConverter;
import com.arbib.my_social_media.utils.Constants;
import com.arbib.my_social_media.utils.RealPathUtil;
import com.arbib.my_social_media.utils.UserSingleton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding profileBinding;
    private User user;
    private User currentUser;
    private ProfileViewPager profileViewPager;
    private ActivityResultLauncher<String> mTakePhoto;
    private final int STORAGE_PERMISSION_CODE = 1;
    private UserApi userApi;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = profileBinding.getRoot();
        setContentView(view);

        retrieveUser();

        currentUser = UserSingleton.getUser();

        profileViewPager = new ProfileViewPager(this);
        profileBinding.profileVPager.setAdapter(profileViewPager);

        profileBinding.txtUsername.setText(user.getFirstName()+" "+user.getLastName());
        profileBinding.iconBack.setOnClickListener(v->{
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        showUserPic();
        if (user.getId() != currentUser.getId()) {
            profileBinding.iconCam.setVisibility(View.GONE);
        }else camListener();



        // initialize userApi service
        retrofit = RetrofitService.getRetrofit();
        userApi = retrofit.create(UserApi.class);



        tabSelections();





    }

    private void tabSelections() {
        profileBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                profileBinding.profileVPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        profileBinding.profileVPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                profileBinding.tabLayout.getTabAt(position).select();
            }
        });
    }

    private void camListener() {
        profileBinding.iconCam.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                mTakePhoto.launch("image/*");

            } else {
                requestStoragePermission();
            }
        });

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    File file = new File(RealPathUtil.getRealPath(ProfileActivity.this, result));
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                    profileBinding.progressBar.setVisibility(View.VISIBLE);
                    userApi.updateImage(user.getId(), filePart).enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                            profileBinding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, "Updated Successfully !!", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                            Log.e("error", t.getMessage());
                        }
                    });

                });
    }

    private void showUserPic() {
        if(user.getPicture() != null) {
            Bitmap btm = BitmapConverter.convertStringToBitmap(user.getPicture());
            profileBinding.imgProfile.setImageBitmap(btm);
        }
    }

    private void retrieveUser() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra(Constants.USER_PROFILE);
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(ProfileActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



}