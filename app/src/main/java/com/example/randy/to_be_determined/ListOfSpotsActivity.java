package com.example.randy.to_be_determined;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListOfSpotsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_spots);

        LinearLayout layout = (LinearLayout) findViewById(R.id.spots);

        for (int i = 0; i < 10; i++) {
            View spotLayout = addSpot();
            layout.addView(spotLayout);
        }
    }

    public View addSpot() {
        LinearLayout spots = (LinearLayout) findViewById(R.id.spots);
        View newSpot = LayoutInflater.from(this).inflate(R.layout.spot_entry, spots, false);

        return newSpot;
    }
}
