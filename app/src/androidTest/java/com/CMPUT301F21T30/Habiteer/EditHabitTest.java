package com.CMPUT301F21T30.Habiteer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddEditHabitActivity;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.AddHabitFragment;
import com.CMPUT301F21T30.Habiteer.ui.addEditHabit.EditHabitFragment;
import com.CMPUT301F21T30.Habiteer.ui.habit.ViewHabitActivity;
import com.google.android.material.textfield.TextInputLayout;
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
public class EditHabitTest {
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
        sharedActions.addHabit(solo);
    }
    @Test
    public void testEditViewChange() {
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.assertCurrentActivity("Wrong activity", ViewHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.edit));
        solo.assertCurrentActivity("Wrong activity", AddEditHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.textInput_habitEndDate));
        // see if the right fragment appears
        AddEditHabitActivity addedit = (AddEditHabitActivity)  solo.getCurrentActivity();
        assertEquals(addedit.getSupportFragmentManager().findFragmentById(R.id.container).getClass(), EditHabitFragment.class);
    }
    @Test
    public void testEditView() {
        solo.clickOnView(solo.getView(R.id.habit_recycler));
        solo.assertCurrentActivity("Wrong activity", ViewHabitActivity.class);
        solo.clickOnView(solo.getView(R.id.edit));
        solo.assertCurrentActivity("Wrong activity", AddEditHabitActivity.class);
        // see if the right fragment appears
        AddEditHabitActivity addedit = (AddEditHabitActivity)  solo.getCurrentActivity();
        assertEquals(addedit.getSupportFragmentManager().findFragmentById(R.id.container).getClass(), EditHabitFragment.class);
        // click on date picker
        solo.clickOnView(solo.getView(R.id.textInput_habitEndDate));
        solo.clickOnButton(1);
        solo.clickOnText("Save");
        // fill in name and reason
        TextInputLayout nameField = (TextInputLayout) solo.getView(R.id.textInput_habitName);
        TextInputLayout reasonField = (TextInputLayout) solo.getView(R.id.textInput_habitReason);

        // add on some suffixes
        solo.enterText(nameField.getEditText()," faster");
        solo.enterText(reasonField.getEditText(),"ter");
        solo.clickOnText("Save");
        // see if changed on view screen
        assertTrue("Not changed",solo.searchText("Run faster"));
        assertTrue("Not changed",solo.searchText("Get fitter"));
        
        solo.goBack();
    }
    @After
    public void teardown() {
        sharedActions.deleteHabit(solo);
    }

    
}