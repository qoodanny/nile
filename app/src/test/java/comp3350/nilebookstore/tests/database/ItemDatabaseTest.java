package comp3350.nilebookstore.tests.database;

import org.junit.Test;

import java.util.HashMap;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class ItemDatabaseTest
{
    @Test
    public void testItemDatabase()
    {
        System.out.println("============================================\n");
        System.out.println("Creating a Item persistence data...\n");

        ItemPersistence database = Services.getItemPersistence();

        System.out.println("============================================\n");
        System.out.println("Listing all items in the database\n");

        // Delete all the items in the database as a cleanup
        database.deleteItemAll();

        // Populate with SQLDB with data...
        database.insertItem(new Item("Agenda",		"20 Page Agenda Booklet",			"Supplies",		2.49,	false,	true));
        database.insertItem(new Item("Pencil",		"It's a 2HB pencil, 5 pack",		"Supplies",		5.99,	false,	true));
        database.insertItem(new Item("Lock",		"Masterlock dial style", 			"Supplies",		20.49,	false,	true));
        database.insertItem(new Item("Chem Basics",	"Online Chem book 13th edition",	"Textbook",		80.99,	true,	true));
        database.insertItem(new Item("Calculus",	"Online Calculus book 5th edition",	"Textbook",		80.99,	true,	false));
        database.insertItem(new Item("Old Book",	"Used textbook",					"GeneralTopic",	9.99,	false,	false));
        database.insertItem(new Item("Tshirt",		"Umanitoba shirt",					"Merchandise",	59.99,	false,	true));

        // List all the items in the database
        HashMap<String, Item> result = database.getItemList();
        for(String key : result.keySet())
        {
            System.out.println(result.get(key).toString());
        }

        // List all the items in the database alphabetically
        System.out.println("Listing all the item names alphabetically");
        System.out.println(database.getItemList());


    }
}
