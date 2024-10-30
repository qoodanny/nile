package comp3350.nilebookstore.tests.objects;

//no dependencies to be mocked using Mockito
//import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.Purchase;

public class PurchaseTest 
{
	@Test
	public void testPurchase() 
	{
		System.out.println("\nStarting testPurchase to test the Purchase Domain Specific Object");
		
		Item item1;
		Item item2;
		Purchase receipt;
			
		// Create the items purchased
		item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
		item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
		
		// Create a list of item objects
		ArrayList<String> cart = new ArrayList<>();
		cart.add(item1.getItemName());
		cart.add(item2.getItemName());
		
		// Finally create the purchase object ("receipt")
		receipt = new Purchase("Locomotion", 			// User object
							   "RECPT00001", 			// Purchase ID
							   "1234 1234 1234 1234",	// Card number
				               "05/2025",				// Card expiry
							   122,						// Card cvc
							   cart,					// List of items purchased
							   111.49, 					// Total price paid
							   false,					// Pickup Order?
							   new String[]{"123 Main St", "MB", "Province", "Postal"});	// Order Address
		assertNotNull(receipt);
		
		System.out.println("\nTesting User");
		assertTrue("Locomotion".equals(receipt.getUser()));
		System.out.println("Finished testing User");
		
		System.out.println("\nTesting PurchchaseID");
		assertTrue("RECPT00001".equals(receipt.getPurchaseID()));
		System.out.println("Finished testing PurchchaseID");
		
		System.out.println("\nTesting CardNum");
		assertTrue("1234 1234 1234 1234".equals(receipt.getCardNum()));
		System.out.println("Finished testing CardNum");
		
		System.out.println("\nTesting CardExp");
		assertTrue("05/2025".equals(receipt.getCardExp()));
		System.out.println("Finished testing CardExp");
		
		System.out.println("\nTesting CardCVC");
		assertTrue(122 == receipt.getCardCVC());
		System.out.println("Finished testing CardCVC");
		
		System.out.println("\nTesting Cart");
		assertTrue(cart.equals(receipt.getItemList()));
		System.out.println("Finished testing Cart");
		
		System.out.println("\nTesting TotalCost");
		assertTrue(111.49 == receipt.getTotalCost());
		System.out.println("Finished testing TotalCost");

		System.out.println("\nTesting Pickup");
		assertFalse(receipt.getIsPickup());
		System.out.println("Finished testing Pickup");
		
		System.out.println("\nFinished testPurchase");
	}
}
