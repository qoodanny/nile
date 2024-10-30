package comp3350.nilebookstore;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.nilebookstore.tests.business.account.AccountVerificationIT;
import comp3350.nilebookstore.tests.business.payment.DeliveryOptionsIT;
import comp3350.nilebookstore.tests.business.payment.PaymentVerificationIT;
import comp3350.nilebookstore.tests.business.shopping.SearchOptionsIT;
import comp3350.nilebookstore.tests.business.shopping.ShoppingCartIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        AccountVerificationIT.class,
        DeliveryOptionsIT.class,
        PaymentVerificationIT.class,
        SearchOptionsIT.class,
        ShoppingCartIT.class

})

public class IntegrationTests {
}
