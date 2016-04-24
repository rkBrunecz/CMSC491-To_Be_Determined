package com.example.randy.to_be_determined;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
        TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

       //Extract the data…
        String stuff = bundle.getString("stuff");
        txt= (TextView)findViewById(R.id.textView4);
        txt.setText(stuff);

        Bitmap mBitmap = (Bitmap) getIntent().getParcelableExtra("bmp");
        ImageView iv=(ImageView)findViewById(R.id.imageView2);
        iv.setImageBitmap(mBitmap);
    }

}
