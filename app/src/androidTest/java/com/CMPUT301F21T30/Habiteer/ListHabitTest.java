package com.CMPUT301F21T30.Habiteer;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ListHabitTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<SignupActivity> rule = new ActivityTestRule<>(SignupActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        sharedActions.login(solo);
    }

    @Test
    public void testHabitList() {
        // solo.getCurrentActivity().getFragmentManager().findFragmentById(R.id.habit_list).isVisible();
        sharedActions.addHabit(solo);
        solo.assertCurrentActivity("Main Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.waitForText("Run", 1, 2000);

    }

    @After
    public void tearDown() throws Exception {
        sharedActions.deleteHabit(solo);
        solo.finishOpenedActivities();
    }

}
