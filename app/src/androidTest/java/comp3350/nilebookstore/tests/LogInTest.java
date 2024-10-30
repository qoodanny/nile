package comp3350.nilebookstore.tests;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.presentation.accountActivity.LoginActivity;

public class LogInTest
{
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginTest()
    {
        onView(withId(R.id.input_email)).perform(typeText("Team14@umanitoba.ca"));
        closeSoftKeyboard();

        onView(withId(R.id.input_password)).perform(typeText("!Password123"));
        onView(withId(R.id.button_login)).perform(click());
    }
}
