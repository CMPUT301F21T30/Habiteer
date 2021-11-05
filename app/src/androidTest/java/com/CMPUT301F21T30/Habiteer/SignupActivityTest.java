package com.CMPUT301F21T30.Habiteer;
import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for SignupActivity. All the UI tests are written here. Robotium test framework is
 used
 */

public class SignupActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignupActivity> rule =
            new ActivityTestRule<>(SignupActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
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
     * Add an emaail and a password and check the city name using assertTrue
     * Clear all the cities from the listview and check again with assertFalse
     */
    //@Test
    //public void checkList(){
// Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        //solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        //solo.clickOnButton("ADD CITY"); //Click ADD CITY Button
//Get view for EditText and enter a city name
        //solo.enterText((EditText) solo.getView(R.id.editText_name), "Edmonton");
        //solo.clickOnButton("CONFIRM"); //Select CONFIRM Button
        //solo.clearEditText((EditText) solo.getView(R.id.editText_name)); //Clear the EditText
/* True if there is a text: Edmonton on the screen, wait at least 2 seconds and find
minimum one match. */
        //assertTrue(solo.waitForText("Edmonton", 1, 2000));
        //solo.clickOnButton("ClEAR ALL"); //Select ClEAR ALL
//True if there is no text: Edmonton on the screen
        //assertFalse(solo.searchText("Edmonton"));
    }


}


}
