package com.example.randy.to_be_determined;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    /* CONSTANTS */
    private static final int NUM_FEATURES = 6;

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
    private String loc[] = { "Library", "University Center", "Math and Psychology", "Biological Science",
                     "Sherman Hall", "Fine Arts", "Engineering", "Information Technology", "Performing Arts and Humanities"};
    private String flr[] = {"1st floor","2nd floor","3rd floor", "4th floor", "5th floor", "6th floor", "7th floor"};
    private ArrayAdapter<String> locarr, flrarr, seatsarr;
    private DatabaseHelper dbhelper;
    private SQLiteDatabase db;
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

        dbhelper = new DatabaseHelper(getApplicationContext());
        db = dbhelper.getWritableDatabase();
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
                short features[] =  new short[NUM_FEATURES]; // 1 for true, 0 otherwise

                if(window.isChecked())
                    features[0] = 1;
                if(outlet.isChecked())
                    features[1] = 1;
                if(scanner.isChecked())
                    features[2] = 1;
                if(whiteboard.isChecked())
                    features[3] = 1;
                if(macComputer.isChecked())
                    features[4] = 1;
                if(rockingChair.isChecked())
                    features[5] = 1;

                if(desc.isEmpty())
                    desc = "None";

                if(createPost(l, f, seats, desc, features) < 0)
                    Toast.makeText(getApplicationContext(), "An error occurred. Please try again later.", Toast.LENGTH_LONG);
                else
                    Toast.makeText(getApplicationContext(), "Posting...", Toast.LENGTH_SHORT).show();

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


    public long createPost(String location, String floor, String seats, String desc, short features[])
    {
        ContentValues values = createContentValues(((SpotSwap)getApplication()).getUserName() , location, floor, seats, desc, features);
        return db.insert("posts", null,values);
    }

    private ContentValues createContentValues(String userName, String location, String floor, String seats, String desc, short features[])
    {
        ContentValues values = new ContentValues();

        values.put("username", userName);
        values.put("location", location);
        values.put("floor", floor);
        values.put("numseats", seats);
        values.put("description", desc);
        values.put("windowseat", features[0]);
        values.put("poweroutlet", features[1]);
        values.put("scanner", features[2]);
        values.put("whiteboard", features[3]);
        values.put("maccomputers", features[4]);
        values.put("rockingchair", features[5]);

        if(imageUri != null)
            values.put("imguri", imageUri.toString());
        else
            values.put("imguri", "None");

        //Reserved column can be either yes or no. All new posts start with no in this column
        values.put("reserved", "No");

        return values;
    }
}
