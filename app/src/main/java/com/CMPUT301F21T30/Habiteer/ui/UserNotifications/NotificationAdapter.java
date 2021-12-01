package com.CMPUT301F21T30.Habiteer.ui.UserNotifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<String> requested;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public NotificationAdapter(ArrayList<String> requested){
        this.requested = requested;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_follow_notification, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(requested.get(position));
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
                    String selectedUserEmail =  requested.get(getLayoutPosition());
                    acceptRequest(selectedUserEmail);
                    notifyItemRemoved(getLayoutPosition());
                    requested = Session.getInstance().getUser().getFollowRequestsList(); // force update recyclerview
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selectedUserEmail =  requested.get(getLayoutPosition());
                    rejectRequest(selectedUserEmail);
                    notifyItemRemoved(getLayoutPosition());
                    requested = Session.getInstance().getUser().getFollowRequestsList(); // force update recyclerview
                }
            });
        }


    }

    public void acceptRequest( String email){

        Session.getInstance().addFollower(email);
        Session.getInstance().removeFollowRequest(email);



    }

    public void rejectRequest (String email){

        Session.getInstance().removeFollowRequest(email);
    }
}
