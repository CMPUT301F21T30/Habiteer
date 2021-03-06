package com.CMPUT301F21T30.Habiteer.ui.Follow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
;

import java.util.List;

/**
 * Adapter for searchList which displays the result of the performed search
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    private List<User> searchList;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public SearchUserAdapter(FragmentActivity activity, List<User> searchList) {
        this.searchList = searchList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView userNameView;
        private int selectedIndex;

        public ViewHolder(final View view) {
            super(view);

            userNameView = view.findViewById(R.id.user_name);
            view.setOnClickListener(this);
        }

        /**
         * This method starts the FollowUser Activity when a search result is clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            //Take to the user's profile
//            notifyItemChanged(selectedIndex);
            selectedIndex = getLayoutPosition();
            User currentUser = searchList.get(selectedIndex);


            // Create new intent to start follow user activity
            Intent intent = new Intent(view.getContext(), FollowUserActivity.class);
            intent.putExtra("UserObj",currentUser); // pass the clicked user to follow user activity
            view.getContext().startActivity(intent); // start the follow user activity
        }
    }

    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ///holder.itemView.setSelected(selectedIndex == position);
        //String userName = searchList.get(position).getEmail();
        //holder.userNameView.setText(userName);

        User user = searchList.get(position);
        holder.userNameView.setText(user.getEmail());

    }

    /**
     * This method returns the total number of search results
     * @return searchList.size()
     */
    @Override
    public int getItemCount() {
        return searchList.size();
    }
}