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
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddHabitFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.antonious.materialdaypicker.MaterialDayPicker;

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
        sharedActions.login(solo);
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
        assertEquals(addedit.getSupportFragmentManager().findFragmentById(R.id.container).getClass(), AddHabitFragment.class);
    }

    /**
     * Test if input validation prevents the activity from submitting empty fields
     */
    @Test
    public void testInputValidation() {
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        sharedActions.saveAndCheckActivityChange(solo); // test with all fields empty
        TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
        TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);
        // test with name field entered
        solo.enterText(nameField.getEditText(),"Run");
        sharedActions.saveAndCheckActivityChange(solo);
        // test with name and reason entered
        solo.enterText(reasonField.getEditText(),"Get fit");
        sharedActions.saveAndCheckActivityChange(solo);
        // test with name,reason,date entered (days of week not selected)
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
        solo.clickOnButton(1);
        solo.clickOnButton(2);
        sharedActions.saveAndCheckActivityChange(solo);
        // test with everything entered
        MaterialDayPicker dayPicker = (MaterialDayPicker) solo.getView(R.id.AddEdit_day_picker);
        solo.clickOnView(dayPicker.getChildAt(0)); // click on a day
        solo.clickOnText("Save");
        solo.assertCurrentActivity("Habit not saved", MainActivity.class); // habit should have saved

    }

    @Test
    public void TestAddHabitDatePicker() {
        solo.clickOnView(solo.getView(R.id.FAB_addHabit));
        solo.assertCurrentActivity("Wrong Activity", AddEditHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.textInput_habitStartDate));
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
    // teardown using delete
    @After
    public void teardown() {
        sharedActions.deleteHabit(solo);
    }
}