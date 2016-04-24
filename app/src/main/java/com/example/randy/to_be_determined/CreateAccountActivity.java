package com.example.randy.to_be_determined;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>CREATE ACCOUNT</font>"));

        /* Get ids */
        passwordEdit = (EditText)findViewById(R.id.enterPassEdit);
        userNameEdit = (EditText)findViewById(R.id.enterUserEdit);
        confirmPasswordEdit= (EditText)findViewById(R.id.confirmPassEdit);
        emailEdit = (EditText)findViewById(R.id.enterEmailEdit);
        createAccountBtn = (Button)findViewById(R.id.createAccountBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", (TextView) findViewById(R.id.spotSwapCreateAccount), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.enterEmailTxt), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.enterUsernameTxt), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.enterPassTxt), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.confirmPassTxt), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", cancelBtn, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", createAccountBtn, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Light.otf", emailEdit, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Light.otf", userNameEdit, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Light.otf", passwordEdit, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Light.otf", confirmPasswordEdit, getAssets());

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
                if(passwordEdit.getText().length() < 5) //Minimum length of a password will be five characters currently
                {
                    Toast.makeText(getApplicationContext(), "No password was entered or password was too short!", Toast.LENGTH_SHORT).show();

                    passwordEdit.setHintTextColor(Color.RED);
                    confirmPasswordEdit.setHintTextColor(Color.RED);

                    /* Clear password text boxes */
                    passwordEdit.setText("");
                    confirmPasswordEdit.setText("");

                    validAccount = false;
                }

                /* Create an account! */
                if(validAccount)
                {
                    Toast.makeText(getApplicationContext(), "Account created!", Toast.LENGTH_SHORT).show();
                    finish(); //Destroy activity
                }

                break;

            case R.id.cancelBtn:
                finish(); //Destroy activity

                break;
        }
    }
}
