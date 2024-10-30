package comp3350.nilebookstore.tests.business.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import comp3350.nilebookstore.business.shopping.ShoppingCart;
import comp3350.nilebookstore.persistence.ItemPersistence;
import comp3350.nilebookstore.persistence.hsqldb.ItemPersistenceHSQLDB;
import comp3350.nilebookstore.tests.utils.TestUtils;

public class ShoppingCartIT {
    private File tempDB;
    private ItemPersistence itemPersistence;
    private ArrayList<String> cartList;
    private ShoppingCart shoppingCart;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.itemPersistence = new ItemPersistenceHSQLDB(tempDB.getAbsolutePath().replace(".script", ""));
        this.cartList = new ArrayList<>();
        cartList.add("Agenda");
        cartList.add("Backpack");
        this.shoppingCart = new ShoppingCart(itemPersistence, cartList);
    }

    @Test
    public void iTTestCalculateTotal() {
        System.out.println("Starting iTTestCalculateTotal()");
        double total = shoppingCart.calculateTotal();
        assertTrue(total == 83.48);
        System.out.println("Ending iTTestCalculateTotal()");
    }

    @Test
    public void iTTestCalculateNumItems() {
        System.out.println("Starting iTTestCalculateNumItems()");
        int numItems = shoppingCart.calculateNumItems();
        assertEquals(2, numItems);
        System.out.println("Ending iTTestCalculateNumItems()");
    }

    @Test
    public void iTTestIncrementItem() {
        System.out.println("Starting iTTestIncrementItem()");
        shoppingCart.incrementItem("Umanitoba Tshirt");
        int numItems = shoppingCart.calculateNumItems();
        assertEquals(3, numItems);
        System.out.println("Ending iTTestIncrementItem()");
    }

    @Test
    public void iTTestDecrementItem() {
        System.out.println("Starting iTTestDecrementItem()");
        shoppingCart.decrementItem("Umanitoba Tshirt");
        int numItems = shoppingCart.calculateNumItems();
        assertEquals(2, numItems);
        System.out.println("Ending iTTestDecrementItem()");
    }

    @Test
    public void iTTestGetCartList() {
        System.out.println("Starting iTTestGetCartList()");
        ArrayList<String> retrievedCartList = shoppingCart.getCartList();

        assertNotNull(retrievedCartList);
        assertEquals(cartList, retrievedCartList);
        System.out.println("Ending iTTestGetCartList()");
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }
}

