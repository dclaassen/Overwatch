package com.wichita.overwatch.overwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class BluetoothRemoteControl extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_remote_control);

        Button forward = (Button)findViewById(R.id.forward);
        Button backward = (Button)findViewById(R.id.backward);
        Button left = (Button)findViewById(R.id.left);
        Button right = (Button)findViewById(R.id.right);
        Button a = (Button)findViewById(R.id.a);
        Button b = (Button)findViewById(R.id.b);
        Button select = (Button)findViewById(R.id.select);
        Button start = (Button)findViewById(R.id.start);

        forward.setOnClickListener(this);
        backward.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        select.setOnClickListener(this);
        start.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth_remote_control, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forward:
                try {
                    sendControllerSignal("~forward");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.backward:
                try {
                    sendControllerSignal("~backward");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.left:
                try {
                    sendControllerSignal("~left");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.right:
                try {
                    sendControllerSignal("~right");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.a:
                try {
                    sendControllerSignal("~a");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.b:
                try {
                    sendControllerSignal("~b");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.select:
                try {
                    sendControllerSignal("~select");
                } catch (IOException ex) {
                    break;
                }
                break;
            case R.id.start:
                try {
                    sendControllerSignal("~start");
                } catch (IOException ex) {
                    break;
                }
                break;
        }
    }
    void sendControllerSignal(String str) throws IOException{
        str += "\n";
        BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
    }

}
