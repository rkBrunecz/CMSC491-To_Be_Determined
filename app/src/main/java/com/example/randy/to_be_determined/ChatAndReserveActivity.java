package com.example.randy.to_be_determined;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * ChatAndReserveActivity
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This activity allows a user to chat with other user who posted the spot and if he is satisfied with the spot specifications
 * then he can reserve it by clicking the reserve button.
 *
 * References:
 * http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class ChatAndReserveActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_SMS_SENT = "com.techblogon.android.apis.os.SMS_SENT_ACTION";
    public static String recv;
    private static final int   SENT     = 1;
    public String phoneNumberSend="";
    public static TextView text1,text2;
    private boolean reservedClicked= false;
    private Integer id=0;
    EditText chat;
    Button send, reserve;
    int seconds , minutes;
    MsgSenderNum sendNum=new MsgSenderNum();

    //for the timer feature

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
        send=(Button) findViewById(R.id.buttonSend);
        reserve =(Button) findViewById(R.id.buttonReserve);
        reserve.setOnClickListener(ChatAndReserveActivity.this);
        send.setOnClickListener(ChatAndReserveActivity.this);

        Intent intent = getIntent();                    //receiving the received message
        String message = intent.getStringExtra(recv);
        id= intent.getIntExtra(ListOfSpotsActivity.ID_MESSAGE,0);
        new GetNum().execute(id);

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
                    ChatAndReserveActivity.this, 0, new Intent(ACTION_SMS_SENT), 0), null);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.buttonSend : String chatTextget=chat.getText().toString();//sending the message to the person

                String  chatTextset=chatTextget+"\n";
                text2.append("Me : "+chatTextset);
                text2.setTextColor(Color.BLUE);
                chat.setText("");
                sendSMS(chatTextget);
                break;
            case R.id.buttonReserve :               //for reserving the spot
                Intent finalizeIntent = new Intent(ChatAndReserveActivity.this,FinalizeRequest.class);
                finalizeIntent.putExtra(ListOfSpotsActivity.ID_MESSAGE,id);
                ChatAndReserveActivity.this.startActivity(finalizeIntent);
                Toast.makeText(ChatAndReserveActivity.this, "Finalizing Spot", Toast.LENGTH_SHORT).show();
                break;


        }

    }


    //for receiving the message

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

    //for getting the phone no. of the person who posted the post in app in order to communicate with him

    public class GetNum extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {

            if(!reservedClicked){

                String s="";
                URL url;
                try{
                    url=new URL("http://mpss.csce.uark.edu/~palande1/chat_num_query.php?id="+params[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(in));

                    s = responseStreamReader.readLine();
                    Log.i("Response for phonenum", s);
                    phoneNumberSend="+1"+s;
                    sendNum.setPhoneNum(s);
                    urlConnection.disconnect();
                }catch (Exception e){

                    e.printStackTrace();

                }

            } else {

            }
            return 0;
        }
    }
}
