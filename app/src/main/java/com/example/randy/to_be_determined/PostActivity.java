package com.example.randy.to_be_determined;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
 * PostActivity
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 * http://www.checkupdown.com/status/E414.html
 * http://stackoverflow.com/questions/5379247/filenotfoundexception-while-getting-the-inputstream-object-from-httpurlconnectio
 * https://www.simplifiedcoding.net/android-upload-image-to-server-using-php-mysql/
 * http://sunil-android.blogspot.com/2013/10/insert-and-retrieve-image-into-db.html
 */
public class PostActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE CONSTANTS */
    private static final int MAX_CHARACTERS = 300;
    private static final int CAMERA_REQUEST = 1888;

    /* PRIVATE VARIABLES */
    private ImageView imageView;
    private Button photoButton;
    private Spinner floor,  location, numSeats;
    private Bitmap photo;
    private EditText description;
    private CheckBox window, outlet, pc, whiteboard, macComputer, rockingChair, quiet;
    private Button post;
    private String numberOfSeats[] = {"One", "Two", "Three", "Four", "Five", "Six", "Seven"};
    private String loc[] = { "AOK Library", "University Center", "Math and Psychology", "Biological Science",
                     "Sherman Hall", "Fine Arts", "Engineering", "Information Technology", "Performing Arts and Humanities"};
    private String flr[] = {"1st floor","2nd floor","3rd floor", "4th floor", "5th floor", "6th floor", "7th floor"};
    private ArrayAdapter<String> locarr, flrarr, seatsarr;
    private TextView descriptionHelper;
    private  byte[] img = null;

    /* Used to update the maximum number of characters in the descriptionHelper textview */
    private TextWatcher desWatcher = new TextWatcher()
    {
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        public void afterTextChanged(Editable s)
        {
            descriptionHelper.setText("Characters Left: " + (MAX_CHARACTERS - description.getText().length()));
        }
    };

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
        pc = (CheckBox) findViewById(R.id.checkBox3);
        whiteboard = (CheckBox) findViewById(R.id.checkBox4);
        macComputer = (CheckBox) findViewById(R.id.checkBox5);
        rockingChair = (CheckBox) findViewById(R.id.checkBox6);
        quiet = (CheckBox) findViewById(R.id.checkBox7);
        post = (Button) findViewById(R.id.button2);
        descriptionHelper = (TextView) findViewById(R.id.descriptionHelper);

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
        CustomFont.setCustomFont("VitaStd-Regular.ttf", descriptionHelper, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", window, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", outlet, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", pc, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", whiteboard, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", macComputer, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", rockingChair, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.ttf", quiet, getAssets());
        CustomFont.setCustomFont("VitaStd-Light.ttf", description, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", post, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", photoButton, getAssets());

        locarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flrarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatsarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(locarr);
        floor.setAdapter(flrarr);
        numSeats.setAdapter(seatsarr);

        post.setOnClickListener(this);
        photoButton.setOnClickListener(this);

        description.setMovementMethod(new ScrollingMovementMethod());
        description.addTextChangedListener(desWatcher);

        descriptionHelper.setText("Characters Left: " + MAX_CHARACTERS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setRotation(90);
            imageView.setOnClickListener(this);
            imageView.setClickable(true);

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
                String pcf = "FALSE";
                String whiteboardf = "FALSE";
                String macComputerf = "FALSE";
                String rockingChairf = "FALSE";
                String quietf = "FALSE";

                if(window.isChecked())
                    windowf = "TRUE";
                if(outlet.isChecked())
                    outletf = "TRUE";
                if(pc.isChecked())
                    pcf = "TRUE";
                if(whiteboard.isChecked())
                    whiteboardf = "TRUE";
                if(macComputer.isChecked())
                    macComputerf = "TRUE";
                if(rockingChair.isChecked())
                    rockingChairf = "TRUE";
                if(quiet.isChecked())
                    quietf = "TRUE";

                if(desc.isEmpty())
                    desc = "None";

                Toast.makeText(getApplicationContext(), "Posting...", Toast.LENGTH_SHORT).show();
                new CreatePost().execute(((SpotSwap)getApplication()).getUserName(), l, f, seats, desc, windowf, outletf, pcf, whiteboardf, macComputerf, rockingChairf, quietf);

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
            int status = -1;
            String s = "";
            String encodedImage = "null";
            URL url;

            try {
                String server = "http://mpss.csce.uark.edu/~palande1/insert_post.php?" + "username=" + URLEncoder.encode(post[0], "UTF-8") + "&location=" + URLEncoder.encode(post[1], "UTF-8") + "&floor=" + URLEncoder.encode(post[2], "UTF-8") +
                        "&numseats=" + URLEncoder.encode(post[3], "UTF-8") + "&description=" + URLEncoder.encode(post[4], "UTF-8") + "&windowseat=" + URLEncoder.encode(post[5], "UTF-8") +
                        "&poweroutlet=" + URLEncoder.encode(post[6], "UTF-8") + "&pc=" + URLEncoder.encode(post[7], "UTF-8") + "&whiteboard=" + URLEncoder.encode(post[8], "UTF-8") +
                        "&maccomputers=" + URLEncoder.encode(post[9], "UTF-8") + "&rockingchair=" + URLEncoder.encode(post[10], "UTF-8") + "&silence=" + URLEncoder.encode(post[11], "UTF-8");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                if(photo != null) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 20, bos);
                    img = bos.toByteArray();
                    encodedImage = Base64.encodeToString(img, Base64.DEFAULT);
                }

                server += "&image=" + URLEncoder.encode(encodedImage, "UTF-8");

                url = new URL(server);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                status = urlConnection.getResponseCode();
                if(status > 400) {
                    InputStream in = new BufferedInputStream(urlConnection.getErrorStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));
                    s = "Error: " + responseStreamReader.readLine();

                    Log.i("Response", s);
                }
                else
                {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));
                    s = responseStreamReader.readLine();

                    Log.i("Response", s);
                }

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
            else if(result.contains("Error"))
                Toast.makeText(getApplicationContext(), "Post failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
