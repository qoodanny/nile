package comp3350.nilebookstore.persistence;
import java.util.ArrayList;
import java.util.HashMap;
import comp3350.nilebookstore.objects.Item;

public interface ItemPersistence
{
	HashMap<String, Item> getItemList();						// Get a list of items currently in the database

	ArrayList<String> getItemListAlphabetical();				// Get a list of item names by alphabetical order

	ArrayList<String> getItemListDepartment(String department);	// Get a list of item names by department

	ArrayList<String> getItemListCondition(boolean isNew);		// Get a list of item names by condition

	ArrayList<String> getItemListEtext(boolean isEtext);	// Get a list of item names by etext/physical

	Item accessItem(String itemName);							// Search an item by its itemName, return null if no such item exists in database,
																// otherwise return the item object

	Item insertItem(Item currentItem);							// Insert an item in the database

	void deleteItem(Item currentItem);							// Delete a specified item

	void deleteItemAll();										// Clear out the database
}
