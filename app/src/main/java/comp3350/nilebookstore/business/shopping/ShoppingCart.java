package comp3350.nilebookstore.business.shopping;

import java.text.DecimalFormat;
import java.util.ArrayList;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class ShoppingCart
{
    private ItemPersistence itemPersistence;
    private ArrayList<String> cartList;

    public ShoppingCart(ArrayList<String> cartList)
    {
        itemPersistence = Services.getItemPersistence();
        this.cartList = cartList;
    }

    public ShoppingCart(final ItemPersistence itemPersistence, ArrayList<String> cartList)
    {
        this.itemPersistence = Services.getItemPersistence();
        this.cartList = cartList;
    }

    public Item accessItem(String cartItem)
    {
        return itemPersistence.accessItem(cartItem);
    }

    public double calculateTotal()
    {
        double cartTotal = 0.00;
        DecimalFormat df = new DecimalFormat("#.##");

        for(int i = 0; i < cartList.size(); i++)
        {
            cartTotal += itemPersistence.accessItem(cartList.get(i)).getItemCost();
        }

        return Double.parseDouble(df.format(cartTotal));
    }

    public int calculateNumItems()
    {
        return cartList.size();
    }

    public void incrementItem(String itemName)
    {
        cartList.add(itemName);
    }

    public void decrementItem(String itemName)
    {
        cartList.remove(itemName);
    }

    public void removeItem(String itemName)
    {
        cartList.remove(itemName);
    }

    public ArrayList<String> getCartList()
    {
        return cartList;
    }
}
