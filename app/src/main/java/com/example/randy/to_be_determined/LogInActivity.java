package com.example.randy.to_be_determined;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
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
 * Created by Randy on 4/3/2016.
 */
public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    /* PRIVATE VARIABLES */
    private Intent mainIntent;
    private EditText passwordText, userNameText;
    private Button loginBtn;
    private TextView forgotPassword, signUp;
    private int numAttempts = 5; //Lock a user out from logging in if this reaches zero

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Get ids */
        passwordText = (EditText)findViewById(R.id.passwordText);
        userNameText = (EditText)findViewById(R.id.usernameText);
        loginBtn = (Button)findViewById(R.id.loginButton);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        signUp = (TextView)findViewById(R.id.signUp);

        mainIntent = new Intent(this, MainActivity.class); //Create an intent to launch the main activity upon successfully logging in

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.spotSwapLogIn), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.usernamePlain), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.passwordPlain), getAssets());
        CustomFont.setCustomFont("VitaStd-LightItalic.ttf", forgotPassword, getAssets());
        CustomFont.setCustomFont("VitaStd-LightItalic.ttf", signUp, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", loginBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", userNameText, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", passwordText, getAssets());

        /* Set up on click listeners */
        loginBtn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        /* Underline the sign up and forgot password text */
        signUp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgotPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                if(userNameText.getText().length() == 0 || passwordText.getText().length() < 5)
                {
                    if(userNameText.getText().length() == 0)
                    {
                        userNameText.setHintTextColor(Color.RED);
                        Toast.makeText(userNameText.getContext(), "No username was entered!", Toast.LENGTH_SHORT).show();
                    }
                    if (passwordText.getText().length() < 5)
                    {
                        passwordText.setHintTextColor(Color.RED);
                        Toast.makeText(passwordText.getContext(), "No password was entered or password was too short (minimum of 5 characters)!", Toast.LENGTH_LONG).show();
                    }
                }
                else //Check to see if the username and password do not match an account in the database.
                {
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();

                    //Disable the login button
                    loginBtn.setEnabled(false);
                    loginBtn.setAlpha(0.5f);

                    new RetrieveInputStream().execute(userNameText.getText().toString(), passwordText.getText().toString());
                }

                break;

            case R.id.forgotPassword:

                break;

            case R.id.signUp:
                /* Clear out the password and username fields */
                passwordText.setText("");
                userNameText.setText("");

                Intent createAccountIntent = new Intent(this, CreateAccountActivity.class); //Create an intent to launch the create account activity
                startActivity(createAccountIntent);
                break;
        }
    }

    public class RetrieveInputStream extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... user_creds)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/log_user_in.php?username=" + user_creds[0] + "&password=" + user_creds[1]);
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

        protected void onPostExecute(String result)
        {
            int val = Integer.valueOf(result);
            if(val <= 0)
            {
                if(val == 0)
                    Toast.makeText(getApplicationContext(), "Either the username or password entered is incorrect!", Toast.LENGTH_LONG).show();
                else if(val == -1)
                    Toast.makeText(getApplicationContext(), "This user is already logged in!", Toast.LENGTH_LONG).show();

                numAttempts--;

                loginBtn.setEnabled(true);
                loginBtn.setAlpha(1f);

                //Lock the user out from logging in if number of attempts reaches zero
                if (numAttempts == 0) {
                    loginBtn.setEnabled(false);
                    loginBtn.setAlpha(0.5f);
                    Toast.makeText(getApplicationContext(), "Too many attempts, please try again later.", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Welcome " + userNameText.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                ((SpotSwap) getApplication()).setUserName(userNameText.getText().toString());
                finish(); //Destroy activity
                startActivity(mainIntent);
            }
        }
    }
}
