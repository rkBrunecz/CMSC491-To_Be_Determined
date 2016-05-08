package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListOfSpotsActivity extends AppCompatActivity {
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_spots);

        LinearLayout layout = (LinearLayout) findViewById(R.id.spots);

        // This needs to iterate for the number of rows in the table
        for (int i = 0; i < 10; i++) {
            View spotLayout = addSpot(2, 5, 0, 0);
            layout.addView(spotLayout);
        }
    }

    public View addSpot(int floor, int seats, int naturallight, int plug) {
        LinearLayout spots = (LinearLayout) findViewById(R.id.spots);

        // Set visibility of icons from spot_entry.xml template
        // This does not work currently
        if (naturallight == 0) {
            spots.removeView(findViewById(R.id.textView5));
        }
        if (plug == 0) {
            spots.removeView(findViewById(R.id.imageView6));
        }

        View newSpot = LayoutInflater.from(this).inflate(R.layout.spot_entry, spots, false);

        return newSpot;
    }
}
