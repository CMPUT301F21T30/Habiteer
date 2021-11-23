package com.CMPUT301F21T30.Habiteer.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<Event> eventArrayList;
    private ArrayList<User> followingList;

    public EventListAdapter(ArrayList<Event> eventArrayList, ArrayList<User> followingList){
        this.eventArrayList = eventArrayList;
        this.followingList = followingList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userEmail = followingList.get(position).getEmail();
        String eventTitle = eventArrayList.get(position).getEventName();
        String eventDate = eventArrayList.get(position).getMakeDate();// TODO: need to change the type of the object
        String eventComment = eventArrayList.get(position).getEventComment();

        holder.userName.setText(userEmail);
        holder.eventName.setText(eventTitle);
        holder.makeDate.setText(eventDate);
        holder.comments.setText(eventComment);

    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView eventName;
        private TextView makeDate;
        private TextView comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_textView);
            eventName = itemView.findViewById(R.id.eventName_textView);
            makeDate = itemView.findViewById(R.id.makeDate_textView);
            comments = itemView.findViewById(R.id.comment_textView);
        }
    }
}
