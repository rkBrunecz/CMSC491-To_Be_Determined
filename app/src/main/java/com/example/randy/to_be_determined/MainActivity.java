package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * MainActivity
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This activity simply allows a user to navigate between searching for a study spot on campus,
 * or posting one. Additionally, this page is where the user can log out when they are finished
 * with the application.
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private Button postBtn, searchBtn, logOutBtn;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set up properties of the tool bar */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

         /* Set up the buttons */
        postBtn = (Button)findViewById(R.id.postSpotBtn);
        searchBtn = (Button)findViewById(R.id.findSpotBtn);
        logOutBtn = (Button)findViewById(R.id.logOutButton);
        userName = (TextView)findViewById(R.id.userNameMain);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView)findViewById(R.id.spotSwapMain), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", postBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", searchBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", logOutBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", userName, getAssets());

        userName.setText(((SpotSwap) getApplication()).getUserName());

        postBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        logOutBtn.setOnClickListener(this);
        userName.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.postSpotBtn:
                Intent postIntent = new Intent(this, PostActivity.class); //Create an intent to launch the create account activity
                startActivity(postIntent);
                break;

            case R.id.findSpotBtn:
                Intent searchIntent = new Intent(this, SearchBasicActivity.class);
                startActivity(searchIntent);
                break;

            case R.id.logOutButton:
                new LogUserOut().execute(userName.getText().toString());

                Intent loginActivity = new Intent(this, LogInActivity.class);
                finish();
                startActivity(loginActivity);
                break;

            case R.id.userNameMain:
                break;
        }
    }

    public class LogUserOut extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... user_creds) {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/log_user_out.php?username=" + user_creds[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Goodbye, " + ((SpotSwap)getApplication()).getUserName() + "!", Toast.LENGTH_SHORT).show();
            ((SpotSwap)getApplication()).setUserName("");
        }
    }
}
