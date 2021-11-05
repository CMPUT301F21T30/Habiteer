package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.CMPUT301F21T30.Habiteer.ui.habit.Habit;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class EditHabitFragment extends Fragment {

    private com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitModel mViewModel;

    public static EditHabitFragment newInstance() {
        return new EditHabitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_habit, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitModel.class);

        // get the selected habit
        int habitIndex = requireActivity().getIntent().getExtras().getInt("habitIndex");
        Habit selectedHabit = Session.getInstance().getUser().getHabitList().get(habitIndex);

        TextInputLayout habitDateInput = view.findViewById(R.id.textInput_habitEndDate);


        // fill in the text fields with existing details
        fillInHabitDetails(view,habitDateInput,selectedHabit);

        habitDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide keyboard

                InputMethodManager in = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Edit end date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();
                picker.show(getChildFragmentManager(), "date");
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
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
                        // send dates to the text field
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

        // get existing data
        String oldName = habit.getHabitName();
        String oldReason = habit.getReason();

        SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        String oldDate = simpleFormat.format(habit.getEndDate()); // date as a string using the above format

        // set text to existing data
        nameInput.getEditText().setText(oldName);
        reasonInput.getEditText().setText(oldReason);
        habitDateInput.getEditText().setText(oldDate);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextInputLayout NameBox = requireView().findViewById(R.id.textInput_habitName);
        TextInputLayout reasonBox = requireView().findViewById(R.id.textInput_habitReason);


        switch (item.getItemId()) {

            case R.id.button_addHabit:
                // edit the habit
                String habitName = NameBox.getEditText().getText().toString();
                String reason = reasonBox.getEditText().getText().toString();


                int habitIndex = requireActivity().getIntent().getExtras().getInt("habitIndex");
                Habit currentHabit  = Session.getInstance().getUser().getHabitList().get(habitIndex);
                if (mViewModel.getEndDate() != null) { // Set the date only if it was changed
                    Date endDate = mViewModel.getEndDate();
                    currentHabit.setEndDate(endDate);
                }

                currentHabit.setHabitName(habitName);
                currentHabit.setReason(reason);
                requireActivity().finish();
                Intent intent = new Intent(getContext(), ViewHabitActivity.class);
                intent.putExtra("habitIndex",habitIndex); // include the index of the habit
                startActivity(intent);
//

                return true;
        }
        return false;
    }

}