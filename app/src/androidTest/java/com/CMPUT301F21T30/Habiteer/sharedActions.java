package com.CMPUT301F21T30.Habiteer;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;

import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class sharedActions {
    /**
     * actions to login to the app
     * @param solo
     */
    public static void login(Solo solo) {
        solo.clickOnView(solo.getView(R.id.goToLoginBtn));
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "tester@robotium.com");
        solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");
        solo.clickOnView(solo.getView(R.id.loginBtn));
        solo.assertCurrentActivity("Main Activity", MainActivity.class);
    }
    public static void addHabit(Solo solo) {
        // select date
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
        solo.clickOnButton(1);
        solo.clickOnButton(2);
        solo.clickOnText("Save");

        // fill in name and reason
        TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
        TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);
        solo.enterText(nameField.getEditText(),"Run");
        solo.enterText(reasonField.getEditText(),"Get fit");
        // fill in day of week
        MaterialDayPicker dayPicker = (MaterialDayPicker) solo.getView(R.id.AddEdit_day_picker);
        solo.clickOnView(dayPicker.getChildAt(0)); // click on a day
        //save
        solo.clickOnText("Save");
    }
    public static void deleteHabit(Solo solo){
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.clickOnView(solo.getView(R.id.delete));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * Clicks "save", and make sure the current activity does not change.
     * Used for testing input validation in Add and Edit habit screens
     * @param solo
     */
    public static void saveAndCheckActivityChange(Solo solo) {
        solo.clickOnText("Save"); // click on the save button without entering anything
        solo.assertCurrentActivity("Wrong Activity!", AddEditHabitActivity.class); // activity should not change, since no empty fields allowed
    }
}
