package com.example.advancedclient;
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
	private static String MasterIP;
	private static String MasterIP2;
	private static String MasterIP3;
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
                if(RoomDetails.equals("")|| MasterIP.equals("")){
                	Toast toast = Toast.makeText(getApplicationContext(),"Must Provide at least Room Number and Master IP 1",Toast.LENGTH_LONG);
                	toast.show();
                }
                else{
                	Intent intent = new Intent(MainActivity.this,BroadcastActivity.class);
                    startActivity(intent);
                }
          }
      });
	}
	
	public static boolean validIP(String ip){
		int x = 0;
		
		
		
		return false;
	}
	
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
