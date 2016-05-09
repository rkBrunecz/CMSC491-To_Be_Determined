package com.example.randy.to_be_determined;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfSpotsActivity extends AppCompatActivity{
    LinearLayout layout;

    private class Spot
    {
        /* PRIVATE VARIABLES */
        private int id, numSeats;     //ID of table entry
        private String floor;
        private boolean natLight, plug, pc, silence, whiteboard, chair, mac;
        private LinearLayout mainHori, mainVert, subHori;
        private TextView tv;

        public Spot(int id, String floor, int seat, boolean natlight, boolean plug, boolean pc, boolean silence, boolean whiteboard, boolean chair, boolean mac)
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

        LinearLayout layout = (LinearLayout) findViewById(R.id.spots);

        // This needs to iterate for the number of rows in the table
        layout.addView(new Spot(1, "1st", 5, true, true, true, true, true, true, true).getSpot());
        layout.addView(new Spot(2, "2nd", 1, true, false, false, false, false, true, true).getSpot());
        layout.addView(new Spot(3, "4th", 5, false, true, false, true, true, true, true).getSpot());
        layout.addView(new Spot(4, "1st", 3, true, true, true, false, true, false, true).getSpot());
        layout.addView(new Spot(5, "3rd", 4, false, true, true, true, false, true, true).getSpot());
        layout.addView(new Spot(6, "1st", 5, true, true, true, true, true, true, true).getSpot());
        layout.addView(new Spot(7, "1st", 6, true, false, false, false, false, true, true).getSpot());
        layout.addView(new Spot(8, "7th", 5, false, true, false, true, true, true, true).getSpot());
        layout.addView(new Spot(9, "1st", 2, true, true, true, false, true, false, true).getSpot());
        layout.addView(new Spot(10, "1st", 5, false, true, true, true, false, true, true).getSpot());
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
