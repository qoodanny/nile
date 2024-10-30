package comp3350.nilebookstore.tests.business.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import comp3350.nilebookstore.business.account.AccountVerification;
import comp3350.nilebookstore.objects.User;
import comp3350.nilebookstore.persistence.UserPersistence;

public class AccountVerificationTest {
    private UserPersistence userPersistence;
    private AccountVerification accountVerification;

    @Before
    public void setUp() {
        userPersistence = Mockito.mock(UserPersistence.class);
    }

    @Test
    public void testCheckValidEmail() {
        System.out.println("Starting testCheckValidEmail...");
        accountVerification = new AccountVerification(userPersistence, "test@example.com", "ValidP@55word");
        assertTrue(accountVerification.checkValidEmail());

        accountVerification = new AccountVerification(userPersistence, "test@.com", "ValidP@55word");
        assertFalse(accountVerification.checkValidEmail());
        System.out.println("Finished testCheckValidEmail.");
    }

    @Test
    public void testCheckValidPassword() {
        System.out.println("Starting testCheckValidPassword...");
        accountVerification = new AccountVerification(userPersistence, "test@example.com", "ValidP@55word");
        assertTrue(accountVerification.checkValidPassword());

        accountVerification = new AccountVerification(userPersistence, "test@example.com", "invalid");
        assertFalse(accountVerification.checkValidPassword());
        System.out.println("Finished testCheckValidPassword.");
    }

    @Test
    public void testCheckAccountLogin() {
        System.out.println("Starting testCheckAccountLogin...");
        User user = new User("Locomotion", "!Password123", "Kyle@myumanitoba.ca", "Computer Science");
        when(userPersistence.accessUser("Kyle@myumanitoba.ca")).thenReturn(user);

        accountVerification = new AccountVerification(userPersistence, "Kyle@myumanitoba.ca", "!Password123");
        assertTrue(accountVerification.checkAccountLogin());

        accountVerification = new AccountVerification(userPersistence, "Kyle@myumanitoba.ca", "WrongP@55word");
        assertFalse(accountVerification.checkAccountLogin());
        System.out.println("Finished testCheckAccountLogin.");
    }

    @Test
    public void testCheckAccountExists() {
        System.out.println("Starting testCheckAccountExists...");
        User user = new User("Locomotion", "!Password123", "Kyle@myumanitoba.ca", "Computer Science");
        when(userPersistence.accessUser("Kyle@myumanitoba.ca")).thenReturn(user);
        when(userPersistence.accessUser("nonexistent@example.com")).thenReturn(null);

        accountVerification = new AccountVerification(userPersistence, "Kyle@myumanitoba.ca", "!Password123");
        assertTrue(accountVerification.checkAccountExists());

        accountVerification = new AccountVerification(userPersistence, "nonexistent@example.com", "ValidP@55word");
        assertFalse(accountVerification.checkAccountExists());
        System.out.println("Finished testCheckAccountExists.");
    }
}
