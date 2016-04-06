package com.example.randy.to_be_determined;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Randy on 4/3/2016.
 */
public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    /* PUBLIC VARIABLES */
    public final static String EXTRA_MESSAGE = "com.example.randy.MESSAGE";

    /* PRIVATE VARIABLES */
    private Intent mainIntent;
    private EditText passwordText, userNameText;
    private Button loginBtn;
    private int numAttempts = 3; //Lock a user out from logging in if this reaches zero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>To Be Determined</font>"));

        /* Get ids */
        passwordText = (EditText)findViewById(R.id.passwordText);
        userNameText = (EditText)findViewById(R.id.emailText);
        loginBtn = (Button)findViewById(R.id.loginButton);
        mainIntent = new Intent(this, MainActivity.class); //Create an intent to launch the main activity upon successfully logging in

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                if (passwordText.getText().length() > 0 && userNameText.getText().length() > 0)
                {
                    mainIntent.putExtra(EXTRA_MESSAGE, userNameText.getText().toString());
                    startActivity(mainIntent);
                    finish(); //Destroy activity
                }
                else if(userNameText.getText().length() == 0 || passwordText.getText().length() == 0)
                {
                    if(userNameText.getText().length() == 0)
                    {
                        userNameText.setHintTextColor(Color.RED);
                        Toast.makeText(userNameText.getContext(), "No username was entered!", Toast.LENGTH_SHORT).show();
                    }
                    if (passwordText.getText().length() == 0)
                    {
                        passwordText.setHintTextColor(Color.RED);
                        Toast.makeText(passwordText.getContext(), "No password was entered!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Either the password or username entered is incorrect!", Toast.LENGTH_LONG).show();
                    numAttempts--;

                    //Lock the user out from logging in if number of attempts reaches zero
                    if(numAttempts == 0)
                    {
                        loginBtn.setEnabled(false);
                        loginBtn.setBackgroundColor(Color.GRAY);
                        Toast.makeText(getApplicationContext(), "Too many attempts, please try again later.", Toast.LENGTH_LONG).show();
                    }
                }

                break;

        }
    }
}
