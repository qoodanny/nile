package comp3350.nilebookstore.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import comp3350.nilebookstore.objects.User;
import comp3350.nilebookstore.persistence.UserPersistence;

public class UserPersistenceHSQLDB implements UserPersistence
{
	private final String dbPath;

	public UserPersistenceHSQLDB(final String dbPath)
	{
		this.dbPath = dbPath;
	}

	private Connection connection() throws SQLException
	{
		return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
	}

	private User fromResultSet(final ResultSet rs) throws SQLException
	{
		// Item characteristics
		final String	userName = rs.getString("userName");
		final String	userPass = rs.getString("userPass");
		final String	userMail = rs.getString("userMail");
		final String	userDept = rs.getString("userDept");

		return new User(userName, userPass, userMail, userDept);
	}

	@Override
	public HashMap<String, User> getUserList()
	{
		final HashMap<String, User> userList = new HashMap<String, User>();

		try(final Connection c = connection())
		{
			final Statement st = c.createStatement();
			final ResultSet rs = st.executeQuery("SELECT * FROM USERLIST");
			while(rs.next())
			{
				final User user = fromResultSet(rs);
				userList.put(user.getUserName(), user);
			}
			rs.close();
			st.close();

			return userList;
		}
		catch(final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public User accessUser(String userMail)
	{
		User user = null;

		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("SELECT * FROM USERLIST WHERE userMail = ?");
			st.setString(1, userMail);

			final ResultSet rs = st.executeQuery();

			while(rs.next())
			{
				user = fromResultSet(rs);
			}

			rs.close();
			st.close();

			return user;
		}
		catch(final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public User insertUser(User currentUser)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("INSERT INTO USERLIST VALUES(?, ?, ?, ?)");

			// User name, password, email, department
			st.setString( 1, currentUser.getUserName() );
			st.setString( 2, currentUser.getUserPass() );
			st.setString( 3, currentUser.getUserMail() );
			st.setString( 4, currentUser.getUserDept() );

			st.executeUpdate();

			return currentUser;
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public void deleteUser(User currentUser)
	{
		try (final Connection c = connection())
		{
			final PreparedStatement st = c.prepareStatement("DELETE FROM USERLIST WHERE userName = ?");
			st.setString(1, currentUser.getUserName());
			st.executeUpdate();
		}
		catch (final SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	@Override
	public void deleteUserAll()
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
		System.out.println("Creating a User persistence data...\n");

		String path = "C:\\Users\\DevOps\\eclipse-workspace\\Nile\\database\\nileData";
		UserPersistenceHSQLDB database = new UserPersistenceHSQLDB(path);

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
		*/
	}
}
