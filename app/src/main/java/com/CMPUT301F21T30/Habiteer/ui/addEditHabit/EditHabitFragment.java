package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ca.antonious.materialdaypicker.MaterialDayPicker;

/**
 * This fragment handles the editing of habits from the habit list. Allows the user to edit name, end date, days of the week, and habit reason.
 * Known issues:
 *  See Github #44, date picker can sometimes be one day off due to timezone issues
 *  TODO: Days of the week picker yet to be implemented
 */
public class EditHabitFragment extends Fragment {

    private com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitModel mViewModel;
    private String habitID;
    public static EditHabitFragment newInstance() {
        return new EditHabitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_habit, container, false);
        setHasOptionsMenu(true); // enable the "SAVE" button on the top right
        mViewModel = new ViewModelProvider(this).get(com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitModel.class);

        // get the selected habit
        habitID = requireActivity().getIntent().getExtras().getString("habitID");

        Habit selectedHabit = Session.getInstance().getHabitHashMap().get(habitID);

        TextInputLayout habitDateInput = view.findViewById(R.id.textInput_habitEndDate);


        // fill in the text fields with existing details
        fillInHabitDetails(view,habitDateInput,selectedHabit);

        habitDateInput.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide keyboard
                InputMethodManager in = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // create single date picker
                MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Edit end date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                picker.show(getChildFragmentManager(), "date");

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    // when the save button is clicked in the Datepicker
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        // Get the offset from our timezone and UTC.
                        TimeZone timeZoneUTC = TimeZone.getDefault();
                        // It will be negative, so that's the -1
                        int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                        // Create a date format, then a date object with our offset
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        Date EndDate = new Date(selection + offsetFromUTC);
                        // send data to viewmodel
                        mViewModel.setEndDate(EndDate);
                        // send date to the text field
                        habitDateInput.getEditText().setText(simpleFormat.format(EndDate));
                        habitDateInput.clearFocus();

                    }
                });
            }
        });
        return view;

    }/**
     fill in the text fields with existing details
        **/
    private void  fillInHabitDetails(View view,TextInputLayout habitDateInput,Habit habit) {
        TextInputLayout nameInput = view.findViewById(R.id.textInput_habitName);
        TextInputLayout reasonInput = view.findViewById(R.id.textInput_habitReason);
        MaterialDayPicker dayPicker = view.findViewById(R.id.AddEdit_day_picker);

        // get existing data
        String oldName = habit.getHabitName();
        String oldReason = habit.getReason();
        List<MaterialDayPicker.Weekday> weekdayList = habit.getWeekdayList();


        SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String oldDate = simpleFormat.format(habit.getEndDate()); // date as a string using the above format

        // set text to existing data
        nameInput.getEditText().setText(oldName);
        reasonInput.getEditText().setText(oldReason);
        habitDateInput.getEditText().setText(oldDate);
        dayPicker.setSelectedDays(weekdayList);

    }

    /**
     * Triggers when a button on the top action bar is pressed. In this case, it is only the save button.
     * @param item the menu item that was selected.
     * @return if the button was pressed or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextInputLayout nameBox = requireView().findViewById(R.id.textInput_habitName);
        TextInputLayout reasonBox = requireView().findViewById(R.id.textInput_habitReason);
        MaterialDayPicker dayPicker = getView().findViewById(R.id.AddEdit_day_picker);


        switch (item.getItemId()) {

            case R.id.button_addHabit:
                // edit the habit
                String habitName = nameBox.getEditText().getText().toString();
                String reason = reasonBox.getEditText().getText().toString();
                List<MaterialDayPicker.Weekday> weekdayList = dayPicker.getSelectedDays();

                // get selected habit from User
                Habit currentHabit  = Session.getInstance().getHabitHashMap().get(habitID);

                // Set the date only if it was changed
                if (mViewModel.getEndDate() != null) {
                    Date endDate = mViewModel.getEndDate();
                    currentHabit.setEndDate(endDate);
                }
                // set other data, with length limit
                currentHabit.setHabitName(habitName.substring(0,Math.min(habitName.length(),nameBox.getCounterMaxLength())));  // either the max length or string length, which one is smaller
                currentHabit.setReason(reason.substring(0,Math.min(reason.length(),reasonBox.getCounterMaxLength()))); // either the max length or string length, which one is smaller
                currentHabit.setWeekdayList(weekdayList);


                // call Session to update data on Firestore
                Session.getInstance().updateHabit(currentHabit);

                requireActivity().finish(); // close the activity

                // update and navigate back to view habit
                Intent intent = new Intent(getContext(), ViewHabitActivity.class);
                intent.putExtra("habitID",habitID); // include the index of the habit
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP); // update the existing view habit activity instead of making a new one
                startActivity(intent);
//

                return true;
        }
        return false;
    }

}