package comp3350.nilebookstore.tests.business.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.os.Parcel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.nilebookstore.business.payment.DeliveryOptions;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.persistence.PurchasePersistence;
import comp3350.nilebookstore.persistence.UserPersistence;
import comp3350.nilebookstore.persistence.hsqldb.PurchasePersistenceHSQLDB;
import comp3350.nilebookstore.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.nilebookstore.tests.utils.TestUtils;

public class DeliveryOptionsIT
{
    private File tempDB;
    private UserPersistence userPersistence;

    private PurchasePersistence purchasePersistence;
    private DeliveryOptions deliveryOptions;
    private String id = "";

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();

        purchasePersistence = new PurchasePersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        userPersistence     = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        // Create the items purchased
        final Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
        final Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
        // Create a list of item objects
        List<String> cart1 = new ArrayList<>();
        cart1.add(item1.getItemName());
        cart1.add(item2.getItemName());

        deliveryOptions = new DeliveryOptions();
        //Create Purchase ID
        id = deliveryOptions.generatePurchaseID();
        // Finally create the purchase object ("receipt")
        Purchase receipt1 = new Purchase("Locomotion", 			// User name
                id, 			// Purchase ID
                "1234 1234 1234 1234",	// Card number
                "05/2025",				// Card expiry
                122,					// Card cvc
                cart1,					// List of items purchased
                111.49,					// Total amount
                false,
                new String[]{"92 Dysart Road", "Winnipeg", "MB", "R3T2H5"} );
        purchasePersistence.insertPurchase(receipt1);

        Boolean isPickUp = purchasePersistence.accessPurchase(id).getIsPickup();
        String userMail  = userPersistence.accessUser("Danny@myumanitoba.ca").getUserMail();
        String[] address = purchasePersistence.accessPurchase("RECPT00001").getUserAddr();

        //Create Delievery Options MB
        deliveryOptions = new DeliveryOptions(isPickUp, userMail, address[0], address[1], address[2], address[3]);
    }

    @Test
    public void iTTestEstimateDeliveryTime() {
        System.out.println("Start testing iTTestEstimateDeliveryTime()\n");
        assertEquals(2, deliveryOptions.estimateDeliveryTime());
        System.out.println("Finish testing iTTestEstimateDeliveryTime()\n");
    }

    @Test
    public void iTTestValidEmail() {
        System.out.println("Start testing iTTestValidEmail()\n");
        assertTrue( deliveryOptions.checkValidEmail(userPersistence.accessUser("Danny@myumanitoba.ca").getUserMail()));
        System.out.println("Finish testing iTTestValidEmail()\n");
    }

    @Test
    public void iTTestParcelableImplementation() {
        System.out.println("Start testing iTTestParcelableImplementation()\n");

        // Write the DeliveryOptions object to a Parcel
        Parcel parcel = Parcel.obtain();
        deliveryOptions.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Create a new DeliveryOptions object from the Parcel
        DeliveryOptions parcelDeliveryOptions = DeliveryOptions.CREATOR.createFromParcel(parcel);

        // Compare the original and parcel DeliveryOptions objects
        assertEquals(deliveryOptions.getPickUp(), parcelDeliveryOptions.getPickUp());
        assertEquals(deliveryOptions.getReceiverEmail(), parcelDeliveryOptions.getReceiverEmail());
        assertEquals(deliveryOptions.getAddress(), parcelDeliveryOptions.getAddress());
        assertEquals(deliveryOptions.getCity(), parcelDeliveryOptions.getCity());
        assertEquals(deliveryOptions.getProvince(), parcelDeliveryOptions.getProvince());
        assertEquals(deliveryOptions.getPostalCode(), parcelDeliveryOptions.getPostalCode());

        parcel.recycle();
        System.out.println("Finish testing iTTestParcelableImplementation()\n");
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}


