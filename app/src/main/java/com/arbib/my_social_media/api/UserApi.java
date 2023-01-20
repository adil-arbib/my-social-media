package com.arbib.my_social_media.api;

import com.arbib.my_social_media.dto.FollowDto;
import com.arbib.my_social_media.dto.LoginUser;
import com.arbib.my_social_media.dto.RegisterDto;
import com.arbib.my_social_media.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface UserApi {

    @Multipart
    @PUT("/api/v1/users/{id}/update-picture")
    Call<Map<String,String>> updateImage(@Path("id") Long id,
                          @Part MultipartBody.Part file);


    @GET("/api/v1/users/{id}")
    Call<User> getUser(@Path("id") Long id);


    @POST("/api/v1/auth/login-user")
    Call<User> login(@Body LoginUser loginUser);


    @POST("/api/v1/auth/register-user")
    Call<User> register(@Body RegisterDto registerDto);


    @GET("/api/v1/users/{id}/suggestions/{page}")
    Call<List<User>> getSuggestionsFriends(@Path("id") Long id,
                                           @Path("page") int page);

    @POST("/api/v1/users/add-following")
    public Call<String> addFollowing(@Body FollowDto followDto);

    @POST("/api/v1/users/remove-following")
    public Call<String> removeFollowing(@Body FollowDto followDto);

}
