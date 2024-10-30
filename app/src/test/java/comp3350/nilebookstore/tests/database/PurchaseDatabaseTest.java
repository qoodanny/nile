package comp3350.nilebookstore.tests.database;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.persistence.PurchasePersistence;

public class PurchaseDatabaseTest
{
    @Test
    public void testPurchaseDatabase()
    {
        System.out.println("Creating a Purchase persistence data...\n");

        PurchasePersistence database = Services.getPurchasePersistence();

        System.out.println("============================================\n");
        System.out.println("Listing all purchases in the database\n");

        // Delete all the items in the database as a cleanup
        database.deletePurchaseAll();

        //-----------------------------------------------------------------------------------------

        // Create the items purchased
        final Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
        final Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);

        // Create a list of item objects
        List<String> cart1 = new ArrayList<>();
        cart1.add(item1.getItemName());
        cart1.add(item2.getItemName());

        // Finally create the purchase object ("receipt")
        Purchase receipt1 = new Purchase("Locomotive", 			// User name
                "RECPT00001", 			// Purchase ID
                "1234 1234 1234 1234",	// Card number
                "05/2025",				// Card expiry
                122,					// Card cvc
                cart1,					// List of items purchased
                111.49,                 // Total amount
                false,                  // Pickup Order?
                new String[]{"123 Main St", "MB", "Province", "Postal"});		// Order Address

        //-----------------------------------------------------------------------------------------

        // Create the items purchased
        final Item item3 = new Item("Chemistry Set", "Physical Chemistry modeling kit", "Chemistry",  305.99, false, true);
        final Item item4 = new Item("Chemistry Set", "Physical Chemistry modeling kit, used", "Chemistry",  105.99, false, false);

        // Create a list of item objects
        List<String> cart2 = new ArrayList<>();
        cart2.add(item3.getItemName());
        cart2.add(item4.getItemName());

        // Finally create the purchase object ("receipt")
        Purchase receipt2 = new Purchase("Locomotive", 			// User name
                "RECPT00002", 			// Purchase ID
                "9087 3125 1234 1233",	// Card number
                "05/2025",				// Card expiry
                910,					// Card cvc
                cart2,					// List of items purchased
                411.98,                 // Total amount
                true,                   // Pickup Order?
                new String[]{"University of Manitoba Campus Bookstore", "", "", ""});   // Order Address

        //-----------------------------------------------------------------------------------------

        database.insertPurchase(receipt1);
        database.insertPurchase(receipt2);

        // List all the purchases in the database
        HashMap<String, Purchase> result = database.getPurchaseList();
        for(String key : result.keySet())
        {
            System.out.println(result.get(key).toString());
        }

        //HashMap<String, Purchase> result = database.getPurchaseList();

        // Searching for a specific purchase
        System.out.println("Searching for purchases made by userName: 'Locomotive' ");
        System.out.println(database.accessPuchaseByUser("Locomotive"));
    }
}
