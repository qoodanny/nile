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
import comp3350.nilebookstore.presentation.paymentActivity.PaymentActivity;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class PaymentTest {

    @Rule
    public ActivityScenarioRule<PaymentActivity> activityRule = new ActivityScenarioRule<>(PaymentActivity.class);

    @Test
    public void testAdd() {

        onView(withId(R.id.input_payment_cardNum)).perform(typeText("1234 1234 1234 1234"));
        closeSoftKeyboard();

        onView(withId(R.id.input_payment_expMonth)).perform(typeText("12"));
        closeSoftKeyboard();

        onView(withId(R.id.input_payment_expYear)).perform(typeText("2024"));
        closeSoftKeyboard();

        onView(withId(R.id.input_payment_cardCVC)).perform(typeText("234"));
        closeSoftKeyboard();

        onView(withId(R.id.button_payment_purchase));
    }
}
