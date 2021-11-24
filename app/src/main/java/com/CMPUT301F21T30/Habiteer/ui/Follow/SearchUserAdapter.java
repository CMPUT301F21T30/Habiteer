package com.CMPUT301F21T30.Habiteer.ui.Follow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    private List<User> searchList;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public SearchUserAdapter(List<User> searchList) {
        this.searchList = searchList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView userName;

        public ViewHolder(final View view) {
            super(view);

            userName = view.findViewById(R.id.user_name);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Take to the user's profile
        }
    }

    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}