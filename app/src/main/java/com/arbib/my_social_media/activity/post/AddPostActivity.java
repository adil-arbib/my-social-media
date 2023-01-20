package com.arbib.my_social_media.activity.post;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arbib.my_social_media.activity.main.MainActivity;
import com.arbib.my_social_media.api.PostApi;
import com.arbib.my_social_media.databinding.ActivityAddPostBinding;
import com.arbib.my_social_media.dto.PostDto;
import com.arbib.my_social_media.model.Post;
import com.arbib.my_social_media.model.User;
import com.arbib.my_social_media.service.RetrofitService;
import com.arbib.my_social_media.utils.BitmapConverter;
import com.arbib.my_social_media.utils.RealPathUtil;
import com.arbib.my_social_media.utils.UserSingleton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {

    private ActivityAddPostBinding binding;
    private User currentUser;
    private Intent intent;
    private final int STORAGE_PERMISSION_CODE = 1;
    private ActivityResultLauncher<String> mTakePhoto;
    private MultipartBody.Part filePart;
    private PostApi postApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentUser = UserSingleton.getUser();
        postApi = RetrofitService.getRetrofit().create(PostApi.class);

        showUserData();

        goBack();

        binding.iconAddPost.setOnClickListener(v -> selectImage());

        binding.iconCancel.setOnClickListener(v -> {
            filePart = null;
            binding.postPic.setImageResource(0);
            binding.iconCancel.setVisibility(View.GONE);
        });

        btnClickListener();

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    try{
                        File file = new File(RealPathUtil.getRealPath(AddPostActivity.this, result));
                        Log.e("size : ", file.length()+"");
                        filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//                    bi.progressBar.setVisibility(View.VISIBLE);
                        Log.e("url : ", result.toString());
                        int width = binding.postPic.getWidth();
                        int height = binding.postPic.getHeight();
                        Glide.with(getApplicationContext()).load(result)
                                .apply(new RequestOptions().override(width, height)).into(binding.postPic);
                        binding.iconCancel.setVisibility(View.VISIBLE);
                    }catch (Exception e) {
                        Log.e("error : ",e.getMessage());
                    }
                });
    }

    private void btnClickListener() {
        binding.btnPost.setOnClickListener(v->{
            String content = binding.edtPost.getText().toString();
            if(content.isEmpty() && filePart == null) {
                Toast.makeText(this, "No data to post", Toast.LENGTH_SHORT).show();
            }else {
                PostDto postDto = new PostDto(content,currentUser.getId());
                savePost(postDto);
            }
        });
    }

    private void savePost(PostDto postDto) {
        binding.progressBar.setVisibility(View.VISIBLE);


        postApi.addPost(postDto).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if( response.code() == 200) {
                    assert response.body() != null;
                    if(filePart != null) savePic(response.body().getId());
                    else {
                        Toast.makeText(AddPostActivity.this, "Posted successfully !!", Toast.LENGTH_SHORT).show();
                        goBackToMain();
                    }

                }else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddPostActivity.this, "an error was occurred !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "an error was occurred !!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void savePic(Long id){
        postApi.setPostPic(id, filePart).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.code()==200){
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddPostActivity.this, "Posted successfully !!", Toast.LENGTH_SHORT).show();
                    goBackToMain();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "an error was occurred !!", Toast.LENGTH_SHORT).show();
                Log.e("error 1 : ",t.getMessage());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void goBackToMain() {
        intent = new Intent(AddPostActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void selectImage() {

        if (ContextCompat.checkSelfPermission(AddPostActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            mTakePhoto.launch("image/*");

        } else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddPostActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(AddPostActivity.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(AddPostActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(AddPostActivity.this,
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


    private void goBack() {
        binding.iconBack.setOnClickListener(v->{
            goBackToMain();
        });
    }

    private void showUserData() {
        if(currentUser.getPicture() != null) {
            Bitmap btm = BitmapConverter.convertStringToBitmap(currentUser.getPicture());
            binding.imgProfile.setImageBitmap(btm);
        }
        binding.txtUsername.setText(currentUser.getFirstName()+" "+currentUser.getLastName());
    }




}