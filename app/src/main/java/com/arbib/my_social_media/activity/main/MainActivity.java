package com.arbib.my_social_media.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.arbib.my_social_media.R;
import com.arbib.my_social_media.model.User;

public class MainActivity extends AppCompatActivity {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        currentUser = intent.getParcelableExtra("currentUser");
        System.out.println(currentUser);

    }
}