package com.CMPUT301F21T30.Habiteer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.CMPUT301F21T30.Habiteer.ui.community.FollowingList;
import com.CMPUT301F21T30.Habiteer.ui.community.View_others_habits;
import com.robotium.solo.Solo;

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
public class HabiteerTest {
    private Solo solo;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.CMPUT301F21T30.Habiteer", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<SignupActivity> rule = new ActivityTestRule<>(SignupActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.clickOnView(solo.getView(R.id.goToLoginBtn));
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "as@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");
        solo.clickOnView(solo.getView(R.id.loginBtn));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void FollowingListIndentTest (){
        solo.assertCurrentActivity("Wrong", FollowingList.class);

        solo.clickInList(1);  //clicks on the first item of the recycler view

        //check if the activity changes to the view_others_habits_activity
        assertTrue(solo.waitForActivity(View_others_habits.class,2000));
    }
    
}