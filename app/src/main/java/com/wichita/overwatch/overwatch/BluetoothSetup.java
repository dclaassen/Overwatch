package com.wichita.overwatch.overwatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*
* Step 04:
* import the BluetoothConnectionService and the BluetoothConnectionServiceBinder
* */
import com.wichita.overwatch.overwatch.BluetoothConnectionService.BluetoothConnectionServiceBinder;

public class BluetoothSetup extends AppCompatActivity {


    /*
    * Step 05:
    * create a service object to connect to
    * create a test variable to determine if the activity has been bound to the service
    * */
    BluetoothConnectionService bluetoothConnectionService01;
    boolean isBound = false;

    /*
    * Step 10:
    * A:    create methods that the BluetoothSetup will use
    *       ex) public void serviceMethod(View view) //pass the views for the UI changes
    * B:    use bluetoothConnectionService01.someMethod() to use the service
    *   SUGGESTION:
    *   Do UI modification here while the service continues running its threads
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setup);
        /*
        * Step 09:
        * A:    create intent to bind
        * B:    bind the intent, connection, context
        * */
        Intent intent01 = new Intent(this, BluetoothConnectionService.class);
        bindService(intent01, bluetoothConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Step 06:
    * create a connection
    * */
    private ServiceConnection bluetoothConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*
            * Step 07:
            * on connect do this....
            * A:    create a binder
            * B:    bind the service
            * C:    set the test variable for boundness to true
            * */
            BluetoothConnectionServiceBinder binder = (BluetoothConnectionServiceBinder) service;
            bluetoothConnectionService01 = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            /*
            * Step 08:
            * on disconnect do this
            * A:    set the  test variable for boundedness to false
            * */
            isBound = false;

        }
    };

}
