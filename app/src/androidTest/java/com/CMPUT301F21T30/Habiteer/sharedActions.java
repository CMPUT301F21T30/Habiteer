package com.CMPUT301F21T30.Habiteer;

import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

public class sharedActions {
    public static void addHabit(Solo solo) {
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
        solo.clickOnButton(1);
        solo.clickOnButton(2);
        solo.clickOnText("Save");

        TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
        TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);
        solo.enterText(nameField.getEditText(),"Run");
        solo.enterText(reasonField.getEditText(),"Get fit");
        solo.clickOnText("Save");
    }
}
