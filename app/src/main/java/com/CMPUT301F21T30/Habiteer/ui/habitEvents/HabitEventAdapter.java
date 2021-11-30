package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.Event;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.HabitEventAdapter;
import com.CMPUT301F21T30.Habiteer.ui.habitEvents.AddHabitEventActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


// To create a custom array adapter for storing and displaying habit events
public class HabitEventAdapter extends ArrayAdapter<Event> {
    private List<Event> eventsArrayList;
    private Context context;

    public HabitEventAdapter(Context context, ArrayList<Event> eventsArrayList){
        super(context, 0, eventsArrayList);
        this.eventsArrayList = eventsArrayList;
        this.context = context;
    }

    /**
     * To set the layout for habit event denoting as the user selects the date from the calendar view
     * to display only the event name on the list view for the events that occurred on the selected date
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habit_event_content, parent,false);
        }
        Event event = eventsArrayList.get(position);

        TextView eventName = view.findViewById(R.id.event_name_text);
        //TextView eventDate = view.findViewById(R.id.event_date_text);

        eventName.setText(event.getEventName());
        //eventDate.setText(event.getMakeDate());

        return view;
    }

}
