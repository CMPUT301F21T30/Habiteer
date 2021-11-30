package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserNotification extends AppCompatActivity {

    private RecyclerView requestRecycler;
    private ArrayList<String> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        requestRecycler = findViewById(R.id.notification_recycler);
        User currentUser = Session.getInstance().getUser();
        requestList = currentUser.getFollowRequestsList();
//        requestList.add("another person");
        recyclerSetup();

    }

    private void recyclerSetup() {
        NotificationAdapter notificationAdapter = new NotificationAdapter(requestList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        requestRecycler.setAdapter(notificationAdapter);
        requestRecycler.setLayoutManager(layoutManager);

        requestRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(requestRecycler.getContext(), layoutManager.getOrientation());
        requestRecycler.addItemDecoration(divider);

    }

}