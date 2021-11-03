package com.CMPUT301F21T30.Habiteer.ui.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;

import java.util.List;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.ViewHolder> {


    List<String> dataUserName;
    Context context;
    OnUserListener onUserListener;

    public  FollowingListAdapter(Context ct, List<String> dataUserName){

        context = ct;
        this.dataUserName = dataUserName;

        onUserListener = (FollowingList)context;


    }

    @NonNull
    @Override
    public FollowingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_following, parent, false);
        return new FollowingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingListAdapter.ViewHolder holder, int position) {
        holder.userName.setText(dataUserName.get(position));

        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserListener.onUserSelected(dataUserName.get(holder.getAbsoluteAdapterPosition()));
            }
        });



    }

    @Override
    public int getItemCount() {

        return dataUserName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.content_following);


        }

    }

    public interface OnUserListener{
        void onUserSelected (String userName);

    }
}
