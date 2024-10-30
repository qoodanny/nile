package comp3350.nilebookstore.tests.database;

import org.junit.Test;

import java.util.HashMap;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.objects.User;
import comp3350.nilebookstore.persistence.UserPersistence;

public class UserDatabaseTest
{
    @Test
    public void testUserDatabase()
    {
        System.out.println("Creating a User persistence data...\n");

        UserPersistence database = Services.getUserPersistence();

        System.out.println("============================================\n");
        System.out.println("Listing all users in the database\n");

        // Delete all the items in the database as a cleanup
        database.deleteUserAll();

        // Populate with SQLDB with data...
        database.insertUser(new User("Kyle",	"!Password123",	"Kyle@myumanitoba.ca",		"Computer Science"));
        database.insertUser(new User("Danny",	"abc123",		"Danny@myumanitoba.ca",		"Computer Science"));
        database.insertUser(new User("Efazi",	"123456789",	"Efazi@myumanitoba.ca",		"Computer Science"));
        database.insertUser(new User("Zen",		"Password!@#",	"Zen@myumanitoba.ca",		"Computer Science"));
        database.insertUser(new User("Lucien",	"!@#QWE123",	"Lucien@myumanitoba.ca",	"Computer Science"));

        // List all the items in the database
        HashMap<String, User> result = database.getUserList();
        for(String key : result.keySet())
        {
            System.out.println(result.get(key).toString());
        }

        // Searching for a specific user
        System.out.println("Searching for the userName = Kyle\n");
        System.out.println(database.accessUser("Kyle"));
    }
}
