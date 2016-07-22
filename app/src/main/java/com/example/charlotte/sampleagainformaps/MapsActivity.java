package com.example.charlotte.sampleagainformaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MapsActivity extends FragmentActivity implements LocationListener {

    GoogleMap map;
    String mylocation = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }



        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();

        //sample test
        final String[] array_places = new String[]{"University of the Philippines - Diliman North Side",
                "Marikina Boys Town East Side","LRT 2 - Santolan Depot","Intramuros Golf Course","Ultra - Pasig",
                "Villamor Air Base Golf Club","Bonifacio Global City","Chateau Open Area","Kagitingan Executive Golf Course","El Salvador Open Area"};

        final Double[] array_latitude = new Double[] {14.618873,14.666823,14.622044,14.593188,14.577634,14.519300,14.540867,14.536991,14.529825,14.488050};
        final Double[] array_longitude = new Double[] {121.073970,121.118052,121.086042,120.996594,121.066295,121.025447,121.050318,121.044095,121.055017,121.016328};



        final ArrayAdapter<String> adapter;



        for(int counter = 0; counter < array_places.length; counter++){


            map.addMarker(new MarkerOptions()
                    .position (new LatLng(array_latitude[counter],array_longitude[counter]))
                    .title(array_places[counter])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    returnOrigin returnDestination = new returnOrigin();

                    for(int count = 0; count < array_places.length; count++){
                        if(array_places[count].equals(marker.getTitle())){
                            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(array_latitude[count], array_longitude[count], 1);
                                if(addresses.size() > 0){
                                    Address address = addresses.get(0);

                                    String dcity = address.getAddressLine(0);
                                    String dcity2 = address.getAddressLine(1);
                                    String dcity1and2 = dcity + " " + dcity2;

                                    returnDestination.getDestination(dcity1and2);

                                  Toast.makeText(MapsActivity.this, returnDestination.setDestination() + "   " + returnDestination.setOrigin(), Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(MapsActivity.this, MapsPath.class);
                                    intent.putExtra("destination", dcity1and2);
                                    intent.putExtra("origin", mylocation);
                                    startActivity(intent);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Toast.makeText(MapsActivity.this, array_latitude[count] + "&&&" + array_longitude[count], Toast.LENGTH_LONG).show();
                        }
                    }
                    return false;
                }
            });
        }

//        map.addMarker(new MarkerOptions()
//                .position (new LatLng(14.618873, 121.073970))
//                .title("University of the Philippines - Diliman North Side")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                if(marker.getTitle().equals("University of the Philippines - Diliman North Side"))
//                    Toast.makeText(MapsActivity.this, "Your Destination: "+ marker.getTitle(), Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//        });
//
//        map.addMarker(new MarkerOptions().position (new LatLng(14.666823, 121.118052)).title("Marikina Boys Town East Side").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//
//        map.addMarker(new MarkerOptions().position (new LatLng(14.622044, 121.086042)).title("LRT 2 - Santolan Depot").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.593188, 120.996594)).title("Intramuros Golf Course").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.577634, 121.066295)).title("Ultra - Pasig").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.519300, 121.025447)).title("Villamor Air Base Golf Club").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.540867, 121.050318)).title("Bonifacio Global City").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.536991, 121.044095)).title("Chateau Open Area").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.529825, 121.055017)).title("Kagitingan Executive Golf Course").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));
//        map.addMarker(new MarkerOptions().position (new LatLng(14.488050, 121.016328)).title("El Salvador Open Area").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evacuation)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latlng = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(latlng).title("You are here!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_you)));
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));

        returnOrigin returnOrigin = new returnOrigin();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size() > 0 ){
                Address address = addresses.get(0);

                String currentcity = address.getAddressLine(0);
                String currentcity1 = address.getAddressLine(1);

                String origin = currentcity + " " + currentcity1;

                returnOrigin.getOrigin(origin);

                mylocation = returnOrigin.setOrigin();
                Toast.makeText(this, "Your Current location: "+ returnOrigin.setOrigin(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onSearch(View view) {

        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            android.location.Address address = addressList.get(0);
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            map.addMarker(new MarkerOptions().position(latlng).title("you've searched").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_search)));
            map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
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

    }

    public boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
