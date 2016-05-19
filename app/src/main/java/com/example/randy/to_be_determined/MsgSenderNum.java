package com.example.randy.to_be_determined;

/**
 * MsgSenderNum
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This class is used for storing the phone number of the user who posted the spot.
 * It is used for sending message(chat) to that particular user to confirm about the spot.
 * It is also used in broadcast receiver for checking the phone number from which we received message and allow only those
 * messages which are relevant to user.
 */

//for storing the phone number of the person who posted the spots used while sending messages to that particular person
public class MsgSenderNum {

    public static String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    private static String phoneNum;

}
