package com.CMPUT301F21T30.Habiteer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<String> requested;
    private User currentUser;

    public NotificationAdapter(ArrayList<String> requested){

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_content, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(requested.get(position));

//        Code for getting user given an email
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection("Users").document(requested).get();
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                currentUser = documentSnapshot.toObject(User.class);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

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

//        currentUser.getSentRequestsList().remove(requestUser);
//        Session.getInstance().followOtherUser(currentUser, currentUser.getSentRequestsList());
    }
}
