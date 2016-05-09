package com.example.randy.to_be_determined;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ListOfSpotsActivity extends AppCompatActivity{
    /* PRIVATE VARIABLES */
    private LinearLayout layout;
    private TextView locationName;

    private class Spot
    {
        /* PRIVATE VARIABLES */
        private int id;     //ID of table entry
        private String floor, numSeats;
        private boolean natLight, plug, pc, silence, whiteboard, chair, mac;
        private LinearLayout mainHori, mainVert, subHori;
        private TextView tv;

        public Spot(int id, String floor, String seat, boolean natlight, boolean plug, boolean pc, boolean silence, boolean whiteboard, boolean chair, boolean mac)
        {
            /* Initialize variables */
            this.id = id;
            this.numSeats = seat;
            this.floor = floor;
            this.natLight = natlight;
            this.plug = plug;
            this.pc = pc;
            this.silence = silence;
            this.whiteboard = whiteboard;
            this.chair = chair;
            this.mac = mac;
            mainHori = new LinearLayout(getApplicationContext());
            mainVert = new LinearLayout(getApplicationContext());
            subHori = new LinearLayout(getApplicationContext());

            /* Set up orientation of the linear views */
            mainHori.setOrientation(LinearLayout.HORIZONTAL);
            mainVert.setOrientation(LinearLayout.VERTICAL);
            subHori.setOrientation(LinearLayout.HORIZONTAL);

            /* Set on click listener */
            mainHori.setClickable(true);
            mainHori.setOnClickListener(viewClick);


            /* Set up text view */
            tv = new TextView(getApplicationContext());
            tv.setText("FLOOR: " + this.floor + "\nSEATS AVAILABLE: " + numSeats);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(20);
            CustomFont.setCustomFont("VitaStd-Regular.ttf", tv, getAssets());

            /* Add views in the right order */
            mainHori.addView(createImageView(R.mipmap.closebk48dp));
            mainHori.addView(mainVert);
            mainVert.addView(tv);
            mainVert.addView(subHori);

            /* Set up image views for the different icons */
            if(natLight)
                subHori.addView(createImageView(R.mipmap.natural_light));
            if(this.plug)
                subHori.addView(createImageView(R.mipmap.plug));
            if(this.pc)
                subHori.addView(createImageView(R.mipmap.pc));
            if(this.silence)
                subHori.addView(createImageView(R.mipmap.silence));
            if(this.whiteboard)
                subHori.addView(createImageView(R.mipmap.whiteboard));
            if(this.chair)
                subHori.addView(createImageView(R.mipmap.chair));
            if(this.mac)
                subHori.addView(createImageView(R.mipmap.mac));
        }

        private ImageView createImageView(int resId)
        {
            ImageView v = new ImageView(getApplicationContext());
            v.setImageResource(resId);
            return v;
        }

        public View getSpot()
        {
            return mainHori;
        }

        private View.OnClickListener viewClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Replace with code to move the user to reserve intent */
                Toast.makeText(getApplicationContext(), "Clicked! " + id, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_spots);

        layout = (LinearLayout) findViewById(R.id.spots);

        locationName = (TextView) findViewById(R.id.textView8);

        String s = "EMPTY";

        if(!getIntent().getStringExtra(SearchWithMapActivity.EXTRA_MESSAGE).isEmpty())
            s = getIntent().getStringExtra(SearchWithMapActivity.EXTRA_MESSAGE);
        else if(!getIntent().getStringExtra(SearchBasicActivity.EXTRA_MESSAGE).isEmpty())
            s = getIntent().getStringExtra(SearchBasicActivity.EXTRA_MESSAGE);

        locationName.setText(s.toUpperCase());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", locationName, getAssets());
        locationName.setGravity(Gravity.CENTER_HORIZONTAL);

        new GetSpots().execute(((SpotSwap) getApplication()).getUserName(), s);
    }

    public class GetSpots extends AsyncTask<String, Void, String> {
        ProgressDialog loading;
        ArrayList<String> strs = new ArrayList<>();

        protected String doInBackground(String... user_creds)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                url = new URL("http://mpss.csce.uark.edu/~palande1/fetch_post.php?username=" + user_creds[0] + "&location=" + URLEncoder.encode(user_creds[1], "UTF-8"));
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine(); //Contains number of rows

                String line;
                while((line = responseStreamReader.readLine()) != null) {
                    strs.add(line);
                }


                urlConnection.disconnect();
            } catch(IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPreExecute()
        {
            loading = ProgressDialog.show(ListOfSpotsActivity.this, "Retrieving Posts", "Retrieving posts...", true);
        }

        protected void onPostExecute(String result)
        {
            String[] post = new String[13];
            int id;     //ID of table entry
            String floor, numSeats;

            for(int i = 0; i < strs.size(); i++)
            {
                String line = strs.get(i);
                boolean natLight = false, plug = false, pc = false, silence = false, whiteboard = false, chair = false, mac = false;

                int j = 0;
                for(String retVal: line.split("~"))
                {
                    post[j] = retVal;
                    j++;
                }

                id = Integer.valueOf(post[0]);
                floor = post[3];
                numSeats = post[4];
                if(post[6].contains("TRUE"))
                    natLight = true;
                if(post[7].contains("TRUE"))
                    plug = true;
                if(post[8].contains("TRUE"))
                    pc = true;
                if(post[9].contains("TRUE"))
                    whiteboard = true;
                if(post[10].contains("TRUE"))
                    mac = true;
                if(post[11].contains("TRUE"))
                    chair = true;
                if(post[12].contains("TRUE"))
                    silence = true;

                layout.addView(new Spot(id, floor, numSeats, natLight, plug, pc, silence, whiteboard, chair, mac).getSpot());
            }

            loading.dismiss();
        }
    }
}
