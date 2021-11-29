package com.CMPUT301F21T30.Habiteer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class holds the current user's User object, which contains the user's
 * habits, following and followers, and blocked accounts. It is a Singleton class
 * which allows global access to the User object.
 * Handles getting and setting of Habits on Firebase
 */
public class Session {
    private FirebaseFirestore db;
    private User user;
    private static Session instance = null;
    private HashMap<String,Habit> habitHashMap;
    private ArrayList<Event> habitEventsList;

    /**
     * Singleton Session constructor
     * Reads data from Users collection in Firestore and converts to User object
     * 
     * @param email, which is the document name in firestore
     */
    private Session(String email, Context context) {
        habitEventsList = new ArrayList<>();
        habitHashMap = new HashMap<String,Habit>();
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

    /**
     * adds a habit into Firestore.
     * @param habit a Habit object.
     */
    public void addHabit(Habit habit) {
        /* Add to in-app list; use title as a temp ID */
        habit.setId(habit.getHabitName());
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
                        user.getHabitIdList().add(habitID); // add the ID to local habit ID list

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
                                if (habitEventsList.get(j).getId() == habit.getEventIdList().get(finalI))
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

    public ArrayList<Event> getEventList() {
        return this.habitEventsList;
    }

    public HashMap<String,Habit> getHabitHashMap() {
        return this.habitHashMap;
    }
}
