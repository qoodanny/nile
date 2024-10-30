package comp3350.nilebookstore.tests.persistence;

import java.util.ArrayList;
import java.util.HashMap;

import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.persistence.PurchasePersistence;

public class PurchasePersistenceStub implements PurchasePersistence
{
	private HashMap<String, Purchase> purchaseList;
	
	public PurchasePersistenceStub() 
	{
		purchaseList = new HashMap<String, Purchase>();;
		
		//-----------------------------------------------------------------------------------------
		
		// Create the items purchased
		final Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
		final Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
		
		// Create a list of item objects
		ArrayList<String> cart1 = new ArrayList<>();
		cart1.add(item1.getItemName());
		cart1.add(item2.getItemName());
		
		// Finally create the purchase object ("receipt")
		Purchase receipt1 = new Purchase("Locomotion", 	// User name
										"RECPT00001", 			// Purchase ID
										"1234 1234 1234 1234",	// Card number
										"05/2025",				// Card expiry
										122,					// Card cvc
										cart1,					// List of items purchased
										111.49,					// Total Amount
										false,					// Pickup Order?
										new String[]{"AddressLine", "MB", "Province", "Postal"} );	// Address
		
		//-----------------------------------------------------------------------------------------
		
		// Create the items purchased
		final Item item3 = new Item("Chemistry Set", "Physical Chemistry modeling kit", "Chemistry",  305.99, false, true);
		final Item item4 = new Item("Chemistry Set", "Physical Chemistry modeling kit, used", "Chemistry",  105.99, false, false);
		
		// Create a list of item objects
		ArrayList<String> cart2 = new ArrayList<>();
		cart2.add(item3.getItemName());
		cart2.add(item4.getItemName());
		
		// Finally create the purchase object ("receipt")
		Purchase receipt2 = new Purchase("Locomotive", 	// User name
										"RECPT00002", 			// Purchase ID
										"9087 3125 1234 1233",	// Card number
										"05/2025",				// Card expiry
										910,					// Card cvc
										cart2,					// List of items purchased
										411.98,					// Total Amount
										true,					// Pickup Order?
										new String[]{"University of Manitoba Campus Bookstore", "", "", ""} );
		
		//-----------------------------------------------------------------------------------------
		
		purchaseList.put(receipt1.getPurchaseID(), receipt1);
		purchaseList.put(receipt2.getPurchaseID(), receipt2);
	}

	@Override
	// Get a list of purchase receipts currently in the database
	public HashMap<String, Purchase> getPurchaseList()
	{
		return purchaseList;
	}

	@Override
	// Search a user by their userName, return all the purchases made by them
	public ArrayList<String> accessPuchaseByUser(String userName)
	{
		ArrayList<String> purchaseByUser = new ArrayList<>();
		
		for(String key : purchaseList.keySet())
		{
			String currentUser = purchaseList.get(key).getUser();
			if(currentUser.equals(userName))	//LMAO
			{
				purchaseByUser.add(purchaseList.get(key).getPurchaseID());
			}
		}
		
		return purchaseByUser;
	}

	@Override
	// Search a purchase receipt by its purchaseID, return null if no purchase receipt exists in database, otherwise return the purchase object
	public Purchase accessPurchase(String purchaseID)
	{
		return purchaseList.get(purchaseID);
	}

	@Override
	// Insert a purchase in the database
	public Purchase insertPurchase(Purchase currentPurchase)
	{
		purchaseList.put(currentPurchase.getPurchaseID(), currentPurchase);
		return currentPurchase;
	}

	@Override
	// Delete a specified purchase
	public void deletePurchase(Purchase currentPurchase)
	{
		purchaseList.remove(currentPurchase.getPurchaseID());
	}

	@Override
	// Clear out the database
	public void deletePurchaseAll()
	{
		purchaseList.clear();
	}

	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a Purchase persistence data...\n");
		
		PurchasePersistenceStub purchaseStub = new PurchasePersistenceStub();
		
		System.out.println("============================================\n");
		System.out.println("Listing all purchases in the database\n");
		System.out.println(purchaseStub.getPurchaseList());
		System.out.println("Done listing all purchases in the database\n");
		
		System.out.println("============================================\n");
		System.out.println("Listing all purchases made by userName = Locomotive");
		System.out.println(purchaseStub.accessPuchaseByUser("Locomotive"));
		*/
	}
}
