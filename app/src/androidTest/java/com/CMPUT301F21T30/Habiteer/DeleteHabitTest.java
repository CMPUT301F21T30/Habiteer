package com.CMPUT301F21T30.Habiteer;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.rule.ActivityTestRule;


import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DeleteHabitTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<SignupActivity> rule = new ActivityTestRule<>(SignupActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        sharedActions.login(solo);
    }

    @Test
    public void deleteHabit()
    {
        sharedActions.addHabit(solo);
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.clickOnView(solo.getView(R.id.delete));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
