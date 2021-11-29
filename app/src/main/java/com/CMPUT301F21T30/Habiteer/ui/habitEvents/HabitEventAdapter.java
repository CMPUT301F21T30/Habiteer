package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.CMPUT301F21T30.Habiteer.R;

import java.util.ArrayList;
import java.util.List;

public class HabitEventAdapter extends ArrayAdapter<Event> {
    private List<Event> eventsArrayList;
    private Context context;

    public HabitEventAdapter(Context context, ArrayList<Event> eventsArrayList){
        super(context, 0, eventsArrayList);
        this.eventsArrayList = eventsArrayList;
        this.context = context;
    }

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
