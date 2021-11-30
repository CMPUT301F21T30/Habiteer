package com.CMPUT301F21T30.Habiteer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<User> requested;
    private User currentUser;

    public NotificationAdapter(ArrayList<User> requested){
        this.requested = requested;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_content, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(requested.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return requested.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private Button accept;
        private Button reject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            accept = itemView.findViewById(R.id.button_accept);
            reject = itemView.findViewById(R.id.button_deny);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //acceptRequest(User user);
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //rejectRequest(User user);
                }
            });
        }


    }

    public void acceptRequest( User requestUser){
        ArrayList<User> followersList = new ArrayList<>();
        ArrayList<User> followingList = new ArrayList<>();
        currentUser = Session.getInstance().getUser();
        followersList.add(requestUser);
        Session.getInstance().updateFollowerList(currentUser, followersList);
        followersList.add(currentUser);

        //user.get(pos).setFollowerList();

    }

    public void rejectRequest (User requestUser){

        currentUser.getRequestedList().remove(requestUser);
        Session.getInstance().updateRequestedList(currentUser, currentUser.getRequestedList());
    }
}
