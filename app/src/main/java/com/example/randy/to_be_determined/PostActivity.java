package com.example.randy.to_be_determined;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class PostActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Button photoButton;
    private Spinner floor,  location, numSeats;
    private Bitmap photo;
    private EditText description;
    private CheckBox window, outlet, scanner, whiteboard, macComputer, rockingChair;
    private Button post;
    private String numberOfSeats[] = {"One", "Two", "Three", "Four", "Five", "Six", "Seven"};
    private String loc[] = { "AOK Library", "University Center", "Math and Psychology", "Biological Science",
                     "Sherman Hall", "Fine Arts", "Engineering", "Information Technology", "Performing Arts and Humanities"};
    private String flr[] = {"1st floor","2nd floor","3rd floor", "4th floor", "5th floor", "6th floor", "7th floor"};
    private ArrayAdapter<String> locarr, flrarr, seatsarr;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageView = (ImageView)this.findViewById(R.id.imageView);
        description=(EditText)this.findViewById(R.id.editText);
        photoButton = (Button) this.findViewById(R.id.button);
        location = (Spinner) findViewById(R.id.spinner);
        floor = (Spinner) findViewById(R.id.spinner2);
        numSeats = (Spinner) findViewById(R.id.spinner3);
        window = (CheckBox) findViewById(R.id.checkBox);
        outlet = (CheckBox) findViewById(R.id.checkBox2);
        scanner = (CheckBox) findViewById(R.id.checkBox3);
        whiteboard = (CheckBox) findViewById(R.id.checkBox4);
        macComputer = (CheckBox) findViewById(R.id.checkBox5);
        rockingChair = (CheckBox) findViewById(R.id.checkBox6);
        post = (Button) findViewById(R.id.button2);

        locarr = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, loc);

        flrarr = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, flr);

        seatsarr = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, numberOfSeats);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView) findViewById(R.id.textView6), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.textView), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.textView2), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.textView3), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.textView5), getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", (TextView) findViewById(R.id.textView7), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", window, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", outlet, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", scanner, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", whiteboard, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", macComputer, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", rockingChair, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", description, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", photoButton, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", post, getAssets());

        locarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flrarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatsarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(locarr);
        floor.setAdapter(flrarr);
        numSeats.setAdapter(seatsarr);

        post.setOnClickListener(this);
        photoButton.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setRotation(90);
            imageView.setOnClickListener(this);
            imageView.setClickable(true);

            imageUri = data.getData();

            photoButton.setVisibility(View.INVISIBLE);
        }
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED)
        {
            imageView.setVisibility(View.INVISIBLE);
            photoButton.setVisibility(View.VISIBLE);
            photo = null;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button :
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                break;
            }

            case R.id.button2 :
                String f = floor.getSelectedItem().toString();
                String l = location.getSelectedItem().toString();
                String seats = numSeats.getSelectedItem().toString();
                String desc = description.getText().toString();
                String windowf = "FALSE";
                String outletf = "FALSE";
                String scannerf = "FALSE";
                String whiteboardf = "FALSE";
                String macComputerf = "FALSE";
                String rockingChairf = "FALSE";

                if(window.isChecked())
                    windowf = "TRUE";
                if(outlet.isChecked())
                    outletf = "TRUE";
                if(scanner.isChecked())
                    scannerf = "TRUE";
                if(whiteboard.isChecked())
                    whiteboardf = "TRUE";
                if(macComputer.isChecked())
                    macComputerf = "TRUE";
                if(rockingChair.isChecked())
                    rockingChairf = "TRUE";

                if(desc.isEmpty())
                    desc = "None";

                Toast.makeText(getApplicationContext(), "Posting...", Toast.LENGTH_SHORT).show();
                new CreatePost().execute(((SpotSwap)getApplication()).getUserName(), l, f, seats, desc, windowf, outletf, scannerf, whiteboardf, macComputerf, rockingChairf);

                finish();

                break;

            case R.id.imageView :
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                break;
            }
        }
    }

    public class CreatePost extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... post)
        {
            /* LOCAL VARIABLES */
            String s = "";
            URL url;

            try {
                String params = "username=" + URLEncoder.encode(post[0], "UTF-8") + "&location=" + URLEncoder.encode(post[1], "UTF-8") + "&floor=" + URLEncoder.encode(post[2], "UTF-8") +
                        "&numseats=" + URLEncoder.encode(post[3], "UTF-8") + "&description=" + URLEncoder.encode(post[4], "UTF-8") + "&windowseat=" + URLEncoder.encode(post[5], "UTF-8") +
                        "&poweroutlet=" + URLEncoder.encode(post[6], "UTF-8") + "&scanner=" + URLEncoder.encode(post[7], "UTF-8") + "&whiteboard=" + URLEncoder.encode(post[8], "UTF-8") +
                        "&maccomputers=" + URLEncoder.encode(post[9], "UTF-8") + "&rockingchair=" + URLEncoder.encode(post[10], "UTF-8");

                url = new URL("http://mpss.csce.uark.edu/~palande1/insert_post.php?" + params + "&image=" + photo);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                s = responseStreamReader.readLine();
                Log.i("Response", url.toString() + "\n" + s);

                urlConnection.disconnect();
            } catch(IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        protected void onPostExecute(String result)
        {
            if(result.contains("Success"))
                Toast.makeText(getApplicationContext(), "Posted successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Post failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
