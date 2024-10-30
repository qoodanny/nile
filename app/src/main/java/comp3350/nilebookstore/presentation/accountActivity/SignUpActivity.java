package comp3350.nilebookstore.presentation.accountActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import comp3350.nilebookstore.R;
import comp3350.nilebookstore.application.Services;
import comp3350.nilebookstore.business.account.AccountVerification;
import comp3350.nilebookstore.objects.User;

public class SignUpActivity extends AppCompatActivity
{
    private EditText editText_username, editText_email, editText_passwordA, editText_passwordB;
    private Spinner spinner_department;
    private String departmentString = "";
    private Button button_create;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText_username    = (EditText) this.findViewById(R.id.input_new_username);
        editText_email       = (EditText) this.findViewById(R.id.input_new_email);
        editText_passwordA   = (EditText) this.findViewById(R.id.input_new_passwordA);
        editText_passwordB   = (EditText) this.findViewById(R.id.input_new_passwordB);
        button_create        = (Button)   this.findViewById(R.id.button_create);
        spinner_department   = (Spinner)  this.findViewById(R.id.spinner_department);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departments, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_department.setAdapter(adapter);

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                departmentString = parent.getItemAtPosition(position).toString();
                System.out.println(departmentString);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        button_create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String userString   = editText_username.getText().toString();
                String emailString  = editText_email.getText().toString();
                String passAString  = editText_passwordA.getText().toString();
                String passBString  = editText_passwordB.getText().toString();

                AccountVerification account = new AccountVerification(emailString, passAString);

                if(userString.equals("") || emailString.equals("") || passAString .equals("") || passBString.equals(""))
                {
                    Toast.makeText(SignUpActivity.this, "User Name, E-mail, or Password field must not be empty!", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkValidEmail())
                {
                    Toast.makeText(SignUpActivity.this, "Invalid E-mail format", Toast.LENGTH_LONG).show();
                }
                else if(account.checkAccountExists())
                {
                    Toast.makeText(SignUpActivity.this, "Account already exists", Toast.LENGTH_LONG).show();
                }
                else if(!account.checkValidPassword())
                {
                    Toast.makeText(SignUpActivity.this, "Invalid Password format", Toast.LENGTH_LONG).show();
                }
                else if(!passAString.equals(passBString))
                {
                    Toast.makeText(SignUpActivity.this, "Initial and re-entered password must match", Toast.LENGTH_LONG).show();
                }
                else
                {
                    User newUser = new User(userString, passAString, emailString, departmentString);
                    Services.getUserPersistence().insertUser(newUser);
                    Toast.makeText(SignUpActivity.this, "User created! Going back to login page...", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    SignUpActivity.this.startActivity(mainIntent);
                }
            }
        });
    }
}