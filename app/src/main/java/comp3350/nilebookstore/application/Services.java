package comp3350.nilebookstore.application;

import comp3350.nilebookstore.persistence.UserPersistence;
import comp3350.nilebookstore.persistence.ItemPersistence;
import comp3350.nilebookstore.persistence.PurchasePersistence;

import comp3350.nilebookstore.persistence.hsqldb.UserPersistenceHSQLDB;
import comp3350.nilebookstore.persistence.hsqldb.ItemPersistenceHSQLDB;
import comp3350.nilebookstore.persistence.hsqldb.PurchasePersistenceHSQLDB;

public class Services
{
    private static UserPersistence userPersistence			= null;
    private static ItemPersistence itemPersistence			= null;
    private static PurchasePersistence purchasePersistence	= null;

    public static synchronized UserPersistence getUserPersistence()
    {
        if (userPersistence == null)
        {
            userPersistence = new UserPersistenceHSQLDB( Main.getDBPathName() );
        }
        return userPersistence;
    }

    public static synchronized ItemPersistence getItemPersistence()
    {
        if (itemPersistence == null)
        {
            itemPersistence = new ItemPersistenceHSQLDB( Main.getDBPathName() );
        }
        return itemPersistence;
    }

    public static synchronized PurchasePersistence getPurchasePersistence()
    {
        if (purchasePersistence == null)
        {
            purchasePersistence = new PurchasePersistenceHSQLDB( Main.getDBPathName() );
        }
        return purchasePersistence;
    }
}

