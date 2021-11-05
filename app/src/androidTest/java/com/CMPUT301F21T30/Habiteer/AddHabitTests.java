package com.CMPUT301F21T30.Habiteer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitFragment;
import com.google.android.material.textfield.TextInputLayout;
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
public class AddHabitTests {
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

    /**
     * Test if clicking the floating action button changes to the correct add habit fragment
     */
    @Test
    public void testFABViewChange() {
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        // see if right activity appears
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);

        // see if the right fragment appears
        AddEditHabitActivity addedit = (AddEditHabitActivity)  solo.getCurrentActivity();
        assertEquals(addedit.getSupportFragmentManager().findFragmentById(R.id.container).getClass(), AddEditHabitFragment.class);
    }

    @Test
    public void TestAddHabitDatePicker() {
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
        // see if the right fragment appears
        AddEditHabitActivity addedit = (AddEditHabitActivity)  solo.getCurrentActivity();
        solo.clickOnButton(1);
        solo.clickOnButton(2);
        solo.clickOnText("Save");

        TextInputLayout dateField = (TextInputLayout) solo.getView(R.id.textInput_habitStartDate);
        assertNotNull("Date field is empty!",dateField.getEditText().getText().toString()); // cannot test correctness yet, see #44 on github

    }

        @Test
    public void testAddHabit() {
            solo.clickOnView(solo.getView(R.id.FAB_addHabit));
            solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
            solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
            // see if the right fragment appears
            AddEditHabitActivity addedit = (AddEditHabitActivity)  solo.getCurrentActivity();
            solo.clickOnButton(1);
            solo.clickOnButton(2);
            solo.clickOnText("Save");

            TextInputLayout dateField = (TextInputLayout) solo.getView(R.id.textInput_habitStartDate);
            assertNotNull("Date field is empty!",dateField.getEditText().getText().toString()); // cannot test correctness yet, see #44 on github

            TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
            TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);
            solo.enterText(nameField.getEditText(),"Run");
            solo.enterText(reasonField.getEditText(),"Get fit");
            solo.clickOnText("Save");
            assertTrue(solo.waitForText("Run",1,2000));

    }
}