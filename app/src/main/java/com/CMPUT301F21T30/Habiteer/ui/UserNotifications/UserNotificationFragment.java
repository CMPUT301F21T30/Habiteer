package com.CMPUT301F21T30.Habiteer.ui.UserNotifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.databinding.FragmentUserNotificationBinding;

import java.util.ArrayList;

public class UserNotificationFragment extends Fragment {

    private ArrayList<String> requestList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the binding first
        View view = FragmentUserNotificationBinding.inflate(inflater, container, false).getRoot();
        RecyclerView requestRecycler = view.findViewById(R.id.notification_recycler);
        User currentUser = Session.getInstance().getUser();
        Session.getInstance().fetchRequests(); // fetch new requests from firebase
        requestList = currentUser.getFollowRequestsList();
        TextView emptyMessage =  view.findViewById(R.id.no_notifications_message);
        if (requestList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
        }
        else {
//        requestList.add("another person");
            emptyMessage.setVisibility(View.GONE);
            recyclerSetup(requestRecycler);
        }
        return view;
    }

    private void recyclerSetup(RecyclerView requestRecycler) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(Session.getInstance().getUser().getFollowRequestsList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        requestRecycler.setAdapter(notificationAdapter);
        requestRecycler.setLayoutManager(layoutManager);

        requestRecycler.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(requestRecycler.getContext(), layoutManager.getOrientation());
        requestRecycler.addItemDecoration(divider);

    }

}