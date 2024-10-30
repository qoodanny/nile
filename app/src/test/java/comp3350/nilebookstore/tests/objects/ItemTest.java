package comp3350.nilebookstore.tests.objects;

//no dependencies to be mocked using Mockito
//import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.nilebookstore.objects.Item;

public class ItemTest 
{
	@Test
	public void testItem() 
	{
		Item item;
		
		System.out.println("\nStarting testItem to test the Item Domain Specific Object");
		
		item = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
		assertNotNull(item);
		
		System.out.println("\nTesting ItemName");
		assertTrue("Pencil".equals(item.getItemName()));
		System.out.println("Finished testing ItemName");
		
		System.out.println("\nTesting ItemDesc");
		assertTrue("It's a 2HB pencil".equals(item.getItemDesc()));
		System.out.println("Finished testing ItemName");
		
		System.out.println("\nTesting ItemCost");
		assertTrue(12.99 == item.getItemCost());
		System.out.println("Finished testing ItemName");
		
		System.out.println("\nTesting isEtext");
		assertTrue(false == item.getisEtext());
		System.out.println("Finished testing ItemName");
		
		System.out.println("\nTesting isNew");
		assertTrue(true == item.getIsNew());
		System.out.println("Finished testing ItemName");
		
		System.out.println("\nFinished testItem");
	}
}
