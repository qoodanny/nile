package comp3350.nilebookstore.tests.business.payment;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import comp3350.nilebookstore.business.payment.PaymentVerification;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.persistence.PurchasePersistence;
import comp3350.nilebookstore.persistence.hsqldb.PurchasePersistenceHSQLDB;
import comp3350.nilebookstore.tests.utils.TestUtils;

public class PaymentVerificationIT {
    private File tempDB;
    private PurchasePersistence persistence;
    private PaymentVerification paymentVerification;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        persistence = new PurchasePersistenceHSQLDB(tempDB.getAbsolutePath().replace(".script", ""));

        // Create the items purchased
        final Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
        final Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
        // Create a list of item objects
        List<String> cart1 = new ArrayList<>();
        cart1.add(item1.getItemName());
        cart1.add(item2.getItemName());
        //Create Purchase ID
        // Finally create the purchase object ("receipt")
        Purchase receipt1 = new Purchase("Locomotion", 			// User name
                "RECPT1", 			// Purchase ID
                "1234 1234 1234 1234",	// Card number
                "05/2025",				// Card expiry
                122,					// Card cvc
                cart1,					// List of items purchased
                111.49,					// Total amount
                false,
                new String[]{"92 Dysart Road", "Winnipeg", "MB", "R3T2H5"} );
        persistence.insertPurchase(receipt1);

        String cardExp = persistence.accessPurchase("RECPT1").getCardExp();

        int[] array;
        try {
            array = separateMonthAndYear(cardExp);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing the card expiration date", e);
        }

        paymentVerification = new PaymentVerification(persistence.accessPurchase("RECPT1").getCardNum(), array[0], array[1], persistence.accessPurchase("RECPT1").getCardCVC());
    }

    @Test
    public void iTTestVerifyCardNum() {
        System.out.println("Start testing iTTestVerifyCardNum()\n");
        assertTrue(paymentVerification.verifyCardNum());
        System.out.println("Finish testing iTTestVerifyCardNum()\n");
    }

    @Test
    public void iTTestVerifyCardExpiry() {
        System.out.println("Start testing iTTestVerifyCardExpiry()\n");
        assertTrue(paymentVerification.verifyExpMonth() && paymentVerification.verifyExpYear());
        System.out.println("Finish testing iTTestVerifyCardExpiry()\n");
    }

    @Test
    public void iTTestVerifyCardCVC() {
        System.out.println("Start testing iTTestVerifyCardCVC()\n");
        assertTrue(paymentVerification.verifyCardCVC());
        System.out.println("Finish testing iTTestVerifyCardCVC()\n");
    }

    private static int[] separateMonthAndYear(String dateString) throws ParseException {
        if (!dateString.matches("^\\d{2}/\\d{4}$")) {
            throw new ParseException("Invalid date format", 0);
        }

        String[] parts = dateString.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        if (month < 1 || month > 12) {
            throw new ParseException("Invalid month", 0);
        }

        return new int[]{month, year};
    }


    @After
    public void tearDown() {
        tempDB.delete();
    }
}
