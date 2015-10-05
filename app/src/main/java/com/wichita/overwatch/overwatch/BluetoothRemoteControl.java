/*
* To Do
*   -Put a statement in the catches for the buttons
*   -
* */

package com.wichita.overwatch.overwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.IOException;

public class BluetoothRemoteControl extends AppCompatActivity {

    private int initialInterval = 400;
    private int normalInterval = 100;

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

        /*
        * Each of the forward, backward, left, and right buttons have remote control style controls
        * Pressing one of these buttons will continuously and consistently send a command
        * When it is released the reset type command is given
        * Ex) holding forward repeatedly sends the ~forward command. When you release the forward
        *     button the ~notforward command is sent
        */
        forward.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~notforward");
                        } catch (Exception e) {
                            showMessage("forward.setOnClickListener() E ERROR");
                        }
                    }
                }
        );
        forward.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        try {
                            sendControllerSignal("~forward");
                        } catch (Exception e) {
                            showMessage("forward.setOnLongClickListener() E ERROR");
                        }
                        return false;
                    }
                }
        );
        /*//Only for using when repeating signals need sent
        forward.setOnTouchListener(
                new RepeatListener(initialInterval, normalInterval, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~forward");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }));
        */

        backward.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~notbackward");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );
        backward.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        try {
                            sendControllerSignal("~backward");
                        } catch (Exception e) {
                            showMessage("backward.setOnLongClickListener() E ERROR");
                        }
                        return false;
                    }
                }
        );
        /*
        backward.setOnTouchListener(
                new RepeatListener(initialInterval, normalInterval, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~backward");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }));
        */

        left.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~notleft");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );
        left.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        try {
                            sendControllerSignal("~left");
                        } catch (Exception e) {
                            showMessage("left.setOnLongClickListener() E ERROR");
                        }
                        return false;
                    }
                }
        );
        /*
        left.setOnTouchListener(
                //RepeatListener(int initialInterval, int normalInterval, OnClickListener clickListener)
                new RepeatListener(initialInterval, normalInterval, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~left");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }));
        */

        right.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~notright");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );
        right.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        try {
                            sendControllerSignal("~right");
                        } catch (Exception e) {
                            showMessage("right.setOnLongClickListener() E ERROR");
                        }
                        return false;
                    }
                }
        );
        /*
        right.setOnTouchListener(
                new RepeatListener(initialInterval, normalInterval, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~right");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }));
        */

        //Regular Button click listeners
        a.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~a");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );

        b.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~b");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );

        select.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~select");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );

        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~start");
                        } catch (IOException ex) {
                            ;
                        }
                    }
                }
        );

    }

    //Method which sends a message, passed to it, out on bluetooth
    void sendControllerSignal(String str) throws IOException{
        str += "\n";
        BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
    }

    //Interval setting variables sent to RepeatListener.java GET and SET
    //Not in use ....
    int getInitialInterval() {
        return initialInterval;
    }
    void setInitialInterval(int initialIntervalP) {
        if ( initialIntervalP <= 3000 && initialIntervalP > 0) {
            initialInterval = initialIntervalP;
        } else {
            String theMsg = "Error in setInitalInterval\n";
            Toast msg = Toast.makeText(getBaseContext(), theMsg, (Toast.LENGTH_SHORT) );
            msg.show();
        }
    }
    //Interval setting variables sent to RepeatListener.java GET and SET
    //Not in use .....
    int getNormalInterval() {
        return normalInterval;
    }
    void setNormalInterval(int normalIntervalP) {
        if ( normalIntervalP <= 3000 && normalIntervalP > 0) {
            normalInterval = normalIntervalP;
        } else {
            String theMsg = "Error in setNormalInterval\n";
            Toast msg = Toast.makeText(getBaseContext(), theMsg, (Toast.LENGTH_SHORT) );
            msg.show();
        }
    }

    void increaseInterval () {
        setInitialInterval(getInitialInterval() + 100);
        setNormalInterval(getNormalInterval() + 100);
    }
    void decreaseInterval () {
        setInitialInterval(getInitialInterval() - 100);
        setNormalInterval(getNormalInterval() - 100);
    }

    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_SHORT));
        msg.show();
    }
    //Methods placed by default
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

}
