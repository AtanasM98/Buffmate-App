package com.YAStudio.buffmate;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ActionBar toolbar;
    private SensorManager sensorManager;
    private float count = 0;
    boolean stepCountRunning;

    final FragmentManager fm = getSupportFragmentManager();
    final Fragment foodFragment = new Search_food_fragment();
    final Fragment runFragment = new Running_Fragment();
    Fragment active = runFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.frame_container, foodFragment, "1").hide(foodFragment).commit();
        fm.beginTransaction().add(R.id.frame_container, runFragment, "1").hide(runFragment).commit();

        toolbar = getSupportActionBar();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Overview");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }




    @Override
    protected void onResume() {
        super.onResume();
        stepCountRunning = true;
        Sensor counteSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // Worked on Calorie tracker
        final TextView tvCalCount = findViewById(R.id.tvCalCount);
        final ProgressBar calorieTracker = findViewById(R.id.pbCalorie);
        String s = Globals.Cal + " Cal";
        tvCalCount.setText(s);
        //int percent = (Globals.Cal / 2500) * 100;
        calorieTracker.setProgress(Globals.Cal);

        if(counteSensor != null){
            sensorManager.registerListener(this, counteSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Log.e("CountError", "Count sensor unavailable!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stepCountRunning = false;
    }

    // Worked on step tracker
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(stepCountRunning){
            count = event.values[0];
            TextView tvSteps = findViewById(R.id.tvStepCount);
            tvSteps.setText(String.valueOf(count));
            final ProgressBar stepTracker = findViewById(R.id.pbSteps);

            stepTracker.setProgress((int)count);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_overview:
                    toolbar.setTitle("Overview");
                    fm.beginTransaction().hide(active).commit();
                    return true;
                case R.id.navigation_food:
                    toolbar.setTitle("Food");
                    fm.beginTransaction().hide(active).show(foodFragment).commit();
                    active = foodFragment;
                    return true;
                case R.id.navigation_running:
                    toolbar.setTitle("Running");
                    fm.beginTransaction().hide(active).show(runFragment).commit();
                    active = runFragment;
                    return true;
                case R.id.navigation_weight:
                    toolbar.setTitle("Weight");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
