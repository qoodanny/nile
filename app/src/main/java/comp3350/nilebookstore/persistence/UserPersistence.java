package comp3350.nilebookstore.persistence;
import java.util.HashMap;
import comp3350.nilebookstore.objects.User;

public interface UserPersistence
{

	HashMap<String, User> getUserList();			// Get a list of users currently in the database

	User accessUser(String userMail);				// Search a user by their userName, return null if no user exists in database, otherwise return the user object

	User insertUser(User currentUser);				// Insert a user in the database

	void deleteUser(User currentUser);				// Delete a specified user

	void deleteUserAll();							// Clear out the database
}

