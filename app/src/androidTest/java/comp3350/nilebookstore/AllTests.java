package comp3350.nilebookstore;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.nilebookstore.tests.CartTest;
import comp3350.nilebookstore.tests.LaunchTest;
import comp3350.nilebookstore.tests.LogInTest;
import comp3350.nilebookstore.tests.SignUpTest;
import comp3350.nilebookstore.tests.ItemAddTest;
import comp3350.nilebookstore.tests.DeliveryTest;
import comp3350.nilebookstore.tests.PaymentTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        LaunchTest.class,
        LogInTest.class,
        //SignUpTest.class
        ItemAddTest.class,
        CartTest.class,
        DeliveryTest.class,
        PaymentTest.class
})

public class AllTests
{
    public static void main(String[] args)
    {
    }
}
