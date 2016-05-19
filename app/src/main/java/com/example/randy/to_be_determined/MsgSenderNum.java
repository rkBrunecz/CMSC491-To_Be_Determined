package com.example.randy.to_be_determined;

/**
 * Created by bvenk on 5/18/2016.
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
