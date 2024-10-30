package comp3350.nilebookstore.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.nilebookstore.tests.business.account.AccountVerificationTest;
import comp3350.nilebookstore.tests.business.payment.DeliveryOptionsTest;
import comp3350.nilebookstore.tests.business.payment.PaymentVerificationTest;
import comp3350.nilebookstore.tests.business.shopping.SearchOptionsTest;
import comp3350.nilebookstore.tests.business.shopping.ShoppingCartTest;

//Internal Use Only, Not Applicable For Testing
//import comp3350.nilebookstore.tests.database.ItemDatabaseTest;
//import comp3350.nilebookstore.tests.database.PurchaseDatabaseTest;
//import comp3350.nilebookstore.tests.database.UserDatabaseTest;

import comp3350.nilebookstore.tests.objects.ItemTest;
import comp3350.nilebookstore.tests.objects.PurchaseTest;
import comp3350.nilebookstore.tests.objects.UserTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AccountVerificationTest.class,
        DeliveryOptionsTest.class,
        PaymentVerificationTest.class,
        SearchOptionsTest.class,
        ShoppingCartTest.class,
        ItemTest.class,
        PurchaseTest.class,
        UserTest.class,

})

public class AllTests
{
    public static void main(String[] args)
    {
    }
}
