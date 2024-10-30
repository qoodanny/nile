package comp3350.nilebookstore.presentation.accountActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.business.account.AccountVerification;
import comp3350.nilebookstore.presentation.shoppingActivity.HomeActivity;

public class LoginActivity extends AppCompatActivity
{
    private EditText editText_email, editText_password;
    private Button button_login, button_signup;
    private ArrayList<String> cartItems;

    private String userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_email    = (EditText) findViewById(R.id.input_email);
        editText_password = (EditText) findViewById(R.id.input_password);
        button_login      = (Button) findViewById(R.id.button_login);
        button_signup     = (Button) findViewById(R.id.button_signup);
        cartItems = new ArrayList<>();

        button_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String emailString          = editText_email.getText().toString();
                String passString           = editText_password.getText().toString();
                AccountVerification account = new AccountVerification(emailString, passString);

                if(emailString.equals("") || passString.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "User E-mail or Password field must not be empty", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkValidEmail())
                {
                    Toast.makeText(LoginActivity.this, "Invalid E-mail format", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkValidPassword())
                {
                    Toast.makeText(LoginActivity.this, "Invalid Password format", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkAccountExists())
                {
                    Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkAccountLogin())
                {
                    Toast.makeText(LoginActivity.this, "User password does not match", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();

                    userAccount = emailString;

                    Intent successIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    successIntent.putExtra("userAccount", userAccount);
                    successIntent.putStringArrayListExtra("cartItems", cartItems);

                    System.out.println(userAccount);
                    System.out.println(cartItems);

                    LoginActivity.this.startActivity(successIntent);
                }
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(LoginActivity.this, "Please enter your new account details", Toast.LENGTH_LONG).show();

                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(signUpIntent);
            }
        });
    }
}