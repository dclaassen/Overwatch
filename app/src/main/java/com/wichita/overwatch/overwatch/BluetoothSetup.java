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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
/*
* Service Connection Step 04:
* import the BluetoothConnectionService and the BluetoothConnectionServiceBinder
* */
import com.wichita.overwatch.overwatch.BluetoothConnectionService.BluetoothConnectionServiceBinder;

public class BluetoothSetup extends AppCompatActivity {

    TextView bSJTextView01;

    /*
    * Service Connection Step 05:
    * create a service object to connect to
    * create a test variable to determine if the activity has been bound to the service
    * */
    static BluetoothConnectionService bluetoothConnectionService01;
    boolean isBound = false;
    //END  Service Connection Step 05:

    /*
    * Service Connection Step 06 - 08:
    * Service Connection Step 06:
    * create a connection
    * */
    private ServiceConnection bluetoothConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*
            * Service Connection Step 07:
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
            * Service Connection Step 08:
            * on disconnect do this
            * A:    set the  test variable for boundedness to false
            * */
            isBound = false;

        }
    };

    /*
    * Service Connection Step 10:
    * A:    create methods that the BluetoothSetup will use
    *       ex) public void serviceMethod(View view) //pass the views for the UI changes
z    * B:    use bluetoothConnectionServiceLocalVariable.someMethod() to use the service
    *   Reasoning:
    *   Do UI modification in Activity while the service continues to run its threads. All
    *   activities bound to this service will keep the Bluetooth connection alive and accessible to
    *   the application.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setup);

        /*
        * Service Connection Step 09:
        * A:    create intent to bind
        * B:    bind the intent, connection, context
        * */
        Intent intent01 = new Intent(this, BluetoothConnectionService.class);
        bindService(intent01, bluetoothConnection, Context.BIND_AUTO_CREATE);
        //END Service Connection Step 09:

        bSJTextView01 = (TextView)findViewById(R.id.bSXTextView01);
        Button bSJOpenButton = (Button)findViewById(R.id.bSXOpenButton);
        Button bSJCloseButton = (Button)findViewById(R.id.bSXCloseButton);

        //bSJOPEN button's click listener: Initialization, Construction, Method
        bSJOpenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    bluetoothConnectionService01.findBT();
                    bSJTextView01.setText("Bluetooth Device Found");
                    bluetoothConnectionService01.openBT();
                    bSJTextView01.setText("Bluetooth Opened");
                }
                catch (IOException ex) {
                    bluetoothConnectionService01.showMessage("openButton.setOnClickListener IO ERROR");
                }
                catch (Exception e) {
                    bluetoothConnectionService01.showMessage("openButton.setOnClickListener() E ERROR");
                }
            }
        });

        //bSJCLOSE button's click listener: Initialization, Construction, Method
        bSJCloseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    bluetoothConnectionService01.closeBT();
                    bSJTextView01.setText("Bluetooth Closed");
                }
                catch (IOException ex) {
                    bluetoothConnectionService01.showMessage("closeButton.setOnClickListener IO ERROR");
                }
                catch (Exception e) {
                    bluetoothConnectionService01.showMessage("closeButton.setOnClickListener() E ERROR");
                }
            }
        });

    }//END onCreate

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

}
