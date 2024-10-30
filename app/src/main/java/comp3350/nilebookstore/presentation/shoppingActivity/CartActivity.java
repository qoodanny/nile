package comp3350.nilebookstore.presentation.shoppingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.business.shopping.ShoppingCart;
import comp3350.nilebookstore.objects.Item;
import comp3350.nilebookstore.presentation.paymentActivity.DeliveryOptionsActivity;

public class CartActivity extends AppCompatActivity
{
    private Button button_increment, button_remove, button_home, button_checkout;
    private TextView textView_cartCount, textView_cartTotal, textView_cart_itemDesc;
    private ArrayList<String> cartItems;
    private ArrayAdapter<String> cartArrayAdapter;
    private String userAccount;
    private ShoppingCart cartEngine;
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartItems  = getIntent().getStringArrayListExtra("cartItems");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {userAccount = extras.getString("userAccount");}

        button_increment        = (Button)   this.findViewById(R.id.button_cart_incrementItem);
        button_remove           = (Button)   this.findViewById(R.id.button_cart_removeItem);
        button_home             = (Button)   this.findViewById(R.id.button_cart_go_home);
        button_checkout         = (Button)   this.findViewById(R.id.button_cart_go_checkout);
        textView_cartCount      = (TextView) this.findViewById(R.id.output_cart_count);
        textView_cartTotal      = (TextView) this.findViewById(R.id.output_cart_total);
        textView_cart_itemDesc  = (TextView) this.findViewById(R.id.output_cart_itemDesc);
        cartEngine = new ShoppingCart(cartItems);

        textView_cartCount.setText(Integer.toString(cartEngine.calculateNumItems()));
        textView_cartTotal.setText(Double.toString(cartEngine.calculateTotal()));

        if(cartItems.isEmpty())
        {
            button_checkout.setEnabled(false);
        }

        cartArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, cartItems)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                System.out.println("Accessing " + cartItems.get(position));
                Item currItem = cartEngine.accessItem(cartItems.get(position));
                System.out.println("Success Accessing " + cartItems.get(position));

                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(currItem.getItemName() + " : " +currItem.getItemCost());
                text2.setText(currItem.getItemDesc());

                return view;
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_cart);
        listView.setAdapter(cartArrayAdapter);

        //==========================================================================================

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == selectedItemPosition)
                {
                    listView.setItemChecked(position, false);
                    button_increment.setEnabled(false);
                    button_remove.setEnabled(false);
                    selectedItemPosition = -1;
                }
                else
                {
                    listView.setItemChecked(position, true);
                    button_increment.setEnabled(true);
                    button_remove.setEnabled(true);
                    selectedItemPosition = position;
                    selectedItemAtPosition(position);
                }
            }
        });

        //==========================================================================================

        button_increment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(cartEngine.calculateNumItems() == 0)
                {
                    Toast.makeText(CartActivity.this, "You don't have any items in your cart!", Toast.LENGTH_LONG).show();
                }
                else if(selectedItemPosition != -1)
                {
                    String currItem = cartArrayAdapter.getItem(selectedItemPosition);
                    cartEngine.incrementItem(currItem);
                    cartArrayAdapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Incremented " + currItem + " in your shopping cart!", Toast.LENGTH_LONG).show();

                    textView_cartCount.setText(Integer.toString(cartEngine.calculateNumItems()));
                    textView_cartTotal.setText(Double.toString(cartEngine.calculateTotal()));
                }
                else
                {
                    Toast.makeText(CartActivity.this, "You don't have any item selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //==========================================================================================

        button_remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(cartEngine.calculateNumItems() == 0)
                {
                    Toast.makeText(CartActivity.this, "You don't have any items in your cart!", Toast.LENGTH_LONG).show();
                }
                else if(selectedItemPosition != -1)
                {
                    String currItem = cartArrayAdapter.getItem(selectedItemPosition);
                    cartEngine.removeItem(currItem);
                    cartArrayAdapter.notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Removed " + currItem + " from your shopping cart!", Toast.LENGTH_LONG).show();

                    textView_cartCount.setText(Integer.toString(cartEngine.calculateNumItems()));
                    textView_cartTotal.setText(Double.toString(cartEngine.calculateTotal()));

                    if(cartItems.isEmpty())
                    {
                        button_checkout.setEnabled(false);
                    }
                }
                else
                {
                    Toast.makeText(CartActivity.this, "You don't have any item selected!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //==========================================================================================

        button_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent cartIntent = new Intent(CartActivity.this, HomeActivity.class);
                cartIntent.putExtra("userAccount", userAccount);
                cartIntent.putStringArrayListExtra("cartItems", cartEngine.getCartList());

                System.out.println(userAccount);
                System.out.println(cartItems);
                CartActivity.this.startActivity(cartIntent);
            }
        });

        //==========================================================================================

        button_checkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent deliveryIntent = new Intent(CartActivity.this, DeliveryOptionsActivity.class);
                deliveryIntent.putExtra("userAccount", userAccount);
                deliveryIntent.putStringArrayListExtra("cartItems", cartItems);

                System.out.println(userAccount);
                System.out.println(cartItems);
                CartActivity.this.startActivity(deliveryIntent);
            }
        });
    }

    public void selectedItemAtPosition(int position)
    {
        Item selectedItem = Services.getItemPersistence().accessItem(cartArrayAdapter.getItem(position));

        textView_cart_itemDesc  = (TextView) findViewById(R.id.output_cart_itemDesc);
        textView_cart_itemDesc.setText(selectedItem.getItemDesc());
    }
}