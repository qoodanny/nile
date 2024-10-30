package comp3350.nilebookstore.tests.business.shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import comp3350.nilebookstore.business.shopping.SearchOptions;
import comp3350.nilebookstore.persistence.ItemPersistence;
import comp3350.nilebookstore.persistence.hsqldb.ItemPersistenceHSQLDB;
import comp3350.nilebookstore.tests.utils.TestUtils;

public class SearchOptionsIT {

    private File tempDB;
    private ItemPersistence itemPersistence;
    private SearchOptions searchOptions;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        itemPersistence = new ItemPersistenceHSQLDB(tempDB.getAbsolutePath().replace(".script", ""));
        searchOptions = new SearchOptions(itemPersistence);
    }

    @Test
    public void iTTestSearchItems() {
        String query = "Used";
        ArrayList<String> itemResults = searchOptions.search(query);

        Assert.assertNotNull(itemResults);
        Assert.assertTrue(itemResults.size() > 0);
        for (String item : itemResults) {
            Assert.assertTrue(item.toLowerCase().contains(query.toLowerCase()));
        }
    }

    @Test
    public void iTTestVerifyFilters() {
        String itemName = "Lock";
        boolean result = searchOptions.verifyFilters(itemName);

        Assert.assertTrue(result);
    }

    @Test
    public void iTTestSetFilters() {
        String cost = "100";
        boolean phys = true;
        boolean ebook = false;
        boolean old = false;
        boolean isNew = true;
        String course = "COMP3350";

        searchOptions.setFilters(cost, phys, ebook, old, isNew, course);

        Assert.assertTrue(searchOptions.validCost(cost));
    }

    @Test
    public void iTTestIsEmpty() {
        String searched = "";
        Assert.assertTrue(searchOptions.isEmpty(searched));
    }

    @Test
    public void iTTestValidCost() {
        String cost = "50";
        Assert.assertTrue(searchOptions.validCost(cost));
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }
}
