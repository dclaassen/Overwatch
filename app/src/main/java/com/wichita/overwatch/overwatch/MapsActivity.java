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
import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    ArrayList<LatLng> markerPoints;
    EditText latlngStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_maps);
            setUpMapIfNeeded();
            markerPoints = new ArrayList<>();
            latlngStrings = (EditText) findViewById(R.id.latlngStrings);
            Button sendLatLng = (Button) findViewById(R.id.sendLatLng);
            Button startRoute = (Button) findViewById(R.id.startRoute);
            Button stopRoute = (Button) findViewById(R.id.stopRoute);
            Button uadLoc = (Button) findViewById(R.id.uadLocation);

            // Getting reference to SupportMapFragment of the activity_main
            SupportMapFragment fm =
                    (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Map for the SupportMapFragment
            map = fm.getMap();

            // Setting onclick event listener for the map
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                /*
                * If the maximum amount of markerPoints marked on the map has been reached
                * Then nothing will happen when the user tries to click to add a point
                * Map points in the ArrayList
                * [0-8] User added waypoints
                * [9] UAD update location point
                * 10 total available waypoints available without a paid license
                * */
                //if(markerPoints.size() >= MAXPOINTS){
                //    return;
                //}

                // Adding new item to the ArrayList
                markerPoints.add(point);
                // Adding the new marker to the map
                MarkerOptions options = new MarkerOptions();
                options.position(point);
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
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

        //sendLatLng Button Click
        sendLatLng.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {
                    String str = "Sent:\n";
                    //Print to textbox all the latitude longitude strings
                    str += markerPoints.toString();
                    latlngStrings.setText(str);
                    transmitStringOnBluetooth();
                }
            }
        );

        //startRoute Button Click
        startRoute.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~startroute");
                        }
                        catch (Exception e) {
                            showMessage("startRoute.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

        //stopRoute Button Click
        stopRoute.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~stoproute");
                        }
                        catch (Exception e) {
                            showMessage("stopRoute.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

        //uadLoc Button Click
        uadLoc.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~uadlocation");
                            newUADMapPoint();
                        }
                        catch (Exception e) {
                            showMessage("uadLoc.setOnClickListener() E ERROR");
                        }
                    }
                }
        );
        }//END try under onCreate()
        catch (Exception e){
            showMessage("Error EXCEPTION e");
        }
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

        //Storage class for lat/long
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Address address = addressList.get(0);
            //get lat/lng of the address variable store it in a LatLng object
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            //add a marker to the above LatLng object
            map.addMarker(new MarkerOptions().position(latlng).title("Marker"));
            map.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }
        catch (NullPointerException npe) {
            showMessage("NullPointer reference in onSerach() MapsActivity");
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
        // Do a null check to confirm that there is no existing instantiated map
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }

    //Set up the map
    private void setUpMap() {
        map.setMyLocationEnabled(true);
    }

    //Print messages to the screen as toasts
    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_SHORT));
        msg.show();
    }

    void sendControllerSignal(String str) throws IOException{
        str += "\n";
        BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
    }

    public void newUADMapPoint() {
        try {
            String newPointStr;
            //static string from the bluetooth "listener" thread in BluetoothSerialCommunication
            newPointStr = BluetoothSerialCommunication.bscTOmaStr;
            showMessage(newPointStr);
            LatLng point = stringToLatLng(newPointStr);

            //clear the map of all points
            map.clear();
            //Re add user location
            map.setMyLocationEnabled(true);
            //Re add all the markers for markerPoints
            for (int i = 0; i < markerPoints.size(); i++) {
                MarkerOptions options1 = new MarkerOptions();
                options1.position(markerPoints.get(i));
                options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(options1);
            }

            //Add the new UAD loc point requested by the click on Loc
            markerPoints.add(point);
            MarkerOptions options2 = new MarkerOptions();
            options2.position(point);
            options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            map.addMarker(options2);
            //Zoom the map view onto the new point
            map.animateCamera(CameraUpdateFactory.newLatLng(point));
        }
        catch (Exception e) {
            showMessage("newMapPoint() ERROR");
        }
    }

    /*
    * Method which converts a string to a LatLng object for the purposes of adding a GPS point
    * requested then received from the UAD to the Google map
    *
    * STRING FORMAT INPUT MUST BE:
    * "Latitude,Longitude"
    *
    * It takes the string and tokenizes it using the "," as a delimeter
    * It then takes the first string token and converts it into a Latitude double variable
    * It then takes the second string token and converts it inot a Longitude double variable
    * It then returns a new object of type LatLng, using the new Latitude and Longitude double
    * variables as parameters, to the calling Class
    * */
    public LatLng stringToLatLng(String str) {
        double lat;
        double lng;
        String strLat;
        String strLng;

        StringTokenizer st = new StringTokenizer(str);
        strLat = st.nextToken(",");
        strLng = st.nextToken();

        lat = Double.parseDouble(strLat);
        lng = Double.parseDouble(strLng);

        return new LatLng(lat, lng);
    }

    /*
    Transmits all of the markerPoints' latitude and longitude in String form
    * "latitude,longitude\n"
    * */
    public void transmitStringOnBluetooth() {
        String str;
        LatLng latLng;

        for (int i = 0; i < markerPoints.size(); i++) {
            str = "";
            latLng = markerPoints.get(i);

            str += i + 1;
            str += ",";
            str += latLng.latitude;
            str += ",";
            str += latLng.longitude + "\n";

            try {
                BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
            }
            catch (Exception e) {
                showMessage("sendLatLng.setOnClickListener() ERROR");
            }
        }
    }

}
