package comp3350.nilebookstore.objects;
import java.util.Objects;

public class Item 
{
	// Item characteristics
	private final String	itemName, itemDesc, itemDept;
	private final double	itemCost;
	private final boolean	isEtext, isNew;

	// Item constructor with just an item's name
	public Item(final String itemName)
	{
		this.itemName	= itemName;
		this.itemDesc	= null;
		this.itemDept	= null;
		this.itemCost	= 0;
		
		this.isEtext	= false;
		this.isNew		= true;	
		
	}
	
	// Item constructor with a basic description
	public Item(final String newItemName,
                final String newItemDesc,
                final String newItemDept,
                final double newItemCost,
                final boolean isEtext,
                final boolean isNew)
	{
		itemName	= newItemName;
		itemDesc 	= newItemDesc;
		itemDept	= newItemDept;
		itemCost	= newItemCost;
		
		this.isEtext = isEtext;
		this.isNew	 = isNew;		
		
	}
	
	// User get methods
	public String getItemName()		{return itemName;}
	public String getItemDesc()		{return itemDesc;}
	public String getItemDept()		{return itemDept;}
	public double getItemCost()		{return itemCost;}
	
	public boolean getisEtext()	{return isEtext;}
	public boolean getIsNew()	{return isNew;}	
	
	// User toString method
	public String toString() 
	{
		return String.format("Item: %s\n"
						   + "Description: %s\n"
						   + "Department: %s\n"
						   + "Cost: %s\n"
						   + "Available as Etext? %s\n"
						   + "Condition: %s\n\n", 
				itemName, itemDesc, itemDept, itemCost, isEtext, isNew);
	}
	
	// Check if this is the same object as other
	public boolean equals(Object other)
	{
		boolean equals = false;
		if(other instanceof Item)
		{
			final Item otherItem = (Item) other;
			equals = Objects.equals(this.itemName, otherItem.itemName);
		}
		
		return equals;
	}
	
	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a Item objects...\n");
		
		Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
		Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
		Item item3 = new Item("Chemistry Set");
		
		System.out.println(item1.toString());
		System.out.println(item2.toString());
		System.out.println(item3.toString());
		*/
	}
}