package comp3350.nilebookstore.tests.business.payment;


//no dependencies to be mocked using Mockito
//import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import comp3350.nilebookstore.business.payment.DeliveryOptions;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class DeliveryOptionsTest {

    private DeliveryOptions deliveryOptions;

    @Before
    public void setUp() {
        deliveryOptions = new DeliveryOptions(false, "test@example.com", "123 Street", "Winnipeg", "MB", "R3T5V6");
    }

    @Test
    public void testGetters() {
        System.out.println("Start testing testGetters()");

        assertFalse(deliveryOptions.getPickUp());
        assertEquals("test@example.com", deliveryOptions.getReceiverEmail());
        assertEquals("123 Street", deliveryOptions.getAddress());
        assertEquals("Winnipeg", deliveryOptions.getCity());
        assertEquals("MB", deliveryOptions.getProvince());
        assertEquals("R3T5V6", deliveryOptions.getPostalCode());

        System.out.println("End testing testGetters()");
    }

    @Test
    public void testSetters() {
        System.out.println("Start testing testSetters()");

        deliveryOptions.setPickUp(true);
        deliveryOptions.setReceiverEmail("new@example.com");
        deliveryOptions.setAddress("456 Street");
        deliveryOptions.setCity("Brandon");
        deliveryOptions.setProvince("MB");
        deliveryOptions.setPostalCode("R7A3Y5");

        assertTrue(deliveryOptions.getPickUp());
        assertEquals("new@example.com", deliveryOptions.getReceiverEmail());
        assertEquals("456 Street", deliveryOptions.getAddress());
        assertEquals("Brandon", deliveryOptions.getCity());
        assertEquals("MB", deliveryOptions.getProvince());
        assertEquals("R7A3Y5", deliveryOptions.getPostalCode());

        System.out.println("End testing testSetters()");
    }

    @Test
    public void testEstimateDeliveryTime() {
        System.out.println("Start testing testEstimateDeliveryTime()");

        assertEquals(2, deliveryOptions.estimateDeliveryTime());

        deliveryOptions.setProvince("SK");
        assertEquals(3, deliveryOptions.estimateDeliveryTime());

        deliveryOptions.setProvince("InvalidProvince");
        assertEquals(-1, deliveryOptions.estimateDeliveryTime());

        System.out.println("End testing testEstimateDeliveryTime()");
    }

    @Test
    public void testCheckValidEmail() {
        System.out.println("Start testing testCheckValidEmail()");

        assertTrue(deliveryOptions.checkValidEmail("valid@example.com"));
        assertFalse(deliveryOptions.checkValidEmail("invalid@example"));
        assertFalse(deliveryOptions.checkValidEmail("invalid@.com"));
        assertFalse(deliveryOptions.checkValidEmail("@example.com"));

        System.out.println("End testing testCheckValidEmail()");
    }

    @Test
    public void testGeneratePurchaseID() {
        System.out.println("Start testing testGeneratePurchaseID()");

        String purchaseID = deliveryOptions.generatePurchaseID();
        assertNotNull(purchaseID);
        assertTrue(purchaseID.startsWith("RECPT"));

        System.out.println("End testing testGeneratePurchaseID()");
    }

    @Test
    public void testParcelableImplementation() {
        System.out.println("Start testing testParcelableImplementation()\n");

        // Create a new DeliveryOptions object
        DeliveryOptions originalDeliveryOptions = new DeliveryOptions(false, "test@example.com", "123 Test St", "Test City", "MB", "A1B2C3");

        // Write the DeliveryOptions object to a Parcel
        Parcel parcel = Parcel.obtain();
        originalDeliveryOptions.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Create a new DeliveryOptions object from the Parcel
        DeliveryOptions parcelDeliveryOptions = DeliveryOptions.CREATOR.createFromParcel(parcel);

        // Compare the original and parcel DeliveryOptions objects
        assertEquals(originalDeliveryOptions.getPickUp(), parcelDeliveryOptions.getPickUp());
        assertEquals(originalDeliveryOptions.getReceiverEmail(), parcelDeliveryOptions.getReceiverEmail());
        assertEquals(originalDeliveryOptions.getAddress(), parcelDeliveryOptions.getAddress());
        assertEquals(originalDeliveryOptions.getCity(), parcelDeliveryOptions.getCity());
        assertEquals(originalDeliveryOptions.getProvince(), parcelDeliveryOptions.getProvince());
        assertEquals(originalDeliveryOptions.getPostalCode(), parcelDeliveryOptions.getPostalCode());

        parcel.recycle();
        System.out.println("Finish testing testParcelableImplementation()\n");
    }
}
