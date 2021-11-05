package com.CMPUT301F21T30.Habiteer;

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

/**
 * Test class for LoginActivity. All the UI tests are written here. Robotium test framework is
 used
 */

public class LoginActivtyTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

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
     * Add an email, and a password and check it using assertTrue
     * Clear the everything and check again with assertFalse
     */
    @Test
    public void checkEmailPassword(){
        // Asserts that the current activity is the LoginActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        //Get view for EditText and enter an Email and a Password
        solo.enterText((EditText) solo.getView(R.id.loginEmail), "123@gm.ca");
        solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");


        /* True if texts are there on screen on the screen, wait at least 2 seconds and find minimum one match for email and 2 for passwords. */
        assertTrue(solo.waitForText("123@gm.ca", 1, 2000));
        assertTrue(solo.waitForText("123456", 1, 2000));

        //Clear the EditText
        solo.clearEditText((EditText) solo.getView(R.id.loginEmail));
        solo.clearEditText((EditText) solo.getView(R.id.loginPassword));

        //True if there is no text on the screen
        assertFalse(solo.searchText("123@gm.ca"));
        assertFalse(solo.searchText("123456"));
    }
    /**
     * Press the Sign up Button and go to SignupActivity
     */
    @Test
    public void checkSignupBtn(){
        // Asserts that the current activity is the LoginActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        //Click on the Signup Button
        solo.clickOnButton("Sign Up"); //Select Signup Button

        // Asserts that the current activity is the SignupActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);

    }

    /**
     * Press the Login Button and go to MainActivity
     */
    //@Test
    //public void checkLoginBtn(){
        // Asserts that the current activity is the SignupActivity. Otherwise, show “Wrong Activity”
        //solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        //Get view for EditText and enter an Email, Password and confirm Password
        //solo.enterText((EditText) solo.getView(R.id.loginEmail), "123@gm.ca");
        //solo.enterText((EditText) solo.getView(R.id.loginPassword), "123456");


        //Click on the Login Button
        
        //solo.clickOnButton("Login"); //Select login Button

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
