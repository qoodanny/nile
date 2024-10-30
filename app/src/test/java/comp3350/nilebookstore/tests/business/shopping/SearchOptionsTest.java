package comp3350.nilebookstore.tests.business.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.nilebookstore.business.shopping.SearchOptions;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

@RunWith(MockitoJUnitRunner.class)
public class SearchOptionsTest {
    @Mock
    private ItemPersistence itemPersistence;
    private SearchOptions searchOptions;

    @Before
    public void setUp() {
        searchOptions = new SearchOptions(itemPersistence);
    }

    @Test
    public void testSearch() {
        ArrayList<String> expectedResults = new ArrayList<>(Arrays.asList("Calculus", "Chem Basics", "Lock", "Pencil"));
        when(itemPersistence.getItemListAlphabetical()).thenReturn(expectedResults);

        ArrayList<String> results = searchOptions.search("c");
        assertEquals(expectedResults, results);

        verify(itemPersistence).getItemListAlphabetical();
    }

    @Test
    public void testVerifyFilters() {
        String itemToCheck = "Calculus";
        Item mockedItem = new Item(itemToCheck, "COMP3350", "Math", 50, true, false);

        when(itemPersistence.accessItem(itemToCheck)).thenReturn(mockedItem);

        boolean verifyResult = searchOptions.verifyFilters(itemToCheck);
        assertTrue(verifyResult);

        searchOptions.setFilters("60", false, true, false, true, "");
        verifyResult = searchOptions.verifyFilters(itemToCheck);
        assertFalse(verifyResult);

        verify(itemPersistence, times(2)).accessItem(itemToCheck);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(searchOptions.isEmpty(""));
        assertFalse(searchOptions.isEmpty("COMP3350"));
    }

    @Test
    public void testValidCost() {
        assertTrue(searchOptions.validCost(""));
        assertTrue(searchOptions.validCost("50"));
        assertFalse(searchOptions.validCost("abcd"));
        assertFalse(searchOptions.validCost("-50"));
    }
}
