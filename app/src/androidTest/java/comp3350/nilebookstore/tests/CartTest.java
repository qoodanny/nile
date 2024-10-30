package comp3350.nilebookstore.tests;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.presentation.accountActivity.LoginActivity;
import comp3350.nilebookstore.presentation.shoppingActivity.CartActivity;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class CartTest {

    @Rule
    public ActivityScenarioRule<CartActivity> activityRule = new ActivityScenarioRule<>(CartActivity.class);

    @Test
    public void testAdd() {


        onView(withId(R.id.list_cart))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.button_cart_incrementItem)).perform(click());


        onView(withId(R.id.list_cart))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.button_cart_removeItem)).perform(click());

        onView(withId(R.id.button_cart_go_checkout)).perform(click());






    }

}
