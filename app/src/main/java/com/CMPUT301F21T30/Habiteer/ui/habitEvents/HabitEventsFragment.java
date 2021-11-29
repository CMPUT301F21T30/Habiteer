package com.CMPUT301F21T30.Habiteer.ui.habitEvents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * To create a calendar view that will allow the user to select
 * specific days to display all habit events that occurred on that date
 */
public class HabitEventsFragment extends Fragment
{
    private ArrayAdapter<Event> habitEventAdapter;
    ListView eventsList;
    private TextView monthYearText;
    private CalendarView calendar;
    private LocalDate selectedDate;
    String date = "";
    String TAG = "Sample";
    Session session = Session.getInstance();
    Context context;
    ArrayList<Event> filteredList;
    ArrayList<Event> eventList;
//    private Object HabitEventAdapter;

//    DatePicker datePicker;

    /**
     * To set view for habit event denoting layout
     * and actively listen for date change on calendar view,
     * get new date every time user clicks on new date on calendar view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_events, container, false);
        super.onCreate(savedInstanceState);

//        date = getArguments().getString("todayDate");
//        Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();

        // To set calendar view
        context = container.getContext();
        selectedDate = LocalDate.now();
        calendar = root.findViewById(R.id.calendarView);
        filteredList = new ArrayList<>();
        eventList = Session.getInstance().getEventList();
        eventsList = root.findViewById(R.id.event_list);
        habitEventAdapter = new HabitEventAdapter(context, filteredList);
        eventsList.setAdapter(habitEventAdapter);
        date = selectedDate.getMonthValue() + "/"
                + selectedDate.getDayOfMonth() + "/" + selectedDate.getYear();
        eventList = Session.getInstance().getEventList();
        filteredList.clear();
        for (int i = 0; i < eventList.size(); i++)
        {
            Log.d(TAG, eventList.get(i).getMakeDate());
            Log.d(TAG, date);

            if (eventList.get(i).getMakeDate().equals(date))
            {
                filteredList.add(eventList.get(i));

            }
        }
        habitEventAdapter.notifyDataSetChanged();

        // To set on date change listener on calendar view
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                date = month + "/" + dayOfMonth + "/" + year;
//                habitEventAdapter.notifyDataSetChanged();
                eventList = Session.getInstance().getEventList();
                filteredList.clear();
                habitEventAdapter.notifyDataSetChanged();
                for (int i = 0; i < eventList.size(); i++)
                {
                    Log.d(TAG, date);
                    Log.d(TAG, eventList.get(i).getMakeDate());
                    if (eventList.get(i).getMakeDate().equals(date))
                    {
                        filteredList.add(eventList.get(i));

                    }
                }
                Log.d(TAG, String.valueOf(filteredList.size()));

                habitEventAdapter.notifyDataSetChanged();
                //session.getUser().getHabitList();
            }
        });

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Event item = filteredList.get(index);
                Intent intent = new Intent(context, EditHabitEventActivity.class);
                intent.putExtra("event", item);
//                Log.d(TAG, item.getHabitId());
                //intent.putExtra("eventDate", todayDate);
                startActivity(intent);
                filteredList.clear();
                habitEventAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }}

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        //HabitEventAdapter HabitEventAdapter = new HabitEventAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        //calendarRecyclerView.setAdapter(HabitEventAdapter);
    }

        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Event item = filteredList.get(index);
                Intent intent = new Intent(context, EditHabitEventActivity.class);
                intent.putExtra("event", item);
                //Log.d(TAG, item.getHabitId());
                //intent.putExtra("eventDate", todayDate);
                startActivity(intent);
                filteredList.clear();
                habitEventAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

}*/
