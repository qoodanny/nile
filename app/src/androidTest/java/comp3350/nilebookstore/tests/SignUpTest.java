package comp3350.nilebookstore.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.presentation.accountActivity.LoginActivity;

public class SignUpTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSignIn() {
        // move from home page to add page
//        onView(withId(R.id.button_signup)).perform(click());
//
//
//        // add a new User
//        onView(withId(R.id.input_new_username)).perform(typeText("Lucien"));
//        onView(withId(R.id.input_new_email)).perform(typeText("Lucien@gmail.com"));
//        onView(withId(R.id.input_new_passwordA)).perform(typeText("!Password123"));
//        closeSoftKeyboard();
//        onView(withId(R.id.input_new_passwordB)).perform(typeText("!Password123"));
//        onView(withId(R.id.button_create)).perform(click());
//        closeSoftKeyboard();
//        onView(withId(R.id.button_create)).perform(click());
//
//
//
//        // verify recipe exists
//
//
//        onView(withId(R.id.input_email)).perform(typeText("Lucien@gmail.com"));
//        onView(withId(R.id.input_password)).perform(typeText("!Password123"));
//        onView(withId(R.id.button_login)).perform(click());

        // test Log in
        onView(withId(R.id.input_email)).perform(typeText("Team14@umanitoba.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.input_password)).perform(typeText("!Password123"));
        onView(withId(R.id.button_login)).perform(click());
    }
}
