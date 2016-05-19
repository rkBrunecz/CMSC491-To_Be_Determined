package com.example.randy.to_be_determined;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * CreateAcountActivity
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This activity allows a user to create and account with the spot swap application. It contains
 * some error checking and makes sure the user does attempt to use the same phone number, user
 * name, or email address as another registered user.
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private EditText passwordEdit, userNameEdit, confirmPasswordEdit, emailEdit, phoneNumberEdit;
    private Button createAccountBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        /* Get ids */
        passwordEdit = (EditText)findViewById(R.id.enterPassEdit);
        userNameEdit = (EditText)findViewById(R.id.enterUserEdit);
        confirmPasswordEdit= (EditText)findViewById(R.id.confirmPassEdit);
        emailEdit = (EditText)findViewById(R.id.enterEmailEdit);
        phoneNumberEdit = (EditText)findViewById(R.id.enterPhoneNumberEdit);
        createAccountBtn = (Button)findViewById(R.id.createAccountBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.spotSwapCreateAccount), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterEmailTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterUsernameTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterPassTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.confirmPassTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterPhoneNumberTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", cancelBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", createAccountBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", emailEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", userNameEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", passwordEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", confirmPasswordEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", phoneNumberEdit, getAssets());

        /* Set up on click listeners */
        cancelBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.createAccountBtn:
                boolean validAccount = true; //True if the no issues were found, allowing for an account to be created. False otherwise.

                if(!emailEdit.getText().toString().toLowerCase().contains("@umbc.edu") || emailEdit.getText().length() == 9)
                {
                    if(emailEdit.getText().length() == 9 && emailEdit.getText().toString().toLowerCase().contains("@umbc.edu"))
                        Toast.makeText(getApplicationContext(), "Cannot have just '@umbc.edu'!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Must have a valid UMBC email address!", Toast.LENGTH_SHORT).show();

                    emailEdit.setHintTextColor(Color.RED);
                    emailEdit.setText(""); //Clear email text

                    validAccount = false;
                }
                if(passwordEdit.getText().length() < 5) //Minimum length of a password will be five characters currently
                {
                    Toast.makeText(getApplicationContext(), "No password was entered or password was too short (minimum of 5 characters)!", Toast.LENGTH_LONG).show();

                    passwordEdit.setHintTextColor(Color.RED);
                    confirmPasswordEdit.setHintTextColor(Color.RED);

                    /* Clear password text boxes */
                    passwordEdit.setText("");
                    confirmPasswordEdit.setText("");

                    validAccount = false;
                }
                if(!passwordEdit.getText().toString().contentEquals(confirmPasswordEdit.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "The passwords you have provided are not equivalent!", Toast.LENGTH_SHORT).show();

                    passwordEdit.setHintTextColor(Color.RED);
                    confirmPasswordEdit.setHintTextColor(Color.RED);

                    /* Clear password text boxes */
                    passwordEdit.setText("");
                    confirmPasswordEdit.setText("");

                    validAccount = false;
                }
                if(userNameEdit.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Need to enter a username!", Toast.LENGTH_SHORT).show();

                    userNameEdit.setHintTextColor(Color.RED);

                    validAccount = false;
                }
                if(phoneNumberEdit.getText().length() != 10)
                {
                    Toast.makeText(getApplicationContext(), "Phone number must be 10 digits!", Toast.LENGTH_SHORT).show();

                    if(phoneNumberEdit.getText().length() == 0)
                        phoneNumberEdit.setHintTextColor(Color.RED);

                    validAccount = false;
                }

                /*Ensure that the username, password, and email address do not contain spaces in them. */
                if(userNameEdit.getText().toString().contains(" "))
                {
                    Toast.makeText(getApplicationContext(), "Username cannot contains spaces!", Toast.LENGTH_SHORT).show();

                    userNameEdit.setHintTextColor(Color.RED);

                    validAccount = false;
                }
                if(passwordEdit.getText().toString().contains(" "))
                {
                    Toast.makeText(getApplicationContext(), "Passwords cannot contain spaces!", Toast.LENGTH_SHORT).show();

                    passwordEdit.setHintTextColor(Color.RED);
                    passwordEdit.setText("");

                    validAccount = false;
                }
                if(emailEdit.getText().toString().contains(" "))
                {
                    Toast.makeText(getApplicationContext(), "Email address must not contain spaces!", Toast.LENGTH_SHORT).show();

                    emailEdit.setHintTextColor(Color.RED);
                    emailEdit.setText(""); //Clear email text

                    validAccount = false;
                }

                /* Create an account! */
                if(validAccount) {
                    Toast.makeText(getApplicationContext(), "Creating account...", Toast.LENGTH_SHORT).show();

                    new CheckUserCredentials().execute(userNameEdit.getText().toString(), emailEdit.getText().toString(), phoneNumberEdit.getText().toString());
                }

                break;

            case R.id.cancelBtn:
                finish(); //Destroy activity

                break;
        }
    }

    public class CheckUserCredentials extends AsyncTask<String, Void, String> {
       ProgressDialog loading;

        protected String doInBackground(String... user_creds)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/check_for_account.php?username=" + user_creds[0] + "&email=" + user_creds[1] + "&phonenum=" + user_creds[2]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine();
                Log.i("Response", s);

                urlConnection.disconnect();
            } catch(IOException e) {
                System.out.println("Error: " + "http://mpss.csce.uark.edu/~palande1/check_for_account.php?username=" + user_creds[0] + "&email=" + user_creds[1] + "&phonenum=" + user_creds[2]);
                e.printStackTrace();
            }

            return s;
        }

        protected void onPreExecute()
        {
            loading = ProgressDialog.show(CreateAccountActivity.this, "Checking credentials", "Checking credentials...", true);
        }

        protected void onPostExecute(String result)
        {
            boolean createAccount = true;

            loading.dismiss();

            if (result.charAt(0) == '1')
            {
                Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                userNameEdit.setHintTextColor(Color.RED);
                userNameEdit.setText(""); //Clear email text

                createAccount = false;
            }
            if (result.charAt(2) == '1')
            {
                Toast.makeText(getApplicationContext(), "Provided email is associated with another account already!", Toast.LENGTH_LONG).show();
                emailEdit.setHintTextColor(Color.RED);
                emailEdit.setText(""); //Clear email text

                createAccount = false;
            }
            if(result.charAt(4) == '1')
            {
                Toast.makeText(getApplicationContext(), "Provided phone number is associated with another account already!", Toast.LENGTH_LONG).show();
                phoneNumberEdit.setHintTextColor(Color.RED);
                phoneNumberEdit.setText(""); //Clear email text

                createAccount = false;
            }

            if(createAccount)
                new InsertAccount().execute(userNameEdit.getText().toString(), passwordEdit.getText().toString(), emailEdit.getText().toString(), phoneNumberEdit.getText().toString());
        }
    }

    public class InsertAccount extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        protected String doInBackground(String... user_creds)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/insert_user.php?username=" + user_creds[0] + "&password=" + user_creds[1] + "&email=" + user_creds[2] + "&phonenum=" + user_creds[3]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine();
                Log.i("Response", s);

                urlConnection.disconnect();
            } catch(IOException e) {
                System.out.println("Error: " + "http://mpss.csce.uark.edu/~palande1/fetch_user.php?username=" + user_creds[0] + "&password=" + user_creds[1]);
                e.printStackTrace();
            }

            return s;
        }

        protected void onPreExecute()
        {
            loading = ProgressDialog.show(CreateAccountActivity.this, "Creating account", "Creating account...", true);
        }

        protected void onPostExecute(String result)
        {
            loading.dismiss();

            if (result.contains("Success"))
            {
                Toast.makeText(getApplicationContext(), "Account created!", Toast.LENGTH_SHORT).show();
                finish(); //Destroy activity
            }
            else
                Toast.makeText(getApplicationContext(), "Account creation failed, please try again.", Toast.LENGTH_SHORT).show();

        }
    }
}
