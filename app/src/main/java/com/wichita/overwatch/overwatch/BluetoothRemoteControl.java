package com.wichita.overwatch.overwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class BluetoothRemoteControl extends AppCompatActivity {

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
        * On pressing button the action command is sent ex) forward
        * On releasing button the halt command is sent ex) notforward
        * When it is released the reset type command is given
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
        /*//Only used when repeating signals need sent
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
                        } catch (Exception e) {
                            showMessage("backward.setOnClickListener() E ERROR");
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
        //Only used when repeating signals need to be sent
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
                        } catch (Exception e) {
                            showMessage("left.setOnClickListener() E ERROR");
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
        //Only used when repeating signals need to be sent
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
                        }
                        catch (Exception e) {
                            showMessage("right.setOnClickListener() E ERROR");
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
        //Only used when repeating signals need to be sent
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
                        } catch (Exception e) {
                            showMessage("a.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

        //b button click listener
        b.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~b");
                        } catch (Exception e) {
                            showMessage("b.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

        //select button click listener
        select.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~select");
                        }
                        catch (Exception e) {
                            showMessage("select.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

        //start button click listener
        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            sendControllerSignal("~start");
                        }
                        catch (Exception e) {
                            showMessage("right.setOnClickListener() E ERROR");
                        }
                    }
                }
        );

    }

    //Method which sends controller signal type message passed to it out on bluetooth to the UAVAP
    void sendControllerSignal(String str) throws IOException{
        str += "\n";
        BluetoothSerialCommunication.mmOutputStream.write(str.getBytes());
    }

    //Prints a message to the Android screen (in the form of a toast: black message box)
    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_SHORT));
        msg.show();
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

}