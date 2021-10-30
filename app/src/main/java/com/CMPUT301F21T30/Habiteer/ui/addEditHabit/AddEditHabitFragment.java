package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import static androidx.core.util.Pair.create;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.util.Pair;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.CMPUT301F21T30.Habiteer.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

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
        habitDateInput.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // hide keyboard

                    InputMethodManager in = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("Select dates")
                            .setSelection( Pair.create(
                                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                    MaterialDatePicker.todayInUtcMilliseconds()
                            ))
                            .build();
                    picker.show(getChildFragmentManager(),"date");
                    picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair>() {
                        @Override
                        public void onPositiveButtonClick(Pair selection) {
                            // Get the offset from our timezone and UTC.
                            TimeZone timeZoneUTC = TimeZone.getDefault();
                            // It will be negative, so that's the -1
                            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                            // Create a date format, then a date object with our offset
                            SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                            Date StartDate = new Date((long) selection.first + offsetFromUTC);
                            Date EndDate = new Date((long) selection.second + offsetFromUTC);
                            habitDateInput.getEditText().setText(simpleFormat.format(StartDate) + " — " + simpleFormat.format(EndDate) );

//                            habitDateInput.getEditText().setText(picker.getHeaderText());
                            habitDateInput.clearFocus();
                        }
                    });
            }
        });



        return view;

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(AddEditHabitModel.class);
//        // TODO: Use the ViewModel
//    }

}