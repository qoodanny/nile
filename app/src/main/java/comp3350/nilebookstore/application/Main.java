package comp3350.nilebookstore.application;

import java.util.HashMap;

import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.objects.User;

public class Main
{
	private static String dbName = "nileData";

	public static void setDBPathName(final String name)
	{
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		dbName = name;
	}
	public static String getDBPathName()
	{
		return dbName;
	}

	public static void main(String[] args)
	{
	}
}
