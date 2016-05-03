package com.example.randy.to_be_determined;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private Button postBtn, searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set up properties of the tool bar */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

         /* Set up the buttons */
        postBtn = (Button)findViewById(R.id.postSpotBtn);
        searchBtn = (Button)findViewById(R.id.findSpotBtn);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView)findViewById(R.id.spotSwapMain), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", postBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", searchBtn, getAssets());

        postBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater toolbarMenu = getMenuInflater();
        toolbarMenu.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.postSpotBtn:
                Intent postIntent = new Intent(this, PostActivity.class); //Create an intent to launch the create account activity
                startActivity(postIntent);
                break;

            case R.id.findSpotBtn:
                Intent searchIntent = new Intent(this, SearchWithMapActivity.class);
                startActivity(searchIntent);
                break;
        }
    }
}
