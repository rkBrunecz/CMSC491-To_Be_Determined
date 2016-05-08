package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 * SearchBasicActivity
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class SearchBasicActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE CONSTANTS */
    private final int DELAY = 2500;
    public final static String EXTRA_MESSAGE = "com.example.randy.to_be_determined.MESSAGE";

    /* PRIVATE VARIABLES */
    private Button searchMapBtn;
    private Handler handler = new Handler();
    private BuildingButton buttons [] = new BuildingButton[9];
    //private Intent selectSpotActivity = new Intent(this, ListOfSpots.class);

    private class BuildingButton {
        private Button b;
        private TextView t;
        private String name;

        public BuildingButton(Button b, TextView t, String name)
        {
            this.b = b;
            this.name = name;
            this.t = t;

            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    /*
                    selectSpotActivity.putExtra(EXTRA_MESSAGE, name);
                    startActivity(selectSpotActivity);*/
                }
            });
        }
    }

    private class UpdateButtonTxt implements Runnable{
        public UpdateButtonTxt()
        {

        }

        @Override
        public void run() {
            new FindNumSpots().execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_basic);

        searchMapBtn = (Button) findViewById(R.id.searchMapBtn);
        buttons[0] = new BuildingButton((Button)findViewById(R.id.libraryBtn), (TextView) findViewById(R.id.libraryTxt), "AOK Library");
        buttons[1] = new BuildingButton((Button)findViewById(R.id.uniCenBtn), (TextView) findViewById(R.id.uniCenTxt), "University Center");
        buttons[2] = new BuildingButton((Button)findViewById(R.id.mathBtn), (TextView) findViewById(R.id.mathTxt), "Math and Psychology");
        buttons[3] = new BuildingButton((Button)findViewById(R.id.bioBtn), (TextView) findViewById(R.id.bioTxt), "Biological Science");
        buttons[4] = new BuildingButton((Button)findViewById(R.id.shermanBtn), (TextView) findViewById(R.id.shermanTxt), "Sherman Hall");
        buttons[5] = new BuildingButton((Button)findViewById(R.id.fineBtn), (TextView) findViewById(R.id.fineTxt), "Fine Arts");
        buttons[6] = new BuildingButton((Button)findViewById(R.id.engiBtn), (TextView) findViewById(R.id.engiTxt), "Engineering");
        buttons[7] = new BuildingButton((Button)findViewById(R.id.infoBtn), (TextView) findViewById(R.id.infoTxt), "Information Technology");
        buttons[8] = new BuildingButton((Button)findViewById(R.id.performingBtn), (TextView) findViewById(R.id.performingTxt), "Performing Arts and Humanities");

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.availableSpots), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", searchMapBtn, getAssets());
        for(int i = 0; i < 9; i++)
        {
            CustomFont.setCustomFont("VitaStd-Bold.ttf", buttons[i].b, getAssets());
            CustomFont.setCustomFont("VitaStd-Regular.ttf", buttons[i].t, getAssets());
        }


        searchMapBtn.setOnClickListener(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        handler.removeCallbacks(null);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        handler.postDelayed(new UpdateButtonTxt(), 0);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case (R.id.searchMapBtn):
                Intent intent = new Intent(this, SearchWithMapActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public class FindNumSpots extends AsyncTask<Void, Void, String> {
        String [] spots = new String[9];

        protected String doInBackground(Void... v)
        {
            /* LOCAL VARIABLES */
            URL url;
            String s = "";

            try {
                for(int i = 0; i < 9; i++)
                {
                    url = new URL("http://mpss.csce.uark.edu/~palande1/fetch_post_buildings.php?username=" + ((SpotSwap) getApplication()).getUserName() + "&location=" + URLEncoder.encode(buttons[i].name, "UTF-8"));
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                    spots[i] = responseStreamReader.readLine();
                    Log.i("Response", s);

                    urlConnection.disconnect();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPostExecute(String result) {
            for(int i = 0; i < 9; i++)
                buttons[i].b.setText(String.valueOf(Integer.valueOf(spots[i]))); //Convert to integer to remove spaces from strings

            handler.postDelayed(new UpdateButtonTxt(), DELAY);
        }
    }

}
