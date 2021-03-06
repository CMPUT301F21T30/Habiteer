package com.CMPUT301F21T30.Habiteer;

/**
 * Test class for SignupActivity. All the UI tests are written here. Robotium test framework is
 used
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


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
     * Add an email, a password and a confirm password and check it using assertTrue
     * Clear the everything and check again with assertFalse
     */
    @Test
    public void checkEmailPassword(){
        // Asserts that the current activity is the SignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);

        //Get view for EditText and enter an Email, Password and confirm Password
        solo.enterText((EditText) solo.getView(R.id.signupEmail), "123@gm.ca");
        solo.enterText((EditText) solo.getView(R.id.signupPassword), "123456");
        solo.enterText((EditText) solo.getView(R.id.signupConfirmPassword), "123456");



        /* True if texts are there on screen on the screen, wait at least 2 seconds and find minimum one match for email and 2 for passwords. */
        assertTrue(solo.waitForText("123@gm.ca", 1, 2000));
        assertTrue(solo.waitForText("123456", 2, 2000));

        //Clear the EditText
        solo.clearEditText((EditText) solo.getView(R.id.signupEmail));
        solo.clearEditText((EditText) solo.getView(R.id.signupPassword));
        solo.clearEditText((EditText) solo.getView(R.id.signupConfirmPassword));

        //True if there is no text on the screen
        assertFalse(solo.searchText("123@gm.ca"));
        assertFalse(solo.searchText("123456"));
    }

    /**
     * Press the Login Button and go to LoginActivity
     */
    @Test
    public void checkLoginBtn(){
        // Asserts that the current activity is the SignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);

        //Click on the Login Button
        solo.clickOnButton("Login"); //Select Login Button

        // Asserts that the current activity is the LoginActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }

    /**
     * Press the Signup Button and go to MainActivity
     */
    //@Test
    //public void checkSignupBtn(){
        // Asserts that the current activity is the SignupActivity. Otherwise, show “Wrong Activity”
        //solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);

        //Get view for EditText and enter an Email, Password and confirm Password
        //solo.enterText((EditText) solo.getView(R.id.signupEmail), "123@gm.ca");
        //solo.enterText((EditText) solo.getView(R.id.signupPassword), "123456");
        //solo.enterText((EditText) solo.getView(R.id.signupConfirmPassword), "123456");

        //Click on the Login Button
        //solo.clickOnButton("Sign up"); //Select Sign up Button

        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        //solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    //}

    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}












