package com.example.randy.to_be_determined;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Randy on 4/7/2016.
 */
public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private EditText passwordEdit, userNameEdit, confirmPasswordEdit, emailEdit;
    private Button createAccountBtn, cancelBtn;
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        /* Get ids */
        passwordEdit = (EditText)findViewById(R.id.enterPassEdit);
        userNameEdit = (EditText)findViewById(R.id.enterUserEdit);
        confirmPasswordEdit= (EditText)findViewById(R.id.confirmPassEdit);
        emailEdit = (EditText)findViewById(R.id.enterEmailEdit);
        createAccountBtn = (Button)findViewById(R.id.createAccountBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.spotSwapCreateAccount), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterEmailTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterUsernameTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.enterPassTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.confirmPassTxt), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", cancelBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", createAccountBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", emailEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", userNameEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", passwordEdit, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", confirmPasswordEdit, getAssets());

        /* Set up on click listeners */
        cancelBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);

        dbhelper = new DatabaseHelper(getApplicationContext());
        db = dbhelper.getWritableDatabase();
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

                /* Create an account! */
                if(validAccount) {
                    long returnVal = createUser(userNameEdit.getText().toString(), passwordEdit.getText().toString(), emailEdit.getText().toString());
                    if (returnVal >= 0) {
                        Toast.makeText(getApplicationContext(), "Account created!", Toast.LENGTH_SHORT).show();
                        finish(); //Destroy activity
                    } else if (returnVal == -1)
                        Toast.makeText(getApplicationContext(), "Account creation failed! Please try again later.", Toast.LENGTH_LONG).show();
                    else if (returnVal == -2)
                    {
                        Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                        userNameEdit.setHintTextColor(Color.RED);
                        userNameEdit.setText(""); //Clear email text
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Provided email is associated with another account already!", Toast.LENGTH_LONG).show();
                        emailEdit.setHintTextColor(Color.RED);
                        emailEdit.setText(""); //Clear email text
                    }

                    validAccount = false;
                }

                break;

            case R.id.cancelBtn:
                finish(); //Destroy activity

                break;
        }
    }

    public long createUser(String userName, String password, String email)
    {
        Cursor c = fetchUsername(userName);

        if(c.getCount() > 0)
            return -2;

        c = fetchEmail(email);
        if(c.getCount() > 0)
            return -3;

        c.close();

        ContentValues values = createContentValues(userName, password, email);
        return db.insert("users", null,values);
    }

    private ContentValues createContentValues(String userName, String password, String email)
    {
        ContentValues values = new ContentValues();
        values.put("username", userName);
        values.put("password", password);
        values.put("email", email);
        return values;
    }

    public Cursor fetchUsername(String name)
    {
        return db.query("users", new String[]{"username"}, "username=?", new String[]{name}, null, null, null);
    }

    public Cursor fetchEmail(String email)
    {
        return db.query("users", new String[]{"email"}, "email=?", new String[]{email}, null, null, null);
    }
}
