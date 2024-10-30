package comp3350.nilebookstore.presentation.paymentActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import comp3350.nilebookstore.R;
import comp3350.nilebookstore.business.payment.DeliveryOptions;
import comp3350.nilebookstore.business.payment.PaymentVerification;
import comp3350.nilebookstore.business.shopping.ShoppingCart;
import comp3350.nilebookstore.objects.Purchase;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class PaymentActivity extends AppCompatActivity
{
    EditText editText_cardNum, editText_expMonth, editText_expYear, editText_cardCVC;
    TextView editText_success_user, editText_success_items, editText_success_location, editText_success_card;
    LinearLayout layout_payment_success;
    Button button_payNow, button_home;
    ArrayList<String> cartItems;
    String userAccount, deliveryAddress;
    PaymentVerification paymentEngine;

    DeliveryOptions     deliveryEngine;

    ShoppingCart        cartEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cartItems  = getIntent().getStringArrayListExtra("cartItems");

        Bundle data = getIntent().getExtras();
        if(data != null)
        {
            cartItems  = data.getStringArrayList("cartItems");

            deliveryEngine  = data.getParcelable("delivery_options");
            userAccount     = deliveryEngine.getReceiverEmail();
            deliveryAddress = "location" + deliveryEngine.getAddress() + " " + deliveryEngine.getCity() +
                    " " + deliveryEngine.getPostalCode() + " to be delivered in " +
                    deliveryEngine.estimateDeliveryTime() + " day(s)\n";

            cartEngine = new ShoppingCart(cartItems);
        }

        editText_cardNum    = (EditText) this.findViewById(R.id.input_payment_cardNum);
        editText_expMonth   = (EditText) this.findViewById(R.id.input_payment_expMonth);
        editText_expYear    = (EditText) this.findViewById(R.id.input_payment_expYear);
        editText_cardCVC    = (EditText) this.findViewById(R.id.input_payment_cardCVC);
        button_payNow       = (Button)   this.findViewById(R.id.button_payment_purchase);

        layout_payment_success      = (LinearLayout) this.findViewById(R.id.layout_payment_success);
        editText_success_user       = (TextView) this.findViewById(R.id.output_paySuccess_user);
        editText_success_items      = (TextView) this.findViewById(R.id.output_paySuccess_items);
        editText_success_location   = (TextView) this.findViewById(R.id.output_paySuccess_location);
        editText_success_card       = (TextView) this.findViewById(R.id.output_paySuccess_card);
        button_home                 = (Button)   this.findViewById(R.id.button_payment_go_home);

        button_payNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String cardString   = editText_cardNum.getText().toString();
                String monthString  = editText_expMonth.getText().toString();
                String yearString   = editText_expYear.getText().toString();
                String cvcString    = editText_cardCVC.getText().toString();

                if(cardString.equals("") || monthString.equals("") || yearString.equals("")  || cvcString.equals(""))
                {
                    Toast.makeText(PaymentActivity.this, "Card Number, Expiry Month/Year, or CVC field must not be empty!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    int monthInt    = Integer.parseInt(editText_expMonth.getText().toString());
                    int yearInt     = Integer.parseInt(editText_expYear.getText().toString());
                    int cvcInt      = Integer.parseInt(editText_cardCVC.getText().toString());

                    paymentEngine = new PaymentVerification(cardString, monthInt, yearInt, cvcInt);

                    if(!paymentEngine.verifyCardNum())
                    {
                        Toast.makeText(PaymentActivity.this, "Invalid Card Number format", Toast.LENGTH_LONG).show();
                        editText_cardNum.setHint("Card Number (16 Digits)");
                    }
                    else if(!paymentEngine.verifyExpMonth())
                    {
                        Toast.makeText(PaymentActivity.this, "Invalid Expiry Month format", Toast.LENGTH_LONG).show();
                        editText_expMonth.setHint("MM");
                    }
                    else if(!paymentEngine.verifyExpYear())
                    {
                        Toast.makeText(PaymentActivity.this, "Invalid Expiry Year format", Toast.LENGTH_LONG).show();
                        editText_expYear.setHint("YYYY");
                    }
                    else if(!paymentEngine.verifyCardCVC())
                    {
                        Toast.makeText(PaymentActivity.this, "Invalid CVC format", Toast.LENGTH_LONG).show();
                        editText_cardCVC.setHint("Card CVC");
                    }
                    else
                    {
                        Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_LONG).show();
                        button_payNow.setEnabled(false);

                        layout_payment_success.setVisibility(View.VISIBLE);

                        /*
                        editText_success_user.setText(userAccount);
                        editText_success_items.setText(cartItems.toString());
                        editText_success_location.setText(deliveryAddress);
                        String cleanCardString = cardString.replaceAll(" ", "");
                        editText_success_card.setText("**** **** **** " + cleanCardString.substring(cleanCardString.length() - 4));
                        */
                        String cleanCardString = cardString.replaceAll(" ", "");

                        //Insert Purchase
                        Purchase order = new Purchase(userAccount, deliveryEngine.generatePurchaseID(),
                                cleanCardString, monthString + "/" + yearString, cvcInt,
                                cartEngine.getCartList(), cartEngine.calculateTotal(), deliveryEngine.getPickUp(),
                                new String[] {deliveryEngine.getAddress(), deliveryEngine.getCity(), deliveryEngine.getProvince(), deliveryEngine.getPostalCode()});

                        System.out.println(order.toString());

                        editText_success_user.setText(order.getUser());
                        editText_success_items.setText(order.getItemList().toString() + " Totalling $ " + order.getTotalCost());
                        editText_success_location.setText(order.getUserAddr()[0] + " " + order.getUserAddr()[1] + " " + order.getUserAddr()[2] + " " + order.getUserAddr()[3]);
                        editText_success_card.setText("**** **** **** " + order.getCardNum().substring(order.getCardNum().length() - 4));
                    }
                }
            }
        });

        button_home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(PaymentActivity.this, "Going back to home", Toast.LENGTH_LONG).show();

                Intent homeIntent = new Intent(PaymentActivity.this, HomeActivity.class);
                homeIntent.putExtra("userAccount", userAccount);

                cartItems.removeAll(cartItems);
                homeIntent.putStringArrayListExtra("cartItems", cartItems);

                PaymentActivity.this.startActivity(homeIntent);
            }
        });
    }
}