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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BroadcastActivity extends Activity {
	private String RoomDetails;
	private String MasterIP;
	private TextView connectedIP;
	private Button SimulateSensor;
	private boolean connected = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		RoomDetails = MainActivity.getRoomDetails();
		MasterIP = MainActivity.getMasterIP();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcast_activity);
		final MediaPlayer missionimpossible = MediaPlayer.create(BroadcastActivity.this,R.raw.missionimpossible);
		connectedIP = (TextView) findViewById(R.id.iplabel);
		connectedIP.setText(MasterIP);
		SimulateSensor = (Button)findViewById(R.id.sensor);
		
		SimulateSensor.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		if (!connected) {
            				missionimpossible.start();
                            Thread thread = new Thread(new ClientThread());
                            thread.start();
                        
                    }
            	}
		});
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
            } catch (Exception e) {connected = false; }
        }
    }

	
}
