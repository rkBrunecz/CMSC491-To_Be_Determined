package com.example.randy.to_be_determined;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * CreateAcountActivity
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This class is used to receive messages from broadcast receiver and also checks whether the message received, came from the
 * user who posted the spot or not.
 * If the message is from the user who posted the spot then this message will be sent to the chat page of the person who is
 * currently reserving the spot.
 *
 *
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        SmsMessage msg = null;
        String strMessage = "";
        String currentPhone="";
        String compareNum = "";

        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            for (int i = 0 ; i<pdus.length;i++){
                msg = SmsMessage.createFromPdu((byte[])pdus[i]);
                currentPhone = msg.getDisplayOriginatingAddress();//for finding the phone number from whom we got message
            }

            messages = new SmsMessage[pdus.length];
            //currentPhone = messages[0].getDisplayOriginatingAddress();
            Log.v("senders number",currentPhone);

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                //strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                //strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                //strMessage += "\n";
            }

            Log.e("SMS", strMessage);
            ChatAndReserveActivity m = new ChatAndReserveActivity();
            compareNum = MsgSenderNum.getPhoneNum();
            String s = "+1"+compareNum;
            Log.v("s : ",s);
            Log.v("current num",currentPhone);

            if (s.equals(currentPhone)){//checking the phone number from where message is received
                m.receiveText(strMessage);//sending the received message to chat box

            }


        }

    }
}
