package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListOfSpotsActivity extends AppCompatActivity {
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_spots);

        LinearLayout layout = (LinearLayout) findViewById(R.id.spots);

        // This needs to iterate for the number of rows in the table
        for (int i = 0; i < 10; i++) {
            View spotLayout = addSpot(2, 5, 0, 0, 1, 1, 1, 0, 1);
            layout.addView(spotLayout);
        }
    }

    public View addSpot(int floor, int seat, int natlight, int plug, int pc, int silence, int whiteboard, int chair, int mac) {
        setContentView(R.layout.spot_entry);
        LinearLayout spots = (LinearLayout) findViewById(R.id.spot_listing);

        // Set text to proper number of seats and floor
        TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setText("FLOOR: " + String.valueOf(floor) + "\nSEATS AVAILABLE: " + String.valueOf(seat));

        // Set visibility of icons based on features available
        if (natlight == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView5);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView5);
            img.setVisibility(View.VISIBLE);
        }
        if (plug == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView6);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView6);
            img.setVisibility(View.VISIBLE);
        }
        if (pc == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView7);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView7);
            img.setVisibility(View.VISIBLE);
        }
        if (silence == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView8);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView8);
            img.setVisibility(View.VISIBLE);
        }
        if (whiteboard == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView9);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView9);
            img.setVisibility(View.VISIBLE);
        }
        if (chair == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView4);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView4);
            img.setVisibility(View.VISIBLE);
        }
        if (mac == 0) {
            ImageView img = (ImageView) findViewById(R.id.imageView3);
            img.setVisibility(View.INVISIBLE);
        }
        else{
            ImageView img = (ImageView) findViewById(R.id.imageView3);
            img.setVisibility(View.VISIBLE);
        }

        View newSpot = LayoutInflater.from(this).inflate(R.layout.spot_entry, spots, false);

        return newSpot;
    }
}
