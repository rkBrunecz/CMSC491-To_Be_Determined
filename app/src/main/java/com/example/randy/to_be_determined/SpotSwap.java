package com.example.randy.to_be_determined;

import android.app.Application;

/**
 * Created by Randy on 5/2/2016.
 *
 * This class is designed to share certain information across multiple activities such as a
 * Users account name.
 *
 * Resources:
 * http://www.helloandroid.com/tutorials/maintaining-global-application-state
 */
public class SpotSwap extends Application {
    private String userName = "";

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
}
