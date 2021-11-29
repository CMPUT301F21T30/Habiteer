package com.CMPUT301F21T30.Habiteer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {

    private ArrayList<User> followerUsers;

    public  FollowerAdapter(ArrayList<User> followerUsers){

        this.followerUsers = followerUsers;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followers_content, parent, false);
        return new FollowerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.follower.setText((followerUsers.get(position).getEmail()));

    }

    @Override
    public int getItemCount() {
        return followerUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView follower;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            follower = itemView.findViewById(R.id.user_name);

        }


    }
}
