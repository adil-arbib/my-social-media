package com.arbib.my_social_media.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit;

    private static void initializeRetrofit() {


        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.156:6060")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) initializeRetrofit();
        return retrofit;
    }
}