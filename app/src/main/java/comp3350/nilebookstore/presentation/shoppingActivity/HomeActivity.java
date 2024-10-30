package comp3350.nilebookstore.presentation.shoppingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.business.shopping.SearchOptions;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.presentation.paymentActivity.DeliveryOptionsActivity;

public class HomeActivity extends AppCompatActivity
{
    private ImageButton imageButton_search, imageButton_filters;
    private PopupMenu   filter;
    private Button      button_add, button_cart, button_checkout;
    private EditText    editText_search;
    private TextView textView_itemName, textView_itemPrice, textView_itemDesc, textView_itemCond, textView_itemEtext;
    private ArrayList<String>       arrayResults, cartItems, filterResultsNew, filterResultsUsed, filterResultsEtext, filterResultsPhysical;
    private ArrayAdapter<String>    itemArrayAdapter;
    private String                  userAccount;
    private SearchOptions           searchEngine;
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cartItems  = getIntent().getStringArrayListExtra("cartItems");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {userAccount = extras.getString("userAccount");}

        imageButton_search  = (ImageButton) this.findViewById(R.id.button_search);
        imageButton_filters = (ImageButton) this.findViewById(R.id.button_filter);
        button_add          = (Button)      this.findViewById(R.id.button_add_cart);
        button_cart         = (Button)      this.findViewById(R.id.button_home_go_cart);
        button_checkout     = (Button)      this.findViewById(R.id.button_home_go_checkout);
        editText_search     = (EditText)    this.findViewById(R.id.input_search);
        textView_itemName   = (TextView)    this.findViewById(R.id.output_item_name);
        textView_itemPrice  = (TextView)    this.findViewById(R.id.output_item_price);
        textView_itemDesc   = (TextView)    this.findViewById(R.id.output_item_desc);
        textView_itemCond   = (TextView)    this.findViewById(R.id.output_item_cond);
        textView_itemEtext  = (TextView)    this.findViewById(R.id.output_item_etext);
        searchEngine        = new SearchOptions();

        System.out.println("Testing Actual");
        arrayResults = Services.getItemPersistence().getItemListAlphabetical();

        if(cartItems.isEmpty())
        {
            button_checkout.setEnabled(false);
        }

        //==========================================================================================

        itemArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, arrayResults)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                Item currItem = Services.getItemPersistence().accessItem(arrayResults.get(position));
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(currItem.getItemName() + " : " +currItem.getItemCost());
                text2.setText(currItem.getItemDesc());

                return view;
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_items);
        listView.setAdapter(itemArrayAdapter);

        //==========================================================================================

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //button_add = (Button) findViewById(R.id.button_add_cart);
                if (position == selectedItemPosition)
                {
                    listView.setItemChecked(position, false);
                    button_add.setEnabled(false);
                    selectedItemPosition = -1;
                }
                else
                {
                    listView.setItemChecked(position, true);
                    button_add.setEnabled(true);
                    selectedItemPosition = position;
                    selectedItemAtPosition(position);
                }
            }
        });

        //==========================================================================================

        imageButton_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String query = editText_search.getText().toString();
                if(searchEngine.isEmpty(query))
                {
                    editText_search.setHint("No results for this item");
                    Toast.makeText(HomeActivity.this, "Search can't be empty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    selectedItemPosition = -1;

                    arrayResults = searchEngine.search(query);
                    itemArrayAdapter.clear();
                    itemArrayAdapter.addAll(arrayResults);
                    listView.setAdapter(itemArrayAdapter);

                    Toast.makeText(HomeActivity.this, "We Found " + arrayResults.size() + " Matches", Toast.LENGTH_LONG).show();
                }
            }
        });

        //==========================================================================================

        filter = new PopupMenu(HomeActivity.this, imageButton_filters);
        filter.getMenuInflater().inflate(R.menu.filter_menu, filter.getMenu());

        imageButton_filters.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                filter.show();
                filter.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        switch(menuItem.getItemId())
                        {
                            case R.id.filter_new:
                            {
                                Toast.makeText(HomeActivity.this, "Showing new items", Toast.LENGTH_LONG).show();
                                arrayResults = Services.getItemPersistence().getItemListCondition(true);
                            }
                            break;

                            case R.id.filter_used:
                            {
                                Toast.makeText(HomeActivity.this, "Showing used items", Toast.LENGTH_LONG).show();
                                arrayResults = Services.getItemPersistence().getItemListCondition(false);
                            }
                            break;

                            case R.id.filter_etext:
                            {
                                Toast.makeText(HomeActivity.this, "Showing etextbooks", Toast.LENGTH_LONG).show();
                                arrayResults = Services.getItemPersistence().getItemListEtext(true);
                            }
                            break;

                            case R.id.filter_physical:
                            {
                                Toast.makeText(HomeActivity.this, "Showing physical items", Toast.LENGTH_LONG).show();
                                arrayResults = Services.getItemPersistence().getItemListEtext(false);
                            }
                            break;

                            case R.id.filter_reset:
                            {
                                Toast.makeText(HomeActivity.this, "Showing all items", Toast.LENGTH_LONG).show();
                                arrayResults = Services.getItemPersistence().getItemListAlphabetical();
                            }
                            break;
                        }
                        selectedItemPosition = -1;
                        itemArrayAdapter.clear();
                        itemArrayAdapter.addAll(arrayResults);
                        listView.setAdapter(itemArrayAdapter);
                        Toast.makeText(HomeActivity.this, "We Found " + arrayResults.size() + " Matches", Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
        });

        //==========================================================================================

        button_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(selectedItemPosition != -1)
                {
                    String currItem = itemArrayAdapter.getItem(selectedItemPosition);
                    cartItems.add(currItem);
                    button_checkout.setEnabled(true);
                    Toast.makeText(HomeActivity.this, "Added " + currItem + " into your shopping cart!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "You don't have any item selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //==========================================================================================

        button_cart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent cartIntent = new Intent(HomeActivity.this, CartActivity.class);
                cartIntent.putExtra("userAccount", userAccount);
                cartIntent.putStringArrayListExtra("cartItems", cartItems);

                System.out.println(userAccount);
                System.out.println(cartItems);
                HomeActivity.this.startActivity(cartIntent);
            }
        });

        //==========================================================================================

        button_checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent deliveryIntent = new Intent(HomeActivity.this, DeliveryOptionsActivity.class);
                deliveryIntent.putExtra("userAccount", userAccount);
                deliveryIntent.putStringArrayListExtra("cartItems", cartItems);

                System.out.println(userAccount);
                System.out.println(cartItems);
                HomeActivity.this.startActivity(deliveryIntent);
            }
        });

        //==========================================================================================
    }

    public void selectedItemAtPosition(int position)
    {
        Item selectedItem = Services.getItemPersistence().accessItem(itemArrayAdapter.getItem(position));

        textView_itemName.setText(selectedItem.getItemName());
        textView_itemPrice.setText(Double.toString(selectedItem.getItemCost()));
        textView_itemDesc.setText(selectedItem.getItemDesc());

        if(selectedItem.getIsNew()) {textView_itemCond.setText("New");}
        else {textView_itemCond.setText("Used");}

        if(selectedItem.getisEtext()) {textView_itemEtext.setText("Available");}
        else {textView_itemEtext.setText("Unavailable");}
    }
}