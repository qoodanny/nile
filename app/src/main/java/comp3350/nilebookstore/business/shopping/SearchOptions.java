package comp3350.nilebookstore.business.shopping;

import java.util.ArrayList;

import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.persistence.ItemPersistence;

public class SearchOptions
{
    private ItemPersistence itemPersistence;
    private double  maxCost;
    private boolean physical, etext;
    private boolean used, unused;
    private String  courseId;
    private boolean hasCourseId;

    public SearchOptions()
    {
        itemPersistence = Services.getItemPersistence();
        maxCost     = 1000000;
        physical    = false;
        etext       = false;
        used        = false;
        unused      = false;
        courseId    = "";
        hasCourseId = false;
    }

    public SearchOptions(final ItemPersistence itemPersistence)
    {
        this.itemPersistence = itemPersistence;
        maxCost     = 1000000;
        physical    = false;
        etext       = false;
        used        = false;
        unused      = false;
        courseId    = "";
        hasCourseId = false;
    }

    public ArrayList<String> search(String query)
    {
        System.out.println("Search method was called");
        ArrayList<String> itemNames = itemPersistence.getItemListAlphabetical();
        ArrayList<String> itemResults = new ArrayList<>();

        for (int i = 0; i < itemNames.size(); i++)
        {
            String currItem = itemNames.get(i);
            if ( (currItem.toLowerCase()).contains((query.toLowerCase())) )
            {
                itemResults.add(currItem);
            }
        }
        return itemResults;
    }

    public boolean verifyFilters(String itemToCheck)
    {
        Item checkItem = itemPersistence.accessItem(itemToCheck);

        //Goes through our filters and verifies if an item works
        if( (etext && !physical) )//item has to be etext
        {
            if( !(checkItem.getisEtext()) )
            {
                return false;
            }
        }
        else if (!etext && physical)//item has to be physical
        {
            if( (checkItem.getisEtext()) )
            {
                return false;
            }
        }

        //------------------------------------------------------------------------------------------

        if( (used && !unused) )//item has to be used
        {
            if( (checkItem.getIsNew()) )
            {
                return false;
            }
        }
        else if (!used && unused)//item has to be unused
        {
            if( !(checkItem.getIsNew()) )
            {
                return false;
            }
        }

        //------------------------------------------------------------------------------------------

        if (checkItem.getItemCost() > maxCost)
        {
            return false;
        }

        //------------------------------------------------------------------------------------------

        return true;
    }

    public void setFilters(String cost, boolean phys, boolean ebook, boolean old, boolean isNew, String course)
    {
        //Sets all the filters based on what is selected by user
        if(cost.equals("") || cost.length() > 7)
        {
            maxCost = 9999999;
        }
        else
        {
            maxCost = Integer.parseInt(cost);
        }

        //------------------------------------------------------------------------------------------

        if(course.equals(""))
        {
            hasCourseId = false;
        }
        else
        {
            courseId = course;
            hasCourseId = true;
        }

        //------------------------------------------------------------------------------------------

        physical    = phys;
        etext       = ebook;
        used        = old;
        unused      = isNew;
    }

    public boolean isEmpty(String searched)
    {
        //Checks if string is empty
        return searched.equals("");
    }

    public boolean validCost(String cost)
    {
        //Checks if cost provided by user is valid
        return cost.equals("") || isNumber(cost);
    }

    private boolean isNumber(String num)
    {
        //Goal of this function is to check if a string is a number and greater then 0
        boolean validNum = true;

        try
        {
            long verify = Long.parseLong(num);
            if(verify < 0)
            {
                validNum = false;
            }
        }
        catch (NumberFormatException nfe)
        {
            validNum = false;
        }
        return validNum;
    }
}
