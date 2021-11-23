package com.CMPUT301F21T30.Habiteer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.ViewHolder> {


    private List<User> followingUsers;

    private int selectedIndex = RecyclerView.NO_POSITION;

    public  FollowingListAdapter(List<User> followingUsers){

        this.followingUsers = followingUsers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_list_content, parent, false);
        return new FollowingListAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull FollowingListAdapter.ViewHolder holder, int position) {
        holder.followingUsers.setText((followingUsers.get(position).getEmail()));
    }

    @Override
    public int getItemCount() {

        return followingUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView followingUsers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            followingUsers = itemView.findViewById(R.id.user_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            notifyItemChanged(selectedIndex);
            selectedIndex = getLayoutPosition();
            System.out.println(selectedIndex);
            notifyItemChanged(selectedIndex);

            // Create new intent to start view others habit activity
            Intent intent = new Intent(view.getContext(), ViewOtherHabits.class);
            intent.putExtra("userIndex",selectedIndex); // pass through the index of the clicked item
            view.getContext().startActivity(intent); // start the view others habit activity
        }
    }


}