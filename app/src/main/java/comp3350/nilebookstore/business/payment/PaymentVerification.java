package comp3350.nilebookstore.business.payment;

import android.icu.util.Calendar;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.persistence.ItemPersistence;
import comp3350.nilebookstore.persistence.PurchasePersistence;

public class PaymentVerification
{
    private ItemPersistence itemPersistence;

    private PurchasePersistence purchasePersistence;
    private String inputCard;
    private int inputMonth, inputYear, inputCVC;

    public PaymentVerification(String inputCard, int inputMonth, int inputYear, int inputCVC)
    {
        itemPersistence     = Services.getItemPersistence();
        purchasePersistence = Services.getPurchasePersistence();

        this.inputCard  = inputCard;
        this.inputMonth = inputMonth;
        this.inputYear  = inputYear;
        this.inputCVC   = inputCVC;
    }

    public boolean verifyCardNum()
    {
        boolean isValid = true;
        String card = removeCardNumSpace();
        isValid = isPositiveNumber(card);

        if(isValid)
        {
            if (card.length() != 16)
            {
                isValid = false;
            }
        }
        return isValid;
    }

    public boolean verifyExpMonth()
    {
        //Goal of this function is to determine if month and year are valid days
        boolean isValid = false;

        //Check if Year is valid
        if ((0 < inputMonth) && (inputMonth <= 12))
        {
            isValid = true;
        }
        return isValid;
    }

    public boolean verifyExpYear()
    {
        //Goal of this function is to determine if month and year are valid days
        boolean isValid = false;
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        //Check if Year is valid
        if (currYear < inputYear)
        {
            isValid = true;
        }
        return isValid;
    }

    public boolean verifyCardCVC()
    {
        //Goal of This function is to test if the cvc number is all numbers and has the correct amount of numbers
        boolean validCard = true;
        String cvcStr = Integer.toString(inputCVC);

        if (cvcStr.length() != 3)
        {
            validCard = false;
        }
        return validCard;
    }

    private boolean isPositiveNumber(String num)
    {
        boolean isValid = true;

        try
        {
            long verify = Long.parseLong(num);
            if(verify < 0)
            {
                isValid = false;
            }
        }
        catch (NumberFormatException nfe)
        {
            isValid = false;
        }
        return isValid;
    }

    private String removeCardNumSpace()
    {
        String clean = inputCard.replaceAll(" ", "");
        return clean;
    }
}
