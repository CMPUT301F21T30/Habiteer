package com.CMPUT301F21T30.Habiteer;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

public class sharedActions {
    /**
     * actions to login to the app
     * @param solo
     */
    public static void login(Solo solo) {
        solo.clickOnView(solo.getView(R.id.goToLoginBtn));
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "as@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");
        solo.clickOnView(solo.getView(R.id.loginBtn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    public static void addHabit(Solo solo) {
        // select date
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
        solo.clickOnButton(1);
        //solo.clickOnButton(2);
        solo.clickOnText("Save");

        // fill in name and reason
        TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
        TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);
        solo.enterText(nameField.getEditText(),"Run");
        solo.enterText(reasonField.getEditText(),"Get fit");
        //save
        solo.clickOnText("Save");
    }
}
