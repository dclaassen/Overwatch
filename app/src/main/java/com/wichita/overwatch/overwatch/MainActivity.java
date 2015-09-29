package com.wichita.overwatch.overwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bluetoothSerialCommunicationSwitchScreens;
    Button bluetoothRemoteControlSwitchScreens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothSerialCommunicationSwitchScreens = (Button)findViewById(R.id.bluetoothSerialCommunication);
        bluetoothSerialCommunicationSwitchScreens.setOnClickListener(this);
        bluetoothRemoteControlSwitchScreens = (Button)findViewById(R.id.bluetoothRemoteControl);
        bluetoothRemoteControlSwitchScreens.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //create the link to and start the Bluetooth Serial Communication screen/activity
    public void bluetoothSerialCommunicationSwitchScreensClick() {
        Intent bluetoothSerialCommunicationIntent = new Intent(MainActivity.this, BluetoothSerialCommunication.class);
        startActivity(bluetoothSerialCommunicationIntent);
    }
    //create the link to and start the Bluetooth Remote Control screen/activity
    public void bluetoothRemoteControlSwitchScreensClick() {
        Intent bluetoothRemoteControlIntent = new Intent(MainActivity.this, BluetoothRemoteControl.class);
        startActivity(bluetoothRemoteControlIntent);
    }

    //Chooses the appropriate function to execute depending on which button has been clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bluetoothSerialCommunication:
                bluetoothSerialCommunicationSwitchScreensClick();
                break;
            case R.id.bluetoothRemoteControl:
                bluetoothRemoteControlSwitchScreensClick();
                break;
        }
    }

}
