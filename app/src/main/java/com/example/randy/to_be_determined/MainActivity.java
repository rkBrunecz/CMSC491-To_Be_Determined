package com.example.randy.to_be_determined;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /* PRIVATE VARIABLES */
    private Button postBtn, searchBtn, logOutBtn;
    private TextView userName;

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
        logOutBtn = (Button)findViewById(R.id.logOutButton);
        userName = (TextView)findViewById(R.id.userNameMain);

        /* Set up custom font */
        CustomFont.setCustomFont("VitaCondensedStd-Bold.ttf", (TextView)findViewById(R.id.spotSwapMain), getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", postBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", searchBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Bold.ttf", logOutBtn, getAssets());
        CustomFont.setCustomFont("VitaStd-Regular.ttf", userName, getAssets());

        userName.setText(((SpotSwap)getApplication()).getUserName());

        postBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        logOutBtn.setOnClickListener(this);
        userName.setOnClickListener(this);
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

            case R.id.logOutButton:
                Intent loginActivity = new Intent(this, LogInActivity.class);
                ((SpotSwap)getApplication()).setUserName("");
                finish();
                startActivity(loginActivity);
                break;

            case R.id.userNameMain:
                break;
        }
    }
}
