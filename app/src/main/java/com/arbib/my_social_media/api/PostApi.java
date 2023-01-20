package com.arbib.my_social_media.api;

import com.arbib.my_social_media.dto.PostDto;
import com.arbib.my_social_media.model.Post;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {


    @POST("/api/v1/posts/add-post")
    Call<Post> addPost(@Body PostDto postDto);

    @Multipart
    @PUT("/api/v1/posts/{id}/set-image")
    Call<Post> setPostPic(@Path("id") Long id,
                                         @Part MultipartBody.Part file);
}
