package comp3350.nilebookstore.persistence;
import java.util.ArrayList;
import java.util.HashMap;
import comp3350.nilebookstore.objects.Purchase;

public interface PurchasePersistence
{
	HashMap<String, Purchase> getPurchaseList();				// Get a list of purchases currently in the database

	ArrayList<String> accessPuchaseByUser(String userName);		// Get a list of purchases made by a specified user

	Purchase accessPurchase(String purchaseID);					// Search a purchase by its purchaseID, return null if no purchase receipt exists in database,
																// otherwise return the purchase object

	Purchase insertPurchase(Purchase currentPurchase);			// Insert a purchase receipt in the database

	void deletePurchase(Purchase currentPurchase);				// Delete a specified purchase receipt

	void deletePurchaseAll();									// Clear out the database
}
