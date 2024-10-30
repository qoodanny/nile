package comp3350.nilebookstore.persistence.stubs;

import java.util.HashMap;

import comp3350.nilebookstore.persistence.UserPersistence;
import comp3350.nilebookstore.objects.User;

public class UserPersistenceStub implements UserPersistence
{
	private HashMap<String, User> userList;
	
	// Create a database stub of users
	public UserPersistenceStub() 
	{
		userList = new HashMap<String, User>();
		
		userList.put("Kyle",	new User("Kyle",	"!Password123",	"Kyle@myumanitoba.ca",		"Computer Science"));
		userList.put("Danny",	new User("Danny",	"abc123",		"Danny@myumanitoba.ca",		"Computer Science"));
		userList.put("Efazi",	new User("Efazi",	"123456789",	"Efazi@myumanitoba.ca",		"Computer Science"));
		userList.put("Zen",		new User("Zen",		"Password!@#",	"Zen@myumanitoba.ca",		"Computer Science"));
		userList.put("Lucien",	new User("Lucien",	"!@#QWE123",	"Lucien@myumanitoba.ca",	"Computer Science"));
	}
	
	@Override
	// Get a list of users currently in the database
	public HashMap<String, User> getUserList() 
	{
		return userList;
	}
	
	@Override
	// Search a user by their userName, return null if no user exists in database, otherwise return the user object
	public User accessUser(String userName)
	{
		return userList.get(userName);
	}

	@Override
	// Insert a user in the database
	public User insertUser(User currentUser) 
	{
		userList.put(currentUser.getUserName(), currentUser);
		return currentUser;
	}

	@Override
	// Delete a specified user
	public void deleteUser(User currentUser)
	{
		userList.remove(currentUser.getUserName());
	}

	@Override
	// Clear out the database
	public void deleteUserAll()
	{
		userList.clear();
	}
	
	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a User persistence data...\n");
		
		UserPersistenceStub userStub = new UserPersistenceStub();
		
		System.out.println("============================================\n");
		System.out.println("Listing all users in the database\n");
		System.out.println(userStub.getUserList());
		
		System.out.println("============================================\n");
		System.out.println("Searching for the userName = Kyle\n");
		System.out.println(userStub.accessUser("Kyle@myumanitoba.ca"));
		*/
	}
}
