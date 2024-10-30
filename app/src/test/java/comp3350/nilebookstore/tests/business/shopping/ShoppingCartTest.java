package comp3350.nilebookstore.tests.business.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.nilebookstore.business.shopping.ShoppingCart;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class ShoppingCartTest {

    private ItemPersistence itemPersistence;
    private ShoppingCart shoppingCart;
    private ArrayList<String> cartList;

    @Before
    public void setUp() {
        itemPersistence = mock(ItemPersistence.class);
        cartList = new ArrayList<>();
        shoppingCart = new ShoppingCart(itemPersistence, cartList);
    }

    @Test
    public void testAccessItem() {
        Item mockedItem = new Item("Pencil", "COMP3350", "Math", 5.99, true, false);
        when(itemPersistence.accessItem("Pencil")).thenReturn(mockedItem);

        Item item = shoppingCart.accessItem("Pencil");
        assertNotNull(item);
        assertEquals("Pencil", item.getItemName());

        verify(itemPersistence).accessItem("Pencil");
    }

    @Test
    public void testCalculateTotal() {
        Item pencilItem = new Item("Pencil", "COMP3350", "Math", 5.99, true, false);
        Item lockItem = new Item("Lock", "COMP3350", "Math", 20.49, true, false);
        when(itemPersistence.accessItem("Pencil")).thenReturn(pencilItem);
        when(itemPersistence.accessItem("Lock")).thenReturn(lockItem);
        shoppingCart.incrementItem("Pencil");
        shoppingCart.incrementItem("Lock");
        double expectedTotal = 26.48; // Pencil ($5.99) + Lock ($20.49)
        assertEquals(expectedTotal, shoppingCart.calculateTotal(), 0.01);

        verify(itemPersistence, times(2)).accessItem(anyString());
    }


    @Test
    public void testCalculateNumItems() {
        shoppingCart.incrementItem("Pencil");
        shoppingCart.incrementItem("Lock");
        int expectedSize = 2;
        assertEquals(expectedSize, shoppingCart.calculateNumItems());
    }

    @Test
    public void testIncrementItem() {
        shoppingCart.incrementItem("Pencil");
        assertEquals(1, shoppingCart.calculateNumItems());
    }

    @Test
    public void testDecrementItem() {
        shoppingCart.incrementItem("Pencil");
        shoppingCart.incrementItem("Lock");
        shoppingCart.decrementItem("Pencil");
        assertEquals(1, shoppingCart.calculateNumItems());
    }

    @Test
    public void testGetCartList() {
        shoppingCart.incrementItem("Pencil");
        shoppingCart.incrementItem("Lock");
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("Pencil");
        expectedList.add("Lock");
        assertEquals(expectedList, shoppingCart.getCartList());
    }
}
