package com.example.news;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formatted = sdf.format(date);
        return formatted;
    }
}
