package com.CMPUT301F21T30.Habiteer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.databinding.FragmentItemBinding;
import com.CMPUT301F21T30.Habiteer.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MynotificationRecyclerViewAdapter extends RecyclerView.Adapter<MynotificationRecyclerViewAdapter.ViewHolder> {

    private final List<User> mUsers;

    public MynotificationRecyclerViewAdapter(List<User> items) {

        mUsers = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        holder.userName.setText(mUsers.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //public final TextView mIdView;
       // public final TextView mContentView;
        //public PlaceholderItem mItem;
        public TextView userName;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            //mIdView = binding.itemNumber;
            //mContentView = binding.content;
        }

        /**
         * don't know if we need this or not
         * @return
         */
        /**
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        **/

    }
}