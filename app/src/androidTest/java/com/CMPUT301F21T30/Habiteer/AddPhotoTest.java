package com.CMPUT301F21T30.Habiteer;

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
public class AddPhotoTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<SignupActivity> rule = new ActivityTestRule<>(SignupActivity.class, true, true);

    // This is for auto user login test
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.clickOnView(solo.getView(R.id.goToLoginBtn));
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "rat6@g.ca");
        solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");
        solo.clickOnView(solo.getView(R.id.loginBtn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    // To test adding a new habit event
    @Test
    public void AddHabitEvent()
    {
        sharedActions.addHabit(solo);
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.clickOnView(solo.getView(R.id.addHabitEvent));
        solo.enterText((EditText)solo.getView(R.id.event_name_input), "Habit name");
        solo.enterText((EditText)solo.getView(R.id.event_comment_input),"Comment");
        solo.clickOnView(solo.getView(R.id.event_image));
        solo.clickInList(1);
        solo.click
        solo.clickOnView(solo.getView(R.id.button_addHabitEventLocation));
        solo.clickOnView(solo.getView(R.id.button_addHabit));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.navigation_habit_event));
        solo.clickOnView(solo.getView(R.id.event_list));
        solo.clickInList(0);
        solo.enterText((EditText)solo.getView(R.id.event_name_input), "Habit name2");
        solo.enterText((EditText)solo.getView(R.id.event_comment_input),"Comment2");
        solo.clickOnView(solo.getView(R.id.button_addHabitEventLocation));
        solo.clickOnView(solo.getView(R.id.button_addHabit));
        solo.clickOnView(solo.getView(R.id.navigation_habit_event));
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.deleteHabitEvent));
        solo.clickOnView(solo.getView(R.id.navigation_habit_event));
        //solo.clickOnView(solo.getView(R.id.delete));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
