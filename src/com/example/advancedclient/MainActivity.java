package com.example.advancedclient;
import java.net.InetAddress;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button Submit;
	private static String RoomDetails;
	private static String MasterIP = null;
	private static String MasterIP2 = null;
	private static String MasterIP3 = null;
	private EditText TempDetails;
	private EditText IP1;
	private EditText IP2;
	private EditText IP3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Submit = (Button)findViewById(R.id.connect);
		TempDetails = (EditText)findViewById(R.id.roomnamenumber);
		
		IP1 = (EditText)findViewById(R.id.ipaddress);
		IP2 = (EditText)findViewById(R.id.ipaddress2);
		IP3 = (EditText)findViewById(R.id.ipaddress3);
		
		Submit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RoomDetails = TempDetails.getText().toString();
                MasterIP = IP1.getText().toString();
                MasterIP2 = IP2.getText().toString();
                MasterIP3 = IP3.getText().toString();
                boolean a,b,c,d;
            
                if(RoomDetails.equals("")|| MasterIP.equals("")){
                	Toast toast = Toast.makeText(getApplicationContext(),
                			"Must Provide at least Room Number and Master IP 1",Toast.LENGTH_LONG);
                	toast.show();
                }
                else{
                	a = checkIP(MasterIP); b = checkIP(MasterIP2); c = checkIP(MasterIP3);
                	if(a == true && b == true && c == true){
                		Intent intent = new Intent(MainActivity.this,BroadcastActivity.class);
                        startActivity(intent);
                	} 
                	else{
                		Toast toast = Toast.makeText(getApplicationContext(), "Please check Staff ID's", Toast.LENGTH_SHORT);
                		toast.show();
                	}	
                }
            }
      });
		
	}
	
	public boolean checkIP(String ip){
		try{
		if(ip.length() == 0 || ip.equals("")){
			return true;
		}else{
			try{
				InetAddress serverAddr = InetAddress.getByName(ip);
				return true;
			}catch(Exception e){return false;}
		}
		}
		 catch(Exception e) {
			return false;
		}
	}
	
	/*public boolean checkIP(String ip){
		if(ip.equals("")){return true;}
		if(ip.length() > 15 || ip.length() < 7){
			return false;
		}
		int counter = 0;
		int part1, part2, part3, part4;
		
		char[] ipArray = ip.toCharArray();
		String temp = "";
		
		
		while(ipArray[counter] != '.' && counter < (ipArray.length - 1)){
			temp = temp + ipArray[counter];
			counter = counter + 1;
		}
			
		if(counter > 3){return false;}
		counter = counter + 1;
		part1 = Integer.parseInt(temp);
		temp = "";
		if(part1 < 0 || part1 > 255){return false;}
		
		while(ipArray[counter] != '.' && counter < (ipArray.length - 1)){
			temp = temp + ipArray[counter];
			counter = counter + 1;
		}
		
		if (counter > 7){return false;}
		counter = counter + 1;
		part2 = Integer.parseInt(temp);
		temp = "";
		if(part2 < 0 || part2 > 255){ return false;}
		
		while(ipArray[counter] != '.' && counter < (ipArray.length - 1)){
			temp = temp + ipArray[counter];
			counter = counter + 1;
		}
		
		if (counter > 11){return false;}
		counter = counter + 1;
		part3 = Integer.parseInt(temp);
		temp = "";
		if(part3 < 0 || part3 > 255){ return false;}
		
		while(counter < (ipArray.length - 1)){
			temp = temp + ipArray[counter];
			counter = counter + 1;
		}
		
		part4 = Integer.parseInt(temp);
		if(part4 < 0 || part4 > 255){ return false;}
		
		return true;
	} */
	
	public static String getSecondaryIP(){
		return MasterIP2;
	}
	
	public static String getTertiaryIP(){
		return MasterIP3;
	}
	
	public static String getMasterIP(){
		return MasterIP;
	}
	
	public static String getRoomDetails(){
		return RoomDetails;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
