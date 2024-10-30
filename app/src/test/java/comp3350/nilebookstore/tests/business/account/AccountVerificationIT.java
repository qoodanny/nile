package comp3350.nilebookstore.tests.business.account;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.nilebookstore.business.account.AccountVerification;
import comp3350.nilebookstore.objects.User;
import comp3350.nilebookstore.persistence.UserPersistence;
import comp3350.nilebookstore.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.nilebookstore.tests.utils.TestUtils;

public class AccountVerificationIT {
    private File tempDB;
    private UserPersistence persistence;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        persistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
    }

    @Test
    public void iTTestValidEmail() {
        System.out.println("Start testing iTTestValidEmail()");
        AccountVerification av1 = new AccountVerification(persistence, "Danny@myumanitoba.ca", "ValidPassword1!");
        assertTrue(av1.checkValidEmail());
        System.out.println("Finish testing iTTestValidEmail()");
    }

    @Test
    public void iTTestValidPassword() {
        System.out.println("Start testing iTTestValidPassword()");
        AccountVerification av2 = new AccountVerification(persistence, "Danny@myumanitoba.ca", "ValidPassword1!");
        assertTrue(av2.checkValidPassword());
        System.out.println("Finish testing iTTestValidPassword()");
    }

    @Test
    public void iTTestAccountLogin() {
        System.out.println("Start testing iTTestAccountLogin()");
        User user1 = persistence.accessUser("Danny@myumanitoba.ca");
        AccountVerification av3 = new AccountVerification(persistence, user1.getUserMail(), user1.getUserPass());
        assertTrue(av3.checkAccountLogin());
        System.out.println("Finish testing iTTestAccountLogin()");
    }

    @Test
    public void iTTestAccountExists() {
        System.out.println("Start testing iTTestAccountExists()");
        AccountVerification av4 = new AccountVerification(persistence, "Danny@myumanitoba.ca", "ValidPassword1!");
        assertTrue(av4.checkAccountExists());
        System.out.println("Finish testing iTTestAccountExists()");
    }

    @After
    public void tearDown() throws Exception {
        // reset DB
        this.tempDB.delete();
    }
}
