package comp3350.nilebookstore.business.account;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.persistence.UserPersistence;

public class AccountVerification
{
    private UserPersistence userPersistence;
    private String inputEmail, inputPassword;
    public AccountVerification(String inputEmail, String inputPassword)
    {
        userPersistence    = Services.getUserPersistence();

        this.inputEmail    = inputEmail;
        this.inputPassword = inputPassword;
    }

    public AccountVerification(final UserPersistence userPersistence, String inputEmail, String inputPassword)
    {
        this.userPersistence   = userPersistence;

        this.inputEmail        = inputEmail;
        this.inputPassword     = inputPassword;
    }

    public boolean checkValidEmail()
    {
        //Goal of this function is to test if the email is valid or not
        boolean isValid = false;
        int atIndex  = inputEmail.indexOf("@");
        int dotIndex = inputEmail.indexOf(".");

        //The email address is considered valid if it contains a single "@"  with a "." symbol after the @ symbol, but not as the last character.
        if (atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < inputEmail.length() - 1)
        {
            isValid = true;
        }
        return isValid;
    }

    public boolean checkValidPassword()
    {
        //Goal of this function is to test if the password is valid or not
        boolean isValid = false;
        int length = inputPassword.length();

        // I am considering if the password has atleast 8 chars and contains atleast one digit, one special character, one uppercase and one lowercase letter then it is valid otherwise it is false.
        if (length >= 8)
        {
            boolean hasDigit     = false;
            boolean hasUpperCase = false;
            boolean hasLowerCase = false;
            boolean hasSpecialCharacter = false;

            for (int i = 0; i < length; i++)
            {
                char c = inputPassword.charAt(i);
                if (Character.isDigit(c))
                {
                    hasDigit = true;
                }
                else if (Character.isUpperCase(c))
                {
                    hasUpperCase = true;
                }
                else if (Character.isLowerCase(c))
                {
                    hasLowerCase = true;
                }
                else if (!Character.isLetterOrDigit(c))
                {
                    hasSpecialCharacter = true;
                }
            }
            if (hasDigit && hasUpperCase && hasLowerCase && hasSpecialCharacter)
            {
                isValid = true;
            }
        }
        return isValid;
    }

    public boolean checkAccountLogin()
    {
        String accountPassword = userPersistence.accessUser(inputEmail).getUserPass();
        return accountPassword.equals(inputPassword);
    }

    public boolean checkAccountExists()
    {
        return userPersistence.accessUser(inputEmail) != null;
    }
}

