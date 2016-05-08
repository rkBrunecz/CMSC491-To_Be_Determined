package com.example.randy.to_be_determined;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * Created by Randy on 5/7/2016.
 * ForgotPasswordActivity
 *
 * Unfortunately, the methods for getting a password for a user is very unsafe and easily manipulated to steal information from other users.
 * Should be okay for demoing purposes.
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    /* PRIVATE VARIABLES */
    private Button getPassBtn;
    private EditText editUser, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getPassBtn = (Button) findViewById(R.id.getPassBtn);
        editUser = (EditText) findViewById(R.id.editUser);
        editEmail = (EditText) findViewById(R.id.editEmail);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.spotSwapForgot), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.plainUsername), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.plainEmail), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", getPassBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", editUser, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", editEmail, getAssets());

        getPassBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.getPassBtn:
                Boolean fetchPassword = true;
                if(editUser.getText().toString().length() == 0)
                {
                    editUser.setHintTextColor(Color.RED);

                    Toast.makeText(getApplicationContext(), "No Username was provided!", Toast.LENGTH_SHORT).show();
                    fetchPassword = false;
                }

                if(editEmail.getText().toString().length() == 0)
                {
                    editEmail.setHintTextColor(Color.RED);

                    Toast.makeText(getApplicationContext(), "No Email was provided!", Toast.LENGTH_SHORT).show();
                    fetchPassword = false;
                }

                if(fetchPassword)
                    new GetPassword().execute(editEmail.getText().toString(), editUser.getText().toString());


                break;
        }
    }

    public class GetPassword extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        protected String doInBackground(String... user_creds)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/fetch_user_for_pass.php?email=" + user_creds[0] + "&username=" + user_creds[1]);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine();
                Log.i("Response", s);

                urlConnection.disconnect();
            } catch(IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPreExecute()
        {
            loading = ProgressDialog.show(ForgotPasswordActivity.this, "Checking credentials", "Checking credentials...", true);
        }

        protected void onPostExecute(String result)
        {
            loading.dismiss();

            if(result.contentEquals("0"))
                Toast.makeText(getApplicationContext(), "Either the username or email provided does match any or our records. Please try again!", Toast.LENGTH_LONG).show();
            else
            {

                Toast.makeText(getApplicationContext(), "Your password: " + result, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
