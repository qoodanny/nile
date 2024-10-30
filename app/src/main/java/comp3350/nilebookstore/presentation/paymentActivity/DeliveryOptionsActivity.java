package comp3350.nilebookstore.presentation.paymentActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.business.payment.DeliveryOptions;

public class DeliveryOptionsActivity extends AppCompatActivity
{
    private Button button_home, button_cart, button_payment;
    private RadioGroup radioGroup_pickup_delivery, radioGroup_emailChoice;
    private LinearLayout layout_locationDetails, layout_postDetails;
    private EditText editText_email, editText_address, editText_city, editText_postal;
    private TextView output_deliveryTime;
    private Spinner spinner_province;
    private ArrayList<String> cartItems;
    private String userAccount;
    private DeliveryOptions deliveryEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options);
        cartItems    = getIntent().getStringArrayListExtra("cartItems");

        Bundle extras = getIntent().getExtras();
        if(extras != null) {userAccount = extras.getString("userAccount");}

        button_home                 = (Button)      this.findViewById(R.id.button_delivery_go_home);
        button_cart                 = (Button)      this.findViewById(R.id.button_delivery_go_cart);
        button_payment              = (Button)      this.findViewById(R.id.button_delivery_go_payment);
        radioGroup_pickup_delivery  = (RadioGroup)  this.findViewById(R.id.radioGroup_pickup_delivery);
        radioGroup_emailChoice      = (RadioGroup)  this.findViewById(R.id.radioGroup_emailChoice);
        layout_locationDetails      = (LinearLayout)this.findViewById(R.id.layout_delivery_locationDetails);
        layout_postDetails          = (LinearLayout)this.findViewById(R.id.layout_delivery_postalDetails);
        editText_email              = (EditText)    this.findViewById(R.id.input_delivery_email);
        editText_address            = (EditText)    this.findViewById(R.id.input_delivery_address);
        editText_city               = (EditText)    this.findViewById(R.id.input_delivery_city);
        editText_postal             = (EditText)    this.findViewById(R.id.input_delivery_postal);
        output_deliveryTime         = (TextView)    this.findViewById(R.id.output_delivery_estimate);
        spinner_province            = (Spinner)     this.findViewById(R.id.spinner_province);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.provinces, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_province.setAdapter(adapter);

        deliveryEngine = new DeliveryOptions();

        //==========================================================================================

        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String province = parent.getItemAtPosition(position).toString();
                if (!province.equals("Province"))
                {
                    deliveryEngine.setProvince(province);
                    output_deliveryTime.setText(deliveryEngine.estimateDeliveryTime() +" day(s)\n");
                }
                else
                {
                    Toast.makeText(DeliveryOptionsActivity.this, "You must select a province", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        //==========================================================================================

        radioGroup_pickup_delivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioButton_pickup:
                        // Handle the "Pick Up" option
                        deliveryEngine.setPickUp(true);

                        editText_address.setText("University of Manitoba Campus Bookstore");
                        editText_address.setEnabled(false);

                        editText_city.setVisibility(View.GONE);
                        layout_postDetails.setVisibility(View.GONE);

                        deliveryEngine.setProvince("MB-Pickup");
                        output_deliveryTime.setText("Ready for pickup in " + deliveryEngine.estimateDeliveryTime() + " day");
                        break;

                    case R.id.radioButton_delivery:
                        // Handle the "Delivery" option
                        deliveryEngine.setPickUp(false);

                        editText_address.setHint("Address Line");
                        editText_address.setText("");
                        editText_address.setEnabled(true);

                        editText_city.setVisibility(View.VISIBLE);
                        layout_postDetails.setVisibility(View.VISIBLE);

                        output_deliveryTime.setText("");
                        break;
                }
            }
        });

        //==========================================================================================

        radioGroup_emailChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup emailRadioGroup, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.radioButton_customEmail:
                        // Handle the "Enter Custom Email" option
                        editText_email.setText("");
                        editText_email.setHint("Receiver Email");
                        break;

                    case R.id.radioButton_accountEmail:
                        System.out.println("Selected Account Email");
                        System.out.println(userAccount);

                        // Handle the "Use Account Email" option
                        editText_email.setText(userAccount);
                        // Set the account email as the receiver email
                        deliveryEngine.setReceiverEmail(userAccount);
                        break;
                }
            }
        });

        //==========================================================================================

        button_payment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String emailString      = editText_email.getText().toString();
                String addressString    = editText_address.getText().toString();
                String cityString       = editText_city.getText().toString();
                String postalString     = editText_postal.getText().toString();
                String provinceString   = deliveryEngine.getProvince();

                if((emailString.equals("") || addressString.equals("") || cityString.equals("") || postalString.equals("")) && editText_address.isEnabled())
                {
                    Toast.makeText(DeliveryOptionsActivity.this, "User E-mail, Address, City, or Postal field must not be empty", Toast.LENGTH_LONG).show();
                }
                else if(provinceString.equals("") && editText_address.isEnabled())
                {
                    Toast.makeText(DeliveryOptionsActivity.this, "You must select a province", Toast.LENGTH_LONG).show();
                }
                else if(!deliveryEngine.checkValidEmail(emailString))
                {
                    Toast.makeText(DeliveryOptionsActivity.this, "Invalid E-mail format", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DeliveryOptionsActivity.this, "Going to Payment", Toast.LENGTH_LONG).show();

                    Intent paymentIntent = new Intent(DeliveryOptionsActivity.this, PaymentActivity.class);

                    deliveryEngine.setAddress(addressString);
                    deliveryEngine.setCity(cityString);
                    deliveryEngine.setPostalCode(postalString);

                    /*
                    paymentIntent.putExtra("userAccount", userAccount);
                    paymentIntent.putStringArrayListExtra("cartItems", cartItems);
                    paymentIntent.putExtra("location", addressString + " "
                                                                    + cityString + " "
                                                                    + postalString + " "
                                                                    + "to be delivered in " + deliveryEngine.estimateDeliveryTime() + " day(s)\n");
                    */

                    Bundle data = new Bundle();
                    data.putStringArrayList("cartItems", cartItems);
                    data.putParcelable("delivery_options", deliveryEngine);
                    paymentIntent.putExtras(data);

                    DeliveryOptionsActivity.this.startActivity(paymentIntent);
                }
            }
        });

        //==========================================================================================
    }
}