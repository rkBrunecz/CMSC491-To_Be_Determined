package com.example.randy.to_be_determined;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText eT;//USED FOR DEBUGGING

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* DEBUG */
        eT = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);
        eT.setText(message);
        eT.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        eT.setEnabled(false);
        /* END DEBUG CODE */
    }
}
