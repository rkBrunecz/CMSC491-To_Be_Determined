package com.example.randy.to_be_determined;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;

/**
 * Created by Randy on 4/23/2016.
 */
public class CustomFont {
    public static void setCustomFont(String fontName, TextView txt, AssetManager am)
    {
        Typeface font = Typeface.createFromAsset(am, "fonts/" + fontName);
        txt.setTypeface(font);
    }

    public static void setCustomFont(String fontName, Button btn, AssetManager am)
    {
        Typeface font = Typeface.createFromAsset(am, "fonts/" + fontName);
        btn.setTypeface(font);
    }

    public static void setCustomFont(String fontName, CheckBox box, AssetManager am)
    {
        Typeface font = Typeface.createFromAsset(am, "fonts/" + fontName);
        box.setTypeface(font);
    }
}
