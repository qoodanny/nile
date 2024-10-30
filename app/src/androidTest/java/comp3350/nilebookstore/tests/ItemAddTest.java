package comp3350.nilebookstore.tests;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.hasEntry;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class ItemAddTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testAdd() {

        onView(withId(R.id.list_items))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.button_add_cart)).perform(click());

        //search item
        onView(withId(R.id.input_email)).perform(typeText("Biology 1000"));
        closeSoftKeyboard();
        onView(withId(R.id.list_items))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.button_add_cart)).perform(click());

        onView(withId(R.id.button_cart_go_checkout)).perform(click());
    }
}
