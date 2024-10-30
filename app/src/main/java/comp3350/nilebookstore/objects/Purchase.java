package comp3350.nilebookstore.objects;

import java.util.List;
import java.util.Objects;

public class Purchase 
{
	// Purchase characteristics
	private final String 	userAcct, purchaseID;
	private final String 	cardNum, cardExp;
	private final int		cardCVC;

	private List<String>	itemList;
	private final double	totalCost;

	private boolean	isPickup	= false;
	private String[]  userAddr	= new String[4];

	// Item constructor with just a user's name
	public Purchase(final String userAcct) 
	{
		this.userAcct	= userAcct;
		this.purchaseID = null;
		
		this.cardNum = null;
		this.cardExp = null;
		this.cardCVC = 0;
		
		this.itemList	= null;
		this.totalCost	= 0;

		this.isPickup	= false;
		this.userAddr	= null;
	}
	
	// Item constructor with a basic description
	public Purchase(final String	userAcct,
                    final String	purchaseID,
                    final String	cardNum,
                    final String	cardExp,
                    final int		cardCVC,
                    List<String>	itemList,
                    final double	totalCost,
                    final boolean	isPickup,
                    final String[]	userAddr)
	{
		this.userAcct 	= userAcct;
		this.purchaseID = purchaseID;
		
		this.cardNum	= cardNum;
		this.cardExp	= cardExp;
		this.cardCVC	= cardCVC;
		
		this.itemList	= itemList;
		this.totalCost	= totalCost;

		this.isPickup	= isPickup;
		this.userAddr	= userAddr;
	}
	
	// Purchase get methods
	public String getUser()				{return userAcct;}
	public String getPurchaseID()		{return purchaseID;}
	
	public String getCardNum() 			{return cardNum;}
	public String getCardExp()			{return cardExp;}
	public int	  getCardCVC()			{return cardCVC;}
	
	public List<String> getItemList()	{return itemList;}
	public double getTotalCost()		{return totalCost;}

	public boolean getIsPickup()		{return isPickup;}
	public String[] getUserAddr()		{return userAddr;}
	
	public String toString() 
	{
		String addressLine = userAddr[0] + " " + userAddr[1] + " " + userAddr[2] + " " + userAddr[3];

		return String.format("User Name: %s\n"
						   + "Purchase ID: %s\n"
						   + "\nCard Number: %s\n"
						   + "Card Expiry %s\n"
						   + "Card CVC: %s\n"
						   + "\nItems Purchased:\n%s\n"
						   + "Total Paid: %s\n"
						   + "Pickup Order? %s\n"
				           + "Address: %s\n\n",
				userAcct, purchaseID, cardNum, cardExp, cardCVC, itemList.toString(), totalCost, isPickup, addressLine);
	}
	
	
	// Check if this is the same object as other
	public boolean equals(Object other)
	{
		boolean equals = false;
		if(other instanceof Purchase)
		{
			final Purchase otherPurchase = (Purchase) other;
			equals = Objects.equals(this.purchaseID, otherPurchase.purchaseID);
		}
		
		return equals;
	}
	
	public static void main(String[] args)
	{
		/*
		System.out.println("Creating a sample Purchase receipt by creating an instance of a user, items they bought and a fictional card...\n");
		
		// Create the items purchased
		Item item1 = new Item("Pencil", "It's a 2HB pencil", "Supplies", 12.99, false, true);
		Item item2 = new Item("Multivariable Calculus", "13th edition etextbook", "Mathematics",  98.50, true, true);
		
		// Create a list of item objects
		List<String> cart = new ArrayList<>();
		cart.add(item1.getItemName());
		cart.add(item2.getItemName());
		
		// Finally create the purchase object ("receipt")
		Purchase receipt = new Purchase("Locomotion", 			// User name
										"RECPT00001", 			// Purchase ID
										"1234 1234 1234 1234",	// Card number
										"05/24",				// Card expiry
										122,					// Card cvc
										cart,					// List of items purchased
										111.49,					// Total amount
										false,
										new String[]{"AddressLine", "MB", "Province", "Postal"} );
		
		// Print it out in the console
		System.out.println(receipt);
		*/
	}
}