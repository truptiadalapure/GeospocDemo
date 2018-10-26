package com.example.truptiadalapure.geospocdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener,GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    android.location.LocationListener locationListenerGPS;
    LocationManager locationManager;
    private ArrayList<LatLng> latLngArrayList;
    Button btnSave,btnNext;
    double lat, lon;
    private DatabaseHelper db;
    String name,phoneNumber,imagePath;

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnNext = (Button) findViewById(R.id.btnNext);
        latLngArrayList = new ArrayList<>();
        db = new DatabaseHelper(this);
        Intent in = getIntent();
        name = in.getStringExtra("name");
        phoneNumber = in.getStringExtra("phone");
        imagePath = in.getStringExtra("imagePath");
        Log.d("",""+name);

        locationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
               // mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("It's Me!"));


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }


        };


        //btn save

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save to db
                if(latLngArrayList.size() == 0)
                {
                    new AlertDialog.Builder(MapsActivity.this)
                            .setMessage("Kindly Plot pins")
                            .setPositiveButton("OK", null)
                            .show();
                }else{
                    Employee emp = new Employee();
                    emp.setName(name);
                    emp.setPhone(phoneNumber);
                    emp.setLatitude("18.7");
                    emp.setLongitude("73.5");
                    emp.setImagePath(imagePath);
                    db.insertEmployee(emp);

                    LatLng[] points = GetPolygonPoints();

                    Polygon p = mMap.addPolygon(
                            new PolygonOptions()
                                    .add(points)
                                    .strokeWidth(7)
                                    .fillColor(Color.CYAN)
                                    .strokeColor(Color.BLUE));
                    LatLngBounds.Builder b = new LatLngBounds.Builder();
                    for (LatLng point : points) {
                        b.include(point);
                    }
                    LatLngBounds bounds = b.build();
                    //Change the padding as per needed
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20,20,5);
                    // mMap.animateCamera(cu);

                }



            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MapsActivity.this,EmployeeListActivity.class);
                startActivity(intent);
            }
        });


    }



    private LatLng[] GetPolygonPoints() {


        ArrayList<LatLng> points = new ArrayList<LatLng>();

        String point;


        return latLngArrayList.toArray(new LatLng[points.size()]);

    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }


    //--------- async task for save ----------

    private class SaveDataAsyncTask extends AsyncTask<String, Void, String> {



        @Override
        protected void onPreExecute() {
            //show progress

        }

        @Override
        protected String doInBackground(String... strings) {
            Employee employee = new Employee();
            db.insertEmployee(employee);
            Log.d("lat long == ",""+latLngArrayList.toString());

            return null;
        }


        @Override
        protected void onPostExecute(String result) {


        }
    }



    //map delegtae methods


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationIfPermitted();



       // mMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Marker z1").zIndex(1.0f));
        mMap.setOnMapLongClickListener(this);


        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-27.457, 153.040),
                        new LatLng(-33.852, 151.211),
                        new LatLng(-37.813, 144.962),
                        new LatLng(-34.928, 138.599)));
// Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d("lat == ",""+latLng.latitude);
        Log.d("long == ",""+latLng.longitude);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .title("Marker z1")
                .zIndex(1.0f));

        latLngArrayList.add(latLng);

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
