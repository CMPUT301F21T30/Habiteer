package com.CMPUT301F21T30.Habiteer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;
import java.util.LinkedHashMap;


/**
 * This class holds the current user's User object, which contains the user's
 * habits, following and followers, and blocked accounts. It is a Singleton class
 * which allows global access to the User object.
 * Handles getting and setting of Habits on Firebase
 * Also handles following other users on Firebase.
 */
public class Session {
    private FirebaseFirestore db;
    private User user;
    private static Session instance = null;
    private LinkedHashMap<String,Habit> habitHashMap;
    private ArrayList<Event> habitEventsList;


    /**
     * Singleton Session constructor
     * Reads data from Users collection in Firestore and converts to User object
     * 
     * @param email, which is the document name in firestore
     */
    private Session(String email, Context context) {
        habitEventsList = new ArrayList<>();
        habitHashMap = new LinkedHashMap<String,Habit>();
        db = FirebaseFirestore.getInstance();

        DocumentReference usersDocRef = db.collection("Users").document(email);
        usersDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                user.setEmail(usersDocRef.getId()); // document does not set email to User, so we set manually
                // Get habits that belong to User from Firestore and append to habitHashMap
                if (user.getHabitIdList().size() != 0) {
                    for (int i = 0; i < user.getHabitIdList().size(); i++) {
                        DocumentReference habitsDocRef = db.collection("Habits").document(user.getHabitIdList().get(i));
                        habitsDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Habit habit = documentSnapshot.toObject(Habit.class);
                                habitHashMap.put(habit.getId(),habit); // add habit to hashmap, with ID as the key
                                /* Adding events to HabitEventsList */
                                ArrayList<String> eventIDList = new ArrayList<>();
                                for (int j = 0; j < habit.getEventIdList().size(); j++) {
                                    eventIDList.add(habit.getEventIdList().get(j));
                                }
                                for (int i = 0; i < eventIDList.size(); i++) {
                                    DocumentReference eventsDocRef = db.collection("HabitEvents").document(eventIDList.get(i));
                                    eventsDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Event event = documentSnapshot.toObject(Event.class);
                                            habitEventsList.add(event);
                                            Log.d(TAG, String.valueOf(habitEventsList.size()));
                                        }
                                    });
                                }
                                /* Login */
                                Toast.makeText(context, "You have been logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // remove login activity and start main activity
                                context.startActivity(intent); // if logged in, go to the main activity
                            }
                        });
                    }
                }
                /* If user has no habits, login right away */
                else {
                    Toast.makeText(context, "You have been logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // remove login activity and start main activity
                    context.startActivity(intent); // if logged in, go to the main activity
                }
            }
        });

//        try {

