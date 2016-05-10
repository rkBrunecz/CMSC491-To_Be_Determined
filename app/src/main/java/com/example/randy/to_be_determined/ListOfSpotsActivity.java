package com.example.randy.to_be_determined;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

/*
 * ListOfSpotsActivity
 *
 * References:
 * http://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
 * http://stackoverflow.com/questions/9685658/add-padding-on-view-programmatically
 * http://stackoverflow.com/questions/16552811/set-a-margin-between-two-buttons-programmatically-from-a-linearlayout
 * http://www.androidhive.info/2015/05/android-swipe-down-to-refresh-listview-tutorial/
 */
public class ListOfSpotsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    /* PRIVATE CONSTANTS */
    private final int MAX_IMG_HEIGHT = 100;
    private final int MAX_IMG_WIDTH = 100;
    public final static String ID_MESSAGE="com.example.randy.to_be_determined.ID_MSG";

    /* PRIVATE VARIABLES */
    private LinearLayout layout;
    private SwipeRefreshLayout refreshLayout;
    private TextView locationName;
    private String locationNameStr;

    private class Spot
    {
        /* PRIVATE VARIABLES */
        private int id;     //ID of table entry
        private String floor, numSeats;
        private boolean natLight, plug, pc, silence, whiteboard, chair, mac;
        private LinearLayout mainHori, mainVert, subHori;
        private TextView tv;

        public Spot(int id, String floor, String seat, boolean natlight, boolean plug, boolean pc, boolean silence, boolean whiteboard, boolean chair, boolean mac, String image)
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

            /* Set additional parameters for the main container */
            mainHori.setBackgroundResource(R.drawable.border);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 4, 0, 4);

            /* Set borders around components */
            mainHori.setLayoutParams(params);

            /* Set up text view */
            tv = new TextView(getApplicationContext());
            tv.setText("FLOOR: " + this.floor + "\n# OF SEATS: " + numSeats);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(20);
            setPadding(5, 0 ,0, 0, tv);
            CustomFont.setCustomFont("VitaStd-Regular.ttf", tv, getAssets());

            Log.i("Image", image);
            /* Add views in the right order */
            if(image.contains("null"))
                mainHori.addView(createImageView(R.mipmap.closebk48dp));
            else
            {
                /* Decode image and set up the image view */
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                ImageView img = new ImageView(getApplicationContext());

                float density = getApplicationContext().getResources().getDisplayMetrics().density;

                img.setLayoutParams(new ViewGroup.LayoutParams((int)(MAX_IMG_WIDTH * density), (int)(MAX_IMG_HEIGHT * density)));
                img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                img.setBackgroundResource(R.drawable.border);

                img.setImageBitmap(decodedImage);

                //Rotate image if it is on its side
                if(decodedImage.getWidth() > decodedImage.getHeight())
                    img.setRotation(90);

                setPadding(10, 10, 10, 10, img);

                mainHori.addView(img);
            }

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

            if(resId == R.mipmap.closebk48dp)
            {
                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                v.setLayoutParams(new ViewGroup.LayoutParams((int)(MAX_IMG_WIDTH * density), (int)(MAX_IMG_HEIGHT * density)));
                v.setScaleType(ImageView.ScaleType.CENTER);
                v.setBackgroundResource(R.drawable.border);
            }

            return v;
        }

        public View getSpot()
        {
            return mainHori;
        }

        private View.OnClickListener viewClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Replace with code to move the user to reserve activity */
                Intent chatIntent = new Intent(ListOfSpotsActivity.this,ChatAndReserveActivity.class);
                chatIntent.putExtra(ID_MESSAGE,id);
                ListOfSpotsActivity.this.startActivity(chatIntent);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_spots);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        layout = (LinearLayout) findViewById(R.id.spots);
        setPadding(10, 10, 10, 10, layout);

        if(!getIntent().getStringExtra(SearchWithMapActivity.EXTRA_MESSAGE).isEmpty())
            locationNameStr = getIntent().getStringExtra(SearchWithMapActivity.EXTRA_MESSAGE);
        else if(!getIntent().getStringExtra(SearchBasicActivity.EXTRA_MESSAGE).isEmpty())
            locationNameStr = getIntent().getStringExtra(SearchBasicActivity.EXTRA_MESSAGE);

        /* Set up location name text view */
        locationName = (TextView) new TextView(getApplicationContext());
        locationName.setText(locationNameStr.toUpperCase());
        locationName.setGravity(Gravity.CENTER_HORIZONTAL);
        locationName.setTextSize(40);
        locationName.setTextColor(Color.BLACK);

        CustomFont.setCustomFont("VitaStd-Regular.ttf", locationName, getAssets());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 20, 0, 20);
        locationName.setLayoutParams(params);

        layout.addView(locationName);

        //Set up refresh listener
        refreshLayout.setOnRefreshListener(this);

        new GetSpots().execute(((SpotSwap) getApplication()).getUserName(), locationNameStr);
    }

    /*
     * Sets the padding for elements with respect to the devices dimensions
     */
    private void setPadding(int left, int top, int right, int bottom, View v)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        v.setPadding((int) (left * density),
                (int) (top * density),
                (int) (right * density),
                (int) (bottom * density));
    }

    public void onRefresh()
    {
        //Clear layout
        layout.removeAllViews();

        /* Set up location name text view */
        locationName.setText(locationNameStr.toUpperCase());
        locationName.setGravity(Gravity.CENTER_HORIZONTAL);
        locationName.setTextSize(40);
        locationName.setTextColor(Color.BLACK);

        CustomFont.setCustomFont("VitaStd-Regular.ttf", locationName, getAssets());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 20, 0, 20);
        locationName.setLayoutParams(params);

        layout.addView(locationName);

        //Update layout
        new GetSpots().execute(((SpotSwap) getApplication()).getUserName(), locationName.getText().toString());
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
                    String id = line.substring(0, line.indexOf("~"));

                    url = new URL("http://mpss.csce.uark.edu/~palande1/fetch_image_id.php?id=" + id);
                    urlConnection = (HttpURLConnection)url.openConnection();
                    in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    line = line + "~";

                    String image;
                    while((image = reader.readLine()) != null)
                        line += image;

                    Log.i("Image", line);
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
            String[] post = new String[14];
            int id;     //ID of table entry
            String floor, numSeats, image;

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

                image = post[13];

                layout.addView(new Spot(id, floor, numSeats, natLight, plug, pc, silence, whiteboard, chair, mac, image).getSpot());
            }

            refreshLayout.setRefreshing(false);
            loading.dismiss();
        }
    }
}
