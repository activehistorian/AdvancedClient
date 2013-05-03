package com.example.advancedclient;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;

public class BroadcastActivity extends Activity {
	private String RoomDetails;
	private String CurrentIP;
	
	private String MasterIP;
	private String SecondaryIP;
	private String TertiaryIP;
	
	private TextView connectedIP;
	private TextView connectedIP2;
	private TextView connectedIP3;	
	private TextView sensorvalue;
	
	private Button SimulateSensor;
	private boolean connected = false;
	private static final String DEVICE_ADDRESS =  "00:12:12:04:32:51";
	int x = 0;
	
	private ArduinoReceiver arduinoReceiver = new ArduinoReceiver();

	
	private String TAG = "PIKAPIKA";
	
	protected void onCreate(Bundle savedInstanceState) {
		
		RoomDetails = MainActivity.getRoomDetails();
		MasterIP = MainActivity.getMasterIP();
		SecondaryIP = MainActivity.getSecondaryIP();
		TertiaryIP = MainActivity.getTertiaryIP();
		CurrentIP = MasterIP;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcast_activity);
		//final MediaPlayer missionimpossible = MediaPlayer.create(BroadcastActivity.this,R.raw.missionimpossible);
		
		connectedIP = (TextView) findViewById(R.id.iplabel);
		connectedIP2 = (TextView) findViewById(R.id.iplabel2);
		connectedIP3 = (TextView) findViewById(R.id.iplabel3);
		sensorvalue = (TextView) findViewById(R.id.SensorValue);
		
		connectedIP.setText(MasterIP);
		connectedIP2.setText(SecondaryIP);
		connectedIP3.setText(TertiaryIP);
		
	/*	SimulateSensor = (Button)findViewById(R.id.sensor);
		SimulateSensor.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		if (!connected) {
            				//missionimpossible.start();
                            	Thread thread = new Thread(new ClientThread());
                                thread.start();
                                
                                Toast toast = Toast.makeText(getApplicationContext(),"Help request sent to " + MasterIP, Toast.LENGTH_SHORT);
            					toast.show();
                                
                                if(SecondaryIP != null){
                                	Toast toast2 = Toast.makeText(getApplicationContext(),"Help request sent to " + SecondaryIP, Toast.LENGTH_SHORT);
                					toast2.show();
                                }
                                
                                if(TertiaryIP != null){
                                	Toast toast2 = Toast.makeText(getApplicationContext(),"Help request sent to " + TertiaryIP, Toast.LENGTH_SHORT);
                					toast2.show();
                                }
                                
                                
                    }
            	}
		}); */
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// in order to receive broadcasted intents we need to register our receiver
		registerReceiver(arduinoReceiver, new IntentFilter(AmarinoIntent.ACTION_RECEIVED));
		
		// this is how you tell Amarino to connect to a specific BT device from within your own code
		Amarino.connect(this, DEVICE_ADDRESS);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// if you connect in onStart() you must not forget to disconnect when your app is closed
		Amarino.disconnect(this, DEVICE_ADDRESS);
		
		// do never forget to unregister a registered receiver
		unregisterReceiver(arduinoReceiver);
	}
	
	public String getLocalIpAddress()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                 }
             }
         } catch (SocketException ex) {
         }

         return null;
    }

    public class ClientThread implements Runnable {
        public void run() {
            try {
            	
	                InetAddress serverAddr = InetAddress.getByName(MasterIP);
	                Socket socket = new Socket(serverAddr, 8080);
	                connected = true;
	                while (connected) {
	                    try {
	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	                        out.println(getLocalIpAddress() + "." + RoomDetails);
	                    } catch (Exception e) {}
	                }
	                socket.close();
	                
	                if(SecondaryIP != null){
	                	InetAddress secondaryserverAddr = InetAddress.getByName(SecondaryIP);
	                	Socket socket2 = new Socket(secondaryserverAddr,8080);
	                	
	                	while (connected) {
		                    try {
		                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())), true);
		                        out.println(getLocalIpAddress() + "." + RoomDetails);
		                    } catch (Exception e) {}
		                }
		                socket2.close();
	                }
	                
	                if(TertiaryIP != null){
	                	InetAddress tertiaryserverAddr = InetAddress.getByName(TertiaryIP);
	                	Socket socket3 = new Socket(tertiaryserverAddr,8080);
	                	
	                	while (connected) {
		                    try {
		                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket3.getOutputStream())), true);
		                        out.println(getLocalIpAddress() + "." + RoomDetails);
		                    } catch (Exception e) {}
		                }
		                socket3.close();
	                }
	                 
            } catch (Exception e) {connected = false; }
        }
    }

    public class ArduinoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String data = null;
			
			final int dataType = intent.getIntExtra(AmarinoIntent.EXTRA_DATA_TYPE, -1);
			if (dataType == AmarinoIntent.STRING_EXTRA){
				data = intent.getStringExtra(AmarinoIntent.EXTRA_DATA);
				if (data != null){
					sensorvalue.setText(data);	
					x = Integer.parseInt(data);
					if(x > 2 && x < 40){
						Thread thread = new Thread(new ClientThread());
                        thread.start();
					}
				}
			}
		}
	}
    
    public int irtodist(String irda){
    	int ir = Integer.parseInt(irda);
    	int dist;
    	if(ir > 5){
    		dist = (6787/ (ir - 3)) - 4;
    	}
    	else{
    		dist = 200;
    	}
    	return dist;			
    }
}
	