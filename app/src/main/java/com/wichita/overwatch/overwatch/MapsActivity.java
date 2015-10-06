package com.wichita.overwatch.overwatch;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    ArrayList<LatLng> markerPoints;
    EditText latlngStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        // Initializing
        markerPoints = new ArrayList<LatLng>();
        latlngStrings = (EditText)findViewById(R.id.latlngStrings);
        Button sendLatLng = (Button)findViewById(R.id.sendLatLng);

        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to Button
        //Button btnDraw = (Button)findViewById(R.id.btn_draw);

        // Getting Map for the SupportMapFragment
        map = fm.getMap();

        // Enable MyLocation Button in the Map
        ///map.setMyLocationEnabled(true);

        // Setting onclick event listener for the map
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Already 10 locations with 8 waypoints and 1 start location and 1 end location.
                // Upto 8 waypoints are allowed in a query for non-business users
                if(markerPoints.size()>=10){
                    return;
                }

                // Adding new item to the ArrayList
                markerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED and
                 * for the rest of markers, the color is AZURE
                 */
                /*
                if(markerPoints.size()==1){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if(markerPoints.size()==2){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }else{
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
                */

                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                // Add new marker to the Google Map Android API V2
                map.addMarker(options);

            }
        });


        // The map will be cleared on long click
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                // Removes all the points from Google Map
                map.clear();

                // Removes all the points in the ArrayList
                markerPoints.clear();

            }
        });


        // Click event handler for Button btn_draw
        /*
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checks, whether start and end locations are captured
                if(markerPoints.size() >= 2){
                    LatLng origin = markerPoints.get(0);
                    LatLng dest = markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    //String url = getDirectionsUrl(origin, dest);

                    //DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    //downloadTask.execute(url);
                }

            }2
        });
        */
        //click event handler for sendLatLng
        sendLatLng.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        String str = markerPoints.toString();//string used for maps activity printout
                        //String transmitString;//string that is sent to Arduino via bluetooth
                        //transmitString = formatLatLngStrings();
                        //transmitString += "\n";
                        latlngStrings.setText(str);//send to textbox in activity
                        transmitStringOnBluetooth();
                        //try to send transmitString via bluetooth in byte form
                        /*
                        try {
                            BluetoothSerialCommunication.mmOutputStream.write(transmitString.getBytes());
                        } catch (Exception e) {
                            showMessage("sendLatLng.setOnClickListener() ERROR");
                        }
                        */
                    }
                }
        );

    }//END onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void changeType(View view) {
        if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void onZoom(View view) {
        if (view.getId() == R.id.Bzoomin) {
            map.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view.getId() == R.id.Bzoomout) {
            map.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    public void onSearch(View view) {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();

        List<Address> addressList = null;//list of addresses

        if (location != null || !location.equals("")) {
            //Storage class for lat/long
            Geocoder geocoder = new Geocoder(this);
            try {
                //list of addresses
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            //get lat/lng of the address variable store it in a LatLng object
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            //add a marker to the above LatLng object
            map.addMarker(new MarkerOptions().position(latlng).title("Marker"));
            map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #map} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        //set a marker for your location
        map.setMyLocationEnabled(true);
    }
    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_SHORT));
        msg.show();
    }
    public String formatLatLngStrings() {
        String str = "";
        LatLng latLng;
        /*
        for (int i = 0; i < markerPoints.size(); i++) {
            latLng = markerPoints.get(i);
            str += i + 1;
            str += ",";
            str += latLng.latitude;
            str += ",";
            str += latLng.longitude + "\n";
            showMessage(str);
        }
        */
        return str;
    }
    public void transmitStringOnBluetooth() {
        String str = "";
        LatLng latLng;

        for (int i = 0; i < markerPoints.size(); i++) {
            str = "";
            latLng = markerPoints.get(i);
            str += i + 1;
            str += ",";
            str += latLng.latitude;
            str += ",";
            str += latLng.longitude + "\n";
            showMessage(str);
            try {
                BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
            } catch (Exception e) {
                showMessage("sendLatLng.setOnClickListener() ERROR");
            }

        }

    }

}
