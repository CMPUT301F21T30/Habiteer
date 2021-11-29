package com.CMPUT301F21T30.Habiteer;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for ViewHabitActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class ViewHabitTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignupActivity> rule =
            new ActivityTestRule<>(SignupActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        sharedActions.login(solo);
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Add a habit and check if it can be viewed
     */
    @Test
    public void testViewHabit() {
        //Add a habit
        sharedActions.addHabit(solo);

        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Click on the added habit
        solo.clickOnView(solo.getView(R.id.habit_recycler));

        //Check the view habit by checking the name of the Habit
        solo.waitForText("Run", 1, 2000);


    }
    /**
     * Close activity after each test
     * @throws Exception
     */

    @After
    public void tearDown() throws Exception {
        sharedActions.deleteHabit(solo);
        solo.finishOpenedActivities();
    }



}
