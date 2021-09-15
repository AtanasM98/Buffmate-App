package com.YAStudio.buffmate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DayRunInformation {
    //Day and Week and hour
    String date;
    float distanceRan;

    public DayRunInformation(float dist){
        distanceRan = dist;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("u 'week' w 'at' HH:m");
        date = format.format(calendar.getTime());
    }
}
