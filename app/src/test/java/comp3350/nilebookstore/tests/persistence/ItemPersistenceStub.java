package comp3350.nilebookstore.tests.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class ItemPersistenceStub implements ItemPersistence
{
	private HashMap<String, Item> itemList;
	
	private ArrayList<String> itemListString;
	
	// Create a database stub of items
	public ItemPersistenceStub() 
	{
		itemList = new HashMap<String, Item>();

		itemList.put("Agenda",		new Item("Agenda",		"20 Page Agenda Booklet",			"Supplies",		2.49,	false,	true));
		itemList.put("Pencil",		new Item("Pencil",		"It's a 2HB pencil, 5 pack",		"Supplies",		5.99,	false,	true));
		itemList.put("Lock",		new Item("Lock",		"Masterlock dial style", 			"Supplies",		20.49,	false,	true));
		
		itemList.put("Chem Basics",	new Item("Chem Basics",	"Online Chem book 13th edition",	"Textbook",		80.99,	true,	true));
		itemList.put("Calculus",	new Item("Calculus",	"Online Calculus book 5th edition",	"Textbook",		80.99,	true,	false));
		
		itemList.put("Old Book",	new Item("Old Book",	"Used textbook",					"GeneralTopic",	9.99,	false,	false));
		
		itemList.put("Tshirt",		new Item("Tshirt",		"Umanitoba shirt",					"Merchandise",	59.99,	false,	true));
	}

	@Override
	// Get a list of items currently in the database
	public HashMap<String, Item> getItemList()
	{
		return itemList;
	}
	
	@Override
	public ArrayList<String> getItemListAlphabetical()
	{
		ArrayList<String> result = new ArrayList<>();
		
		for(String key : itemList.keySet()) 
		{
			result.add(key);
		}
		Collections.sort(result);
		
		return result;
	}

	@Override
	public ArrayList<String> getItemListDepartment(String department)
	{
		ArrayList<String> result = new ArrayList<>();

		for(Item item : itemList.values())
		{
			if(item.getItemDept().contains(department))
			{
				result.add(item.getItemName());
			}
		}
		Collections.sort(result);

		return result;
	}
	
	@Override
	public ArrayList<String> getItemListCondition(boolean isNew)
	{
		ArrayList<String> result = new ArrayList<>();
		
		for(Item item : itemList.values()) 
		{
			if(item.getIsNew() == isNew)
			{
				result.add(item.getItemName());
			}
		}
		Collections.sort(result);
		
		return result;
	}

	@Override
	public ArrayList<String> getItemListEtext(boolean isEtext)
	{
		ArrayList<String> result = new ArrayList<>();

		for(Item item : itemList.values())
		{
			if(item.getisEtext() == isEtext)
			{
				result.add(item.getItemName());
			}
		}
		Collections.sort(result);

		return result;
	}
	
	@Override
	// Search a item by their itemName, return null if no item exists in database, otherwise return the item object
	public Item accessItem(String itemName)
	{
		return itemList.get(itemName);
	}

	@Override
	// Insert a item in the database
	public Item insertItem(Item currentItem)
	{
		itemList.put(currentItem.getItemName(), currentItem);
		itemListString.add(currentItem.getItemName());
		return currentItem;
	}

	@Override
	// Delete a specified item
	public void deleteItem(Item currentItem)
	{
		itemList.remove(currentItem.getItemName());
		itemListString.remove(currentItem.getItemName());
	}

	@Override
	// Clear out the database
	public void deleteItemAll()
	{
		itemList.clear();
	}
	
	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a Item persistence data...\n");
		
		ItemPersistenceStub itemStub = new ItemPersistenceStub();
		
		System.out.println("============================================\n");
		System.out.println("Listing all items in the database\n");
		System.out.println(itemStub.getItemList());
		System.out.println(itemStub.getItemListAlphabetical());
		System.out.println(itemStub.itemListDepartment("Supplies"));
		System.out.println(itemStub.itemListCondition(false));
		

		System.out.println("============================================\n");
		System.out.println("Searching for the itemName = Lock\n");
		System.out.println(itemStub.accessItem("Lock"));
		*/
	}
}
