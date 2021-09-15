package com.YAStudio.buffmate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapsPopUp extends AppCompatActivity {

    private static final int PERMISSION_EXTERNAL_READ = 0;
    int week, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        //Check for permissions
        //Check for permission of reading
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_READ);
        }

        week = getIntent().getIntExtra("week", 0);
        day = getIntent().getIntExtra("day", 0);



        setImage(Environment.getExternalStorageDirectory() + "/Buffmate/maps/Week" + week + "day" + day + "run.jpeg");

    }

    private void setImage(String imgPath){
        final TextView tvStatus = findViewById(R.id.tvStatus);
        File f = new File(imgPath);
        Bitmap b = BitmapFactory.decodeFile(imgPath);
        final ImageView imageView = findViewById(R.id.weeklyRunView);
        imageView.setImageBitmap(b);
        tvStatus.setText("Week " + week + ", day of week: " + day);
    }
}
