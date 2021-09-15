package com.YAStudio.buffmate;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public final class Globals {
 public static int Cal = 0;

 static void updateCal() {


  Log.i("Sleep", "Done sleeping");

  //TextView tvCalCount = findViewById(R.id.tvCalCount);

  //ProgressBar calorieTracker =  findViewById(R.id.pbCalorie);
  String s = Globals.Cal + " Cal";

  //tvCalCount.setText(s);

  int percent = Globals.Cal / 2500 * 100;
  //calorieTracker.setProgress(percent);

 }

}
