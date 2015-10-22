package com.wichita.overwatch.overwatch;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Binder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BluetoothConnectionService extends Service {

    /*
    * Step 01:
    * create the binder or bridge between client and service
    * */
    private final IBinder bluetoothConnectionServiceBinder = new BluetoothConnectionServiceBinder();

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    static OutputStream mmOutputStream;//might screw things up
    static InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;


    public BluetoothConnectionService() {
    }

    /*
    * Step 11:
    * create the methods for this service called by the activity
    * */

    void beginListenForData() {
        final Handler handler = new Handler();
    final byte delimiter = 10; //This is the ASCII code for a newline character

    stopWorker = false;
    readBufferPosition = 0;
    readBuffer = new byte[9999];
    workerThread = new Thread(new Runnable() {
        public void run() {

            long delay = 5000;
            long futureTime = System.currentTimeMillis() + delay;

            while(!Thread.currentThread().isInterrupted() && !stopWorker && System.currentTimeMillis() < futureTime) {
                try {
                    int bytesAvailable = mmInputStream.available();
                    if(bytesAvailable > 0) {
                        byte[] packetBytes = new byte[bytesAvailable];
                        mmInputStream.read(packetBytes);
                        for(int i=0;i<bytesAvailable;i++) {
                            byte b = packetBytes[i];
                            if(b == delimiter) {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");

                                readBufferPosition = 0;

                                if (futureTime == System.currentTimeMillis()) {
                                    futureTime += delay;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            showMessage(data);
                                            //Maybe delay this or something
                                            //myLabel.setText(data);
                                            //make String data as an Extra in order to be seen in
                                            //other activities
                                            //Intent intent = new Intent(BluetoothSerialCommunication.this, MapsActivity.class);
                                            //intent.putExtra("data", data);
                                            //startActivity(intent);
                                            //i.putExtra("dataP", data);

                                        }
                                    });
                                }
                            }
                            else {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                }
                catch (IOException ex) {
                    stopWorker = true;
                }
                //futureTime +=
            }
        }
    });

    workerThread.start();
}



    //Print to screen a message (black box message)
    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_SHORT));
        msg.show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        /*
        * Step 03
        * when onBind is called elsewhere this method returns the binder which is the class
        * created below
        * */
        return bluetoothConnectionServiceBinder;
    }

    /*
    *Step 02
    * create a class that extends the Binder creating the bridge above
    * */
    public class BluetoothConnectionServiceBinder extends Binder {
        BluetoothConnectionService getService() {
            return BluetoothConnectionService.this;
        }
    }
}
