package com.YAStudio.buffmate;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// I have mostly done this tracking activity.

public class MapsTrackingActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private static final int PERMISSION_FINE_LOCATION = 1;
    private static final int PERMISSION_EXTERNAL_WRITE = 2;
    private static final int PERMISSION_EXTERNAL_READ = 3;
    private GoogleMap mMap;
    PolylineOptions po = new PolylineOptions();
    private LocationManager locationManager;
    private LocationListener locLis;
    private List<Location> locations = new ArrayList<>();
    private float distanceRan;
    private Calendar cal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("'Week'w'day'u");

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Check for permissions
        //Check for permission of writing
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //ask for permission
            ActivityCompat.requestPermissions(MapsTrackingActivity.this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_WRITE);
        }//Check for permissions
        //Check for permission of reading
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            //ask for permission
            ActivityCompat.requestPermissions(MapsTrackingActivity.this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_READ);
        }


        final Button btnFinish = findViewById(R.id.btnFinishRun);
        final Button btnPause = findViewById(R.id.btnStopTracking);
        btnPause.setOnClickListener(new View.OnClickListener() {
            //Button switches between two functions: one pauses the tracking activity and the other resumes it
            @Override
            //Pause activity and switch to resume activity
            public void onClick(View v) {
                locationManager.removeUpdates(locLis);
                btnPause.setText("Resume Run");
                btnPause.setOnClickListener(new View.OnClickListener() {
                    //Resumes activity and switches to pause activity
                    @Override
                    public void onClick(View v) {
                        btnPause.setText("Pause Run");
                        drawPath();
                    }
                });
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(locLis);
                mMap.snapshot(callback);
                setResult(Activity.RESULT_OK, new Intent().putExtra("distance", distanceRan));
                finish();
            }
        });
    }

    GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
        Bitmap bitmap;
        @Override
        public void onSnapshotReady(Bitmap snapshot) {
            // TODO Auto-generated method stub
            bitmap = snapshot;
            String filePath;
            FileOutputStream fout;


            try
            {
                filePath = Environment.getExternalStorageDirectory() + "/Buffmate/maps/" + format.format(cal.getTime()) + "run.jpeg";
                File file = new File(filePath);
                fout = new FileOutputStream(file.getAbsoluteFile());
                // Write the string to the file
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                fout.flush();
                fout.close();
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                Log.d("ImageCapture", "FileNotFoundException");
                Log.d("ImageCapture", e.getMessage());
                filePath = "";
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                Log.d("ImageCapture", "IOException");
                Log.d("ImageCapture", e.getMessage());
                filePath = "";
            }
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Check for permission of using Fine location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        drawPath();
    }

    private void drawPath() {

        //Check for permission of using Fine location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

         locLis = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                double y = location.getLatitude();
                double x = location.getLongitude();
                String message = "Last seen at: " + y + ", " + x + "\n";
                Log.i("locationServ", message);
                po.add(new LatLng(y, x));
                Polyline polyline = mMap.addPolyline(po);
                locations.add(location);
                if(locations.size() >= 2){
                    distanceRan += locations.get(0).distanceTo(locations.get(1));
                    locations.clear();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locLis);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

}
