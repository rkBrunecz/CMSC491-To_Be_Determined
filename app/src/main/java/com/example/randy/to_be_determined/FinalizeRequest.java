package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FinalizeRequest extends AppCompatActivity {

    Integer id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_request);
        Intent intent = getIntent();                    //receiving the received message
        //String message = intent.getStringExtra(recv);
        id= intent.getIntExtra(ListOfSpotsActivity.ID_MESSAGE,0);
        new GetNum().execute(id);

    }

    public class GetNum extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {


            String s = "";
            URL url;
            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/after_reserve.php?id=" + params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine();
                Log.i("Response for phonenum", s);
                //phoneNumberSend = "+1" + s;
                urlConnection.disconnect();
            } catch (Exception e) {

                e.printStackTrace();

            }


            return 0;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Intent navigateIntent = new Intent(FinalizeRequest.this,MainActivity.class);
            FinalizeRequest.this.startActivity(navigateIntent);
        }
    }
}
