package com.example.randy.to_be_determined;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CheckBox;

/**
 * CustomFont
 * Developers: Randy Brunecz, Jessica Rolfe, Venkat Rami Reddy, Rajuta Parlance
 *
 * This class uses a number of static classes that expedite the process of adding a custom
 * font to various UI elements.
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
