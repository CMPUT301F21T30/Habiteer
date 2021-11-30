package com.CMPUT301F21T30.Habiteer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;

import java.util.ArrayList;

public class UserNotification extends AppCompatActivity {

    private RecyclerView requestRecycler;
    private User currentUser;
    private ArrayList<User> requestedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        requestRecycler = findViewById(R.id.notification_recycler);
        currentUser = Session.getInstance().getUser();
        requestedList = currentUser.getRequestedList();

        recyclerSetup();

    }

    private void recyclerSetup() {
        NotificationAdapter notificationAdapter = new NotificationAdapter(requestedList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notificationAdapter.setLayoutManager(layoutManager);
        requestRecycler.setItemAnimator(new DefaultItemAnimator());
        requestRecycler.setAdapter(notificationAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(notificationAdapter.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        requestRecycler.addItemDecoration(divider);

    }
}