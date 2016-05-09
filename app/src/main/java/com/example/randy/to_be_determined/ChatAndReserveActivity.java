package com.example.randy.to_be_determined;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChatAndReserveActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_SMS_SENT = "com.techblogon.android.apis.os.SMS_SENT_ACTION";
    public static String recv;
    private static final int   SENT     = 1;
    private static String phoneNumberSend="+18043005195";
    public static TextView text1,text2;
    EditText chat;
    Button send, reserve;
    int seconds , minutes;

    public String formatTime(long millis) {
        String output = "00:00";
        long seconds = millis / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String sec = String.valueOf(seconds);
        String min = String.valueOf(minutes);

        if (seconds < 10)
            sec = "0" + seconds;
        if (minutes < 10)
            min= "0" + minutes;

        output = min + " : " + sec;
        return output;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_and_reserve);

        text1=(TextView)findViewById(R.id.textView);
        text2=(TextView) findViewById(R.id.textView2);
        text2.setMovementMethod(new ScrollingMovementMethod());

        chat = (EditText) findViewById(R.id.editText);
        send=(Button) findViewById(R.id.button);
        reserve =(Button) findViewById(R.id.buttonReserve);
        reserve.setOnClickListener(this);
        send.setOnClickListener(this);

        Intent intent = getIntent();                    //receiving the received message
        String message = intent.getStringExtra(recv);
        if (message != null)
        {
            Log.e("Message newmain",message);
            String  chatTextset=message+"\n";
            text2.append("You : "+chatTextset);
            text2.setTextColor(Color.RED);

        }



        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText(" "+formatTime(millisUntilFinished));
            }



            public void onFinish() {

                text1.setText("TIME OUT!!!");
                text1.setTextColor(Color.RED);

                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(100); //Managing the time of the blink with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                text1.startAnimation(anim);
                send.setClickable(false);
                chat.setFocusable(false);
            }
        }.start();


    }

    private void sendSMS(String msg) {


        SmsManager sms = SmsManager.getDefault();
        List<String> messages = sms.divideMessage(msg);
        for (String message : messages) {
            sms.sendTextMessage(phoneNumberSend, null, message, PendingIntent.getBroadcast(
                    this, 0, new Intent(ACTION_SMS_SENT), 0), null);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button : String chatTextget=chat.getText().toString();

                String  chatTextset=chatTextget+"\n";
                text2.append("Me : "+chatTextset);
                text2.setTextColor(Color.BLUE);
                chat.setText("");
                sendSMS(chatTextget);
                break;
            case R.id.buttonReserve :
                Toast.makeText(ChatAndReserveActivity.this, "Reserved Spot", Toast.LENGTH_SHORT).show();
                break;


        }

    }

    public void receiveText(String msg){
        Log.e("Messsage received: ", msg);
        String chatTextset = msg + "\n";
        text2.append("You: " + chatTextset);
        text2.setTextColor(Color.RED);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
