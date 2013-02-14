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
	private EditText TempDetails;
	private EditText TempIP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Submit = (Button)findViewById(R.id.connect);
		TempDetails = (EditText)findViewById(R.id.roomnamenumber);
		TempIP = (EditText)findViewById(R.id.ipaddress);
		
		Submit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RoomDetails = TempDetails.getText().toString();
                MasterIP = TempIP.getText().toString();
                if(RoomDetails.equals("")|| MasterIP.equals("")){
                	Toast toast = Toast.makeText(getApplicationContext(),"No blank fields allowed",Toast.LENGTH_LONG);
                	toast.show();
                }
                else{
                	Intent intent = new Intent(MainActivity.this,BroadcastActivity.class);
                    startActivity(intent);
                }
          }
      });
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
