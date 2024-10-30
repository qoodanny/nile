package comp3350.nilebookstore.tests.objects;

//no dependencies to be mocked using Mockito
//import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.nilebookstore.objects.User;

public class UserTest 
{
	@Test
	public void testUser() 
	{
		User user;
		
		System.out.println("\nStarting testUser to test the User Domain Specific Object");
		
		user = new User("Locomotion", "!Password123", "Kyle@umanitoba.ca", "Computer Science");
		assertNotNull(user);
		
		System.out.println("\nTesting UserName");
		assertTrue("Locomotion".equals(user.getUserName()));
		System.out.println("Finished testing UserName");
		
		System.out.println("\nTesting UserPass");
		assertTrue("!Password123".equals(user.getUserPass()));
		System.out.println("Finished testing UserPass");
		
		System.out.println("\nTesting UserMail");
		assertTrue("Kyle@umanitoba.ca".equals(user.getUserMail()));
		System.out.println("Finished testing UserMail");
		
		System.out.println("\nTesting UserDept");
		assertTrue("Computer Science".equals(user.getUserDept()));
		System.out.println("Finished testing UserDept");

		System.out.println("\nFinished testUser, all tests passed");
	}
}
