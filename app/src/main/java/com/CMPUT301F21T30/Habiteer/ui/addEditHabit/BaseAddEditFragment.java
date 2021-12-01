package com.CMPUT301F21T30.Habiteer.ui.addEditHabit;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.CMPUT301F21T30.Habiteer.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ca.antonious.materialdaypicker.MaterialDayPicker;


public class BaseAddEditFragment extends Fragment {
    public boolean hasEmptyFields(TextInputLayout[] boxes, MaterialDayPicker dayPicker) {
        boolean emptyFields = false;
        TextView errorText = getView().findViewById(R.id.dayPicker_ErrorText);

        for (TextInputLayout box:boxes) {
            String boxContents = Objects.requireNonNull(box.getEditText()).getText().toString();
            if (TextUtils.isEmpty(boxContents)) {
                // if empty, show error message
                box.setError(Objects.requireNonNull(box.getHint()).toString() + " field cannot be empty.");
                box.setErrorEnabled(true);
                emptyFields = true;

            }
            else {
                // remove error messages
                box.setErrorEnabled(false);
            }
        }
        if (dayPicker.getSelectedDays().isEmpty()) {
            errorText.setVisibility(View.VISIBLE); // show error message on day picker
            emptyFields = true;
        }
        else {
            errorText.setVisibility(View.GONE); // remove error message on day picker
        }
        return emptyFields;
    }
}
