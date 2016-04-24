package com.example.randy.to_be_determined;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    Button photoButton;
    Spinner floor;
    Bitmap photo;
    Spinner location;
    EditText description;
    CheckBox charger,window;
    Button post;
    String loc[] = { "RLC", "Quite Floor", "Very Quite Floor", "General sitting"};
    String flr[] = {"0th level","1st floor","2nd floor","3rd floor"};
    ArrayAdapter<String> locarr,flrarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Post</font>"));

        imageView = (ImageView)this.findViewById(R.id.imageView);
        description=(EditText)this.findViewById(R.id.editText);
        photoButton = (Button) this.findViewById(R.id.button);
        location = (Spinner) findViewById(R.id.spinner);
        floor = (Spinner) findViewById(R.id.spinner2);
        charger = (CheckBox) findViewById(R.id.checkBox);
        window = (CheckBox) findViewById(R.id.checkBox2);
        post = (Button) findViewById(R.id.button2);

        locarr = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, loc);

        flrarr = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, flr);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", (TextView) findViewById(R.id.textView6), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.textView), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.textView2), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.textView3), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", (TextView) findViewById(R.id.textView5), getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", charger, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Regular.otf", window, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Light.otf", description, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", photoButton, getAssets());
        CustomFont.setCustomFont("VitaCondensedStd-Bold.otf", post, getAssets());

        locarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flrarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(locarr);
        floor.setAdapter(flrarr);
        post.setOnClickListener(this);
        photoButton.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            photoButton.setVisibility(View.INVISIBLE);
            photoButton.setClickable(false);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button :
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.button2 :
                String chrgr=null,wndw=null;
                String f = floor.getSelectedItem().toString();
                String l = location.getSelectedItem().toString();
                String desc = description.getText().toString();
                String disp=null;
                if(charger.isChecked())
                { chrgr=charger.getText().toString();}
                if(window.isChecked())
                {  wndw=window.getText().toString();}
                if(charger!=null && window!=null)
                    disp = f+" "+l+" "+desc+" "+chrgr+" "+wndw;
                else if(charger!=null && window==null)
                    disp = f+" "+l+" "+desc+" "+chrgr;
                else if(window!=null && charger==null)
                    disp = f+" "+l+" "+desc+" "+wndw;
                else
                    disp = f+" "+l+" "+desc;

                Intent i = new Intent(this, DisplayActivity.class);
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("stuff", disp);
                i.putExtra("bmp",photo);
                //Add the bundle to the intent
                i.putExtras(bundle);

                //Fire that second activity
                startActivity(i);

                //Toast.makeText(getApplicationContext(), disp,
                //Toast.LENGTH_LONG).show();
                break;

            /*case R.id.btplus:
                Toast.makeText(getApplicationContext(), "Plus is clicked" + "+", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btminus:
                Toast.makeText(getApplicationContext(),"Minus is clicked" + "-", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;*/
        }
    }
}