//        }
//        catch (NullPointerException e) {
//            System.out.println("Habit ID list is empty: " + e);
//        }

    }

    /**
     * Used to instantiate the Session singleton.
     * @param email email of the logged in user
     * @param context android context used for starting activities
     * @return the Session object
     */
    public static Session getInstance(String email, Context context) {
        if (instance == null) {
            instance = new Session(email, context);
        }
        return instance;
    }

    /**
     * Used to get the Session singleton. Cannot be used if the Session is not instantiate with getInstance(String email, Context context) first.
     * @return the Session object
     */
    public static Session getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("Session not instantiated! Instantiate using getInstance(String email, Context context) before calling this.");
        }
        return instance;

    }
    /**
     * @return the User object for the currently logged in user.
     */
    public User getUser() {
        return this.user;
    }
    public void destroy() {
        Session.instance = null;
    }

    /**
     * adds a habit into Firestore.
     * @param habit a Habit object.
     */
    public void addHabit(Habit habit) {
        /* Add to in-app list; use title as a temp ID */
        String tempID = habit.getHabitName();
        habit.setId(tempID);
        user.getHabitIdList().add(tempID); // add the ID to local habit ID list
        habitHashMap.put(habit.getHabitName(),habit);

        /* Store onto Firebase Habits Collection */
        db.collection("Habits")
                .add(habit)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String habitID = documentReference.getId();
                        documentReference.update("id", habitID);
                        /* Set the ID to the one generated on Firebase */
                        habit.setId(habitID);
                        habitHashMap.put(habitID, habitHashMap.remove(habit.getHabitName())); // replace the temp ID with the real ID as the key in the hashmap
                        // replace ID in local habit ID list
                        user.getHabitIdList().remove(tempID);
                        user.getHabitIdList().add(habitID);

                        Log.d(TAG, "DocumentSnapshot successfully written! ID: " + habitID);
                        /* Store onto Firebase Users Collection */
                        db.collection("Users").document(user.getEmail()).update("habitIdList", FieldValue.arrayUnion(habitID))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated! ID: " + habitID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }
    /**
     * Deletes a habit from the user habit list.
     * @param habit a Habit object.
     */
    public void deleteHabit(Habit habit) {
        String habitID = habit.getId();
        /* Delete habit in in-app list/hashmap */
        habitHashMap.remove(habitID);

        ArrayList<String> habitIdList = user.getHabitIdList();
        habitIdList.remove(habitID);
        user.setHabitIdList(habitIdList);
        /* Deleting events for habit */
        for (int i = 0; i < habit.getEventIdList().size(); i++)
        {
            int finalI = i;
            db.collection("HabitEvents").document(habit.getEventIdList().get(i))
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted! ID: " + habit.getEventIdList().get(finalI));
                            /* Delete from EventsList */
                            for (int j = 0; j < habitEventsList.size(); j++) {
                                if (habitEventsList.get(j).getId().equals(habit.getEventIdList().get(finalI)))
                                    habitEventsList.remove(j);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
        }
        /* Delete on Firebase Habits Collection */
        db.collection("Habits").document(habitID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted! ID: " + habitID);
                        /* Delete from Firebase Users Collection */
                        db.collection("Users").document(user.getEmail()).update("habitIdList", user.getHabitIdList())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted! ID: " + habitID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }

    public void updateHabit(Habit habit) {
        String habitID = habit.getId();
        db.collection("Habits")
            .document(habitID)
            .set(habit)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written! ID: " + habitID);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error updating document", e);
            }
        });
    }
    public void swapHabitOrder(int initialPos, int newPos) {
        ArrayList<String> habitIdList = user.getHabitIdList();
        Collections.swap(habitIdList,initialPos,newPos);
        user.setHabitIdList(habitIdList);
        db.collection("Users").document(user.getEmail()).update("habitIdList", habitIdList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Index " + initialPos + " and " + newPos + " swapped!") ;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting document", e);
            }
        });

    }

    /**
     * To store a habit event on the database
     * @param event
     * @param habitID
     */
    public void addEvent(Event event, String habitID) {
        /* Getting habit id from Firebase */
        Habit currentHabit = Session.getInstance().getHabitHashMap().get(habitID);

        /* Store onto Firebase Events Collection */
        db.collection("HabitEvents")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String eventID = documentReference.getId();
                        currentHabit.getEventIdList().add(eventID);
                        documentReference.update("id", eventID);
                        event.setId(eventID);
                        habitEventsList.add(event);
                        Log.d(TAG, "DocumentSnapshot successfully written! ID: " + eventID);
                        /* Store onto Firebase Users Collection */
                        db.collection("Habits").document(currentHabit.getId()).update("eventIdList", FieldValue.arrayUnion(eventID))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated! ID: " + eventID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }

    /**
     * To update an event on the database
     * @param event
     */
    public void updateEvent(Event event) {
        for (int i = 0; i < habitEventsList.size(); i++)
        {
            if (habitEventsList.get(i).getId().equals(event.getId()))
            {
                habitEventsList.remove(i);
                habitEventsList.add(event);
                String habitEventID = event.getId();
                db.collection("HabitEvents")
                        .document(habitEventID)
                        .set(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written! ID: " + habitEventID);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }

        }

    }

    /**
     * To delete an event from the database
     * @param event
     */
    public void deleteEvent(Event event) {
        /* Delete habit event */
        for (int i = 0; i < habitEventsList.size(); i++)
        {
            if (habitEventsList.get(i).getId().equals(event.getId()))
            {
                habitEventsList.remove(i);

                Habit currentHabit = Session.getInstance().getHabitHashMap().get(event.getHabitId());
                currentHabit.getEventIdList().remove(event.getId());
                /* Delete on Firebase Habits Collection */
                db.collection("HabitEvents").document(event.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted! ID: " + event.getId());
                                /* Delete from Firebase Users Collection */
                                db.collection("Habits").document(currentHabit.getId()).update("eventIdList", currentHabit.getEventIdList())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted! ID: " + event.getId());
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        }

    }


    /**
     * Given a User, this method gets the User's habits from Firebase. This is used to view habits of users that are not the current logged in user. Used in Follow classes.
     * @param user
     * @return
     */
    public HashMap<String,Habit> getOthersHabits(User user) {
        HashMap<String,Habit> userHabits = new HashMap<>();
        if (user.getHabitIdList().size() != 0) {
            for (int i = 0; i < user.getHabitIdList().size(); i++) {
                DocumentReference habitsDocRef = db.collection("Habits").document(user.getHabitIdList().get(i));
                habitsDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Habit habit = documentSnapshot.toObject(Habit.class);
                        userHabits.put(habit.getId(), habit); // add habit to hashmap, with ID as the key
                    }
                });
            }
        }
        return userHabits;
    }

    public ArrayList<Event> getEventList() {
        return this.habitEventsList;
    }

    public LinkedHashMap<String,Habit> getHabitHashMap() {
        return this.habitHashMap;
    }

    /**
     * Upload the image to firebase
     * @param fileName - filename of the image
     * @param linkUri - local path of the image
     * @param referenceStorage - reference in firebase storage to upload the image to
     * @return returnUriLink
     */

    public Uri uploadImageToFirebase(String fileName, Uri linkUri, StorageReference referenceStorage ){


        Uri returnUriLink = null;

        StorageReference imageUpload = referenceStorage.child("images/" + fileName);

        UploadTask taskUpload = imageUpload.putFile(linkUri);

        while (!taskUpload.isComplete()) ;
        if (taskUpload.isSuccessful()) {

            Task<Uri> uriTask = imageUpload.getDownloadUrl();

            while (!uriTask.isComplete()) ;

            if (uriTask.isSuccessful()) {

                Uri uriTaskResult = uriTask.getResult();

                returnUriLink =  uriTaskResult;

            } else {
                Log.d("Upload", "couldn't get download url");


            }
        } else {
            Log.d("Upload", "couldn't upload url");
        }
        return returnUriLink;
    }

    /**
     * Wrapper function for updateFollowerList(), adds an email to the current user's follower list, and on firebase
     * @param email - target user's email, to add to the list
     */
    public void addFollower(String email) {
        updateFollowerList(email,true);
    }
    /**
     * Wrapper function for updateFollowerList(), removes an email from the current user's follower list, and on firebase
     * @param email- target user's email, to remove from the list
     */
    public void removeFollower(String email) {
        updateFollowerList(email,false);
    }

    /**
     * Updates following lists for current user and target user.
     * @param thatEmail - target user email.
     * @param add - Whether to add or remove a follower.
     */
    private void updateFollowerList(String thatEmail,boolean add){
        String thisEmail = this.user.getEmail();
        ArrayList<String> followerList = this.user.getFollowerList();
        if(add) {
            followerList.add(thatEmail);
        }
        else {
            followerList.remove(thatEmail);
        }
        // update local follower list
        this.user.setFollowerList(followerList);

        CollectionReference collection = db.collection("Users");
        //update the this user's firebase
        collection.document(thisEmail).update("followerList", followerList)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Document successfully written for followers list");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error while updating follower", e);
                }
            });

        // update the other user's following list
        DocumentReference doc = collection.document(thatEmail);
        if (add) {
            doc.update("followingList",FieldValue.arrayUnion(thisEmail));
        }
        else {
            doc.update("followingList",FieldValue.arrayRemove(thisEmail));
        }


        }


    public void updateFollowingList(User user, ArrayList<String>followingList){
        user.setFollowingList(followingList);

        //stores into firebase
        db.collection("Users").document(user.getEmail()).update("Following List", user.getFollowingList())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Document successfully written for following list");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG ,"Error while adding following", e);
            }
        });
    }

    /**
     * Wrapper method for updateSentRequest(), adds the target email into this user's sent request list, and adds this user's email into the target user's follow request list on Firebase.
     * @param email - the target user's email
     */
    public void addSentRequest(String email){updateSentRequestsList(email,true);}
    public void removeSentRequest(String email){updateSentRequestsList(email,false);}

    /**
     * Updates the sent request lists for the current user, and updates the follow request list for the target user on Firebase.
     * @param targetEmail target user
     * @param add boolean, whether to add or remove a request
     */
    public void updateSentRequestsList(String targetEmail,boolean add){
        String thisEmail = this.user.getEmail();
        ArrayList<String> sentRequests = this.user.getSentRequestsList();

        if (add){
            sentRequests.add(targetEmail);
        }
        else {
            sentRequests.remove(targetEmail);
        }
        this.user.setSentRequestsList(sentRequests);

        CollectionReference collection = db.collection("Users");
        // updates the logged in user's sent requests on firebase
        collection.document(thisEmail).update("sentRequestsList", sentRequests)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Document successfully written for request list");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG ,"Error while adding user request", e);
            }
        });
        DocumentReference targetDoc = collection.document(targetEmail);
        FieldValue fieldValue;

        if (add){
            fieldValue =  FieldValue.arrayUnion(thisEmail);
        }
        else {
            fieldValue = FieldValue.arrayRemove(thisEmail);
        }

        //update the other user's firebase
        targetDoc.update("followRequestsList",fieldValue)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Document successfully written for request list");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG ,"Error while adding user request", e);
            }
        });

    }

    /**
     * Removes a follow request from the current and target user's firebase entries.
     * @param targetEmail - the target user's email
     */
    public void removeFollowRequest(String targetEmail) {
        String thisEmail = this.user.getEmail();
        ArrayList<String> followRequests = this.user.getFollowRequestsList();
        followRequests.remove(targetEmail);
        this.user.setFollowRequestsList(followRequests); // update local list

        CollectionReference collection = db.collection("Users");
        // updates the logged in user's follow requests on firebase
        collection.document(thisEmail).update("followRequestsList",followRequests);
        // update the target users sent requests on firebase
        collection.document(targetEmail).update("sentRequestsList",FieldValue.arrayRemove(thisEmail));

    }

    /**
     * Update the user's local follow request list with the one on Firebase.
     */
    public void fetchRequests() {
        db.collection("Users").document(this.user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> requestList =  (ArrayList<String>) documentSnapshot.get("followRequestsList");

                Session.getInstance().user.setFollowRequestsList(requestList);
            }
        });


    }
}
