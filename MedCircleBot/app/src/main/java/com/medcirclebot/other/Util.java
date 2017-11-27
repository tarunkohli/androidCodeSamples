package com.medcirclebot.other;


import java.util.Calendar;

public class Util {

    public static String getTimeInAmPM() {
        String delegate = "hh:mm aaa";
        return (String) android.text.format.DateFormat.format(delegate, Calendar.getInstance().getTime());
    }
}
