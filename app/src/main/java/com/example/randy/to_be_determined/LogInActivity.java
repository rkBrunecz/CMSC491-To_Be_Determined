package com.example.randy.to_be_determined;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Randy on 4/3/2016.
 */
public class LogInActivity extends AppCompatActivity {

    /* PRIVATE VARIABLES */
    EditText passwordText, emailText;
    Button loginBtn;
    int numAttempts = 3; //Lock a user out from logging in if this reaches zero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Get ids */
        passwordText = (EditText)findViewById(R.id.passwordText);
        emailText = (EditText)findViewById(R.id.emailText);
        loginBtn = (Button)findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordText.getText().length() > 0 &&
                        emailText.getText().toString().contains("@umbc.edu") &&
                        emailText.getText().length() > 9) //Want to ensure length is greater than 9 to avoid a user inputting only the "@umbc.edu
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT);
                else if(passwordText.getText().toString().equalsIgnoreCase("admin") && emailText.getText().toString().equalsIgnoreCase("admin")) //Admin log in
                    Toast.makeText(getApplicationContext(), "Logging in as Admin...", Toast.LENGTH_SHORT);
                else if(emailText.getText().length() == 0 || passwordText.getText().length() == 0)
                {
                    if(passwordText.getText().length() == 0)
                        passwordText.setHintTextColor(Color.RED);
                    if(emailText.getText().length() == 0)
                        emailText.setHintTextColor(Color.RED);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Either the password or email address entered is incorrect!", Toast.LENGTH_LONG);
                    numAttempts--;

                    //Lock the user out from logging in if number of attempts reaches zero
                    if(numAttempts == 0)
                        loginBtn.setEnabled(false);
                }
            }
        });
    }
}
