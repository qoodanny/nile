package comp3350.nilebookstore.objects;

import java.util.Objects;

public class User
{
	// User characteristics
	private final String userName;
	private String	 userPass, userMail, userDept;

	// User constructor with a user name and password
	public User(final String newUserName,
                String	 	 newUserPass,
                String	 	 newUserMail,
                String		 newUserDept)
	{
		userName = newUserName;
		userPass = newUserPass;
		userMail = newUserMail;
		userDept = newUserDept;
	}

	// User get methods
	public String	getUserName()	{return userName;}
	public String	getUserPass()	{return userPass;}
	public String	getUserMail()	{return userMail;}
	public String	getUserDept()	{return userDept;}

	// User set methods
	public void setUserPass(String	 newUserPass)	{userPass = newUserPass;}
	public void setUserMail(String	 newUserMail)	{userMail = newUserMail;}
	public void setUserDept(String	 newUserDept)	{userDept = newUserDept;}

	// User toString method
	public String toString()
	{
		return String.format("UserName: %s\n"
						+ "Password: %s\n"
						+ "Email: %s\n"
						+ "Department: %s\n",
				userName, userPass, userMail, userDept);
	}

	// Check if this is the same object as other
	public boolean equals(Object other)
	{
		boolean equals = false;
		if(other instanceof User)
		{
			final User otherUser = (User) other;
			equals = Objects.equals(this.userMail, otherUser.userMail);
		}

		return equals;
	}

	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a User objects...\n");

		User user1 = new User("Locomotion", "!Password123", "Kyle@myumanitoba.ca", "Computer Science");
		User user2 = new User("Locomotive", "QWE!@#123rty", "AltAccount@myumanitoba.ca", "Computer Science");

		System.out.println(user1.toString());
		System.out.println(user2.toString());
		*/
	}
}
