package comp3350.nilebookstore.persistence.hsqldb;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.persistence.PurchasePersistence;

public class PurchasePersistenceHSQLDB implements PurchasePersistence
{
	private final String dbPath;
	
	public PurchasePersistenceHSQLDB(final String dbPath)
	{
		this.dbPath = dbPath;
	}
	
    private Connection connection() throws SQLException 
    {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
    
    private Purchase fromResultSet(final ResultSet rs) throws SQLException 
    {
    	// Item characteristics
    	final String userAcct			= rs.getString("userAcct");
    	final String purchaseID			= rs.getString("purchaseID");
    	
    	final String cardNum 			= rs.getString("cardNum");
    	final String cardExp 			= rs.getString("cardExp");
    	final int	 cardCVC 			= rs.getInt(   "cardCVC");
    	
    	Array	 itemListArray	  = rs.getArray("itemList");
    	Object[] itemListArrayObj = (Object[]) itemListArray.getArray();
    	List<String> itemList = new ArrayList<>();
        for(int i = 0; i < itemListArrayObj.length; i++) 
        {
        	itemList.add((String)itemListArrayObj[i]);
        }

    	final double totalCost 			= rs.getDouble("totalCost");

		final boolean isPickup 			= rs.getBoolean("isPickup");
		final String	addressLine		= rs.getString("addressLine");
		final String	addressCity		= rs.getString("addressCity");
		final String	addressProvince = rs.getString("addressProvince");
		final String	addressPostal	= rs.getString("addressPostal");

    	    	
        return new Purchase(userAcct, purchaseID, cardNum, cardExp, cardCVC, itemList, totalCost, isPickup, new String[] {addressLine, addressCity, addressProvince, addressPostal} );
    }
	
	@Override
	public HashMap<String, Purchase> getPurchaseList() 
	{
		final HashMap<String, Purchase> purchaseList = new HashMap<String, Purchase>();
		
		try(final Connection c = connection())
		{
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM PURCHASELIST");
            while(rs.next()) 
            {            	             	
                final Purchase purchase = fromResultSet(rs);        
                purchaseList.put(purchase.getPurchaseID(), purchase);
            }
            rs.close();
            st.close();
            
            return purchaseList;
		}
		catch(final SQLException e) 
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public ArrayList<String> accessPuchaseByUser(String userName)
	{
		Purchase purchaseByUser = null;
		ArrayList<String> purchaseByUserList = new ArrayList<String>();
		
		try (final Connection c = connection()) 
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PURCHASELIST WHERE userAcct = ?");
			st.setString(1, userName);
			
			final ResultSet rs = st.executeQuery();			
			
			while(rs.next()) 
			{	
				purchaseByUser = fromResultSet(rs);
				purchaseByUserList.add(purchaseByUser.getPurchaseID());
			}

			rs.close();
			st.close();
			
			return purchaseByUserList;
		}
		catch(final SQLException e) 
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public Purchase accessPurchase(String purchaseID) 
	{
		Purchase purchase = null;
		
		try (final Connection c = connection()) 
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM PURCHASELIST WHERE purchaseID = ?");
			st.setString(1, purchaseID);
			
			final ResultSet rs = st.executeQuery();			
			
			while(rs.next()) 
			{
				purchase = fromResultSet(rs);
			}

			rs.close();
			st.close();
			
			return purchase;
		}
		catch(final SQLException e) 
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public Purchase insertPurchase(Purchase currentPurchase) 
	{
        try (final Connection c = connection()) 
        {
            final PreparedStatement st = c.prepareStatement("INSERT INTO PURCHASELIST VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            // User name, password, email, department
            st.setString( 1, currentPurchase.getUser() );
            st.setString( 2, currentPurchase.getPurchaseID() );
            
            st.setString( 3, currentPurchase.getCardNum() );
            st.setString( 4, currentPurchase.getCardExp() );
            st.setInt( 	  5, currentPurchase.getCardCVC() );

            String[] arrayItemList = new String[currentPurchase.getItemList().size()];
            currentPurchase.getItemList().toArray(arrayItemList);
            Array anArray = c.createArrayOf("VARCHAR", arrayItemList);          
            st.setObject( 6, anArray);
            
            st.setDouble( 7, currentPurchase.getTotalCost() );

			st.setBoolean(8, currentPurchase.getIsPickup());
			st.setString( 9, currentPurchase.getUserAddr()[0]);
			st.setString(10, currentPurchase.getUserAddr()[1]);
			st.setString(11, currentPurchase.getUserAddr()[2]);
			st.setString(12, currentPurchase.getUserAddr()[3]);

            st.executeUpdate();

            return currentPurchase;
        } 
        catch (final SQLException e) 
        {
            throw new PersistenceException(e);
        }
	}

	@Override
	public void deletePurchase(Purchase currentPurchase) 
	{
        try (final Connection c = connection()) 
        {
            final PreparedStatement st = c.prepareStatement("DELETE FROM PURCHASELIST WHERE purchaseID = ?");
            st.setString(1, currentPurchase.getPurchaseID());
            st.executeUpdate();
        } 
        catch (final SQLException e) 
        {
            throw new PersistenceException(e);
        }
	}

	@Override
	public void deletePurchaseAll() 
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
		System.out.println("Creating a Purchase persistence data...\n");
		
		String path = "C:\\Users\\DevOps\\eclipse-workspace\\Nile\\database\\nileData";
		PurchasePersistenceHSQLDB database = new PurchasePersistenceHSQLDB(path);
		
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
										"05/24",				// Card expiry
										122,					// Card cvc
										cart1,					// List of items purchased
										111.49,					// Total amount
										false,					// Pickup Order?
										new String[]{"123 Main St", "MB", "Province", "Postal"});	// Order Address
		
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
										"05/24",				// Card expiry
										910,					// Card cvc
										cart2,					// List of items purchased
										411.98,					// Total amount
										true,					// Pickup Order?
										new String[]{"University of Manitoba Campus Bookstore", "", "", ""});	// Order Address
		
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
		*/
	}
}
