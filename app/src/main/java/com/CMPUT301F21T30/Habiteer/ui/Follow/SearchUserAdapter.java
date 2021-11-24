package com.CMPUT301F21T30.Habiteer.ui.Follow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.User;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.HabitAdapter;
;

import java.text.SimpleDateFormat;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    private List<User> searchList;
    private int selectedIndex = RecyclerView.NO_POSITION;

    public SearchUserAdapter(List<User> searchList) {
        this.searchList = searchList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView userNameView;

        public ViewHolder(final View view) {
            super(view);

            userNameView = view.findViewById(R.id.user_name);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.itemView.setSelected(selectedIndex == position);
        String userName = searchList.get(position).getEmail();
        holder.userNameView.setText(userName);


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}