package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddEditHabitFragment extends Fragment  {

    private AddEditHabitModel mViewModel;

    public static AddEditHabitFragment newInstance() {
        return new AddEditHabitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addedithabit, container, false);
        TextInputLayout habitDateInput = view.findViewById(R.id.textInput_habitStartDate);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(AddEditHabitModel.class);
        habitDateInput.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // hide keyboard

                    InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    MaterialDatePicker<Pair<Long, Long>> picker = MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("Select dates")
                            .setSelection( Pair.create(
                                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                    MaterialDatePicker.todayInUtcMilliseconds()
                            ))
                            .build();
                    picker.show(getChildFragmentManager(),"date");
                    picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long> >() {
                        @Override
                        public void onPositiveButtonClick(Pair<Long,Long>  selection) {
                            // Get the offset from our timezone and UTC.
                            TimeZone timeZoneUTC = TimeZone.getDefault();
                            // It will be negative, so that's the -1
                            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                            // Create a date format, then a date object with our offset
                            SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                            Date StartDate = new Date(selection.first + offsetFromUTC);
                            Date EndDate = new Date(selection.second + offsetFromUTC);
                            // send data to viewmodel
                            mViewModel.setStartDate(StartDate);
                            mViewModel.setEndDate(EndDate);
                            // send dates to the text field
                            habitDateInput.getEditText().setText(MessageFormat.format("{0} — {1}", simpleFormat.format(StartDate), simpleFormat.format(EndDate)));
                            habitDateInput.clearFocus();
                        }

                    });
            }
        });



        return view;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextInputLayout NameBox = getView().findViewById(R.id.textInput_habitName);
        TextInputLayout reasonBox = getView().findViewById(R.id.textInput_habitReason);


        switch (item.getItemId()) {

            case R.id.button_addHabit:
                // create the new habit
                String habitName = NameBox.getEditText().getText().toString();
                String reason = reasonBox.getEditText().getText().toString();
                Date startDate = mViewModel.getStartDate();
                Date endDate = mViewModel.getEndDate();
                Habit newHabit = new Habit(habitName,startDate,endDate,reason);
                Session session = Session.getInstance();
                session.addHabit(newHabit);
                session.storeHabits(session.getUser().getHabitList());
                getActivity().finish();


                return true;
        }
        return false;
    }


}