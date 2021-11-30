package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class UserNotification extends AppCompatActivity {

    private RecyclerView requestRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        requestRecycler = findViewById(R.id.notification_recycler);
    }
}