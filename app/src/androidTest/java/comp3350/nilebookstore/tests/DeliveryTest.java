package comp3350.nilebookstore.tests;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.presentation.accountActivity.LoginActivity;
import comp3350.nilebookstore.presentation.paymentActivity.DeliveryOptionsActivity;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class DeliveryTest {

    @Rule
    public ActivityScenarioRule<DeliveryOptionsActivity> activityRule = new ActivityScenarioRule<>(DeliveryOptionsActivity.class);

    @Test
    public void testAdd() {


        onView(withId(R.id.radioGroup_pickup_delivery))
                .perform(click());
        onView(withId(R.id.radioButton_accountEmail))
                .perform(click());

        onView(withId(R.id.spinner_province))
                .perform(click());
        onView(withId(R.id.input_delivery_address)).perform(typeText("University of Manitoba"));
        onView(withId(R.id.input_delivery_city)).perform(typeText("Winnipeg"));
        onView(withId(R.id.input_delivery_postal)).perform(typeText("R4Y 5F4"));

        /*
        onData(allOf(is(instanceOf(String.class)), is("MB")))
                .inAdapterView(withId(R.id.spinner_province))
                .perform(click());
         */

        onView(withId(R.id.button_delivery_go_payment)).perform(click());
    }
}
