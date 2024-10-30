package comp3350.nilebookstore.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class ItemPersistenceHSQLDB implements ItemPersistence 
{
	private final String dbPath;

	public ItemPersistenceHSQLDB(final String dbPath)
	{
		this.dbPath = dbPath;
	}

    private Connection connection() throws SQLException 
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
    
    private Item fromResultSet(final ResultSet rs) throws SQLException 
    {
    	// Item characteristics
    	final String	itemName = rs.getString("itemName");
    	final String	itemDesc = rs.getString("itemDesc");
    	final String	itemDept = rs.getString("itemDept");
    	final double	itemCost = rs.getDouble("itemCost");
    	  	
    	final boolean	isEtext  = rs.getBoolean("isEtext");
    	final boolean	isNew	 = rs.getBoolean("isNew"); 	
    	
        return new Item(itemName, itemDesc, itemDept, itemCost, isEtext, isNew);
    }

	@Override
	public HashMap<String, Item> getItemList() 
	{
		final HashMap<String, Item> itemList = new HashMap<String, Item>();
		
		try(final Connection c = connection())
		{
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ITEMLIST");
            while(rs.next()) 
            {            	
                final Item item = fromResultSet(rs);
                itemList.put(item.getItemName(), item);
            }
            rs.close();
            st.close();
            
            return itemList;
		}
		catch(final SQLException e) 
		{
			throw new PersistenceException(e);
		}
	}
	
	@Override
	public ArrayList<String> getItemListAlphabetical()
	{
		ArrayList<String> result = new ArrayList<>();
		HashMap<String, Item> itemList = this.getItemList();
		
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
		HashMap<String, Item> itemList = this.getItemList();

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
		HashMap<String, Item> itemList = this.getItemList();

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
		HashMap<String, Item> itemList = this.getItemList();

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
	public Item accessItem(String itemName) 
	{
		Item item = null;
		
		try (final Connection c = connection()) 
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM ITEMLIST WHERE itemName = ?");
			st.setString(1, itemName);
			
			final ResultSet rs = st.executeQuery();			
			
			while(rs.next()) 
			{
				item = fromResultSet(rs);
			}

			rs.close();
			st.close();
			
			return item;
		}
		catch(final SQLException e) 
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public Item insertItem(Item currentItem) 
	{
        try (final Connection c = connection()) 
        {
            final PreparedStatement st = c.prepareStatement("INSERT INTO ITEMLIST VALUES(?, ?, ?, ?, ?, ?)");
            
            // Item name, description, and department
            st.setString( 1, currentItem.getItemName() );
            st.setString( 2, currentItem.getItemDesc() );
            st.setString( 3, currentItem.getItemDept() );

            // Item cost
            st.setDouble( 4, currentItem.getItemCost() );
            
            // Item is etext? is new?
            st.setBoolean( 5, currentItem.getisEtext() );
            st.setBoolean( 6, currentItem.getIsNew()   );

            st.executeUpdate();

            return currentItem;
        } 
        catch (final SQLException e) 
        {
            throw new PersistenceException(e);
        }
	}

	@Override
	public void deleteItem(Item currentItem) 
	{
        try (final Connection c = connection()) 
        {
            final PreparedStatement st = c.prepareStatement("DELETE FROM ITEMLIST WHERE itemName = ?");
            st.setString(1, currentItem.getItemName());
            st.executeUpdate();
        } 
        catch (final SQLException e) 
        {
            throw new PersistenceException(e);
        }
	}

	@Override
	public void deleteItemAll() 
	{
        try (final Connection c = connection()) 
        {
            final PreparedStatement st = c.prepareStatement("TRUNCATE SCHEMA PUBLIC AND COMMIT NO CHECK");
            st.executeUpdate();
        } 
        catch (final SQLException e) 
        {
            throw new PersistenceException(e);
        }
	}

	public static void main(String[] args) 
	{
		/*
		System.out.println("Creating a Item persistence data...\n");
		
		String path = "C:\\Users\\DevOps\\eclipse-workspace\\Nile\\database\\nileData";
		ItemPersistenceHSQLDB database = new ItemPersistenceHSQLDB(path);
		
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
		System.out.println(database.getItemListAlphabetical());
		*/
	}
}
