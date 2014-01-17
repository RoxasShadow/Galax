package net.giovannicapuano.galax;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import net.giovannicapuano.galax.controllers.GPSTracker;
import net.giovannicapuano.galax.controllers.User;
import net.giovannicapuano.galax.controllers.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final int interval = 10000; // 10s
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}
		}, 0, interval);
	}
	
	private void TimerMethod() {
		this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
		public void run() {
    		GPSTracker gps = new GPSTracker(MainActivity.this, Login.class);
    		if(!gps.canGetLocation()) {
    			gps.showSettingsAlert();
    			return;
    		}

    		double latitude  = 0.0;
    		double longitude = 0.0;
    		int    n         = 50;
    		for(int i = 0; i < n; ++i) {
    			latitude  += gps.getLocation().getLatitude();
    			longitude += gps.getLocation().getLongitude();
    		}

	        String address = Utils.coordinatesToAddress(latitude / n, longitude / n, MainActivity.this);
	        if(address.equals(""))
	    		Toast.makeText(MainActivity.this, "Error retrieving coordinates.", Toast.LENGTH_SHORT).show();
	        else if(!User.setAddress(address, MainActivity.this))
	    		Toast.makeText(MainActivity.this, "Error sending coordinates.", Toast.LENGTH_SHORT).show();
			
			address = User.getAddress(MainActivity.this);
			Log.v("MainActivity", "Your address is: " + address);
			ArrayList<String> data = new ArrayList<String>();
			
			User[] people = User.getPeople(MainActivity.this);
			if(people != null && people.length > 0)
				for(User person : people)
					data.add(person.toString());
			else
				data.add("No friends found around you.");
			
	        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
	        ((ListView)findViewById(R.id.listView1)).setAdapter(arrayAdapter);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
            	User.logout(this);
        		Intent intent = new Intent(MainActivity.this, Login.class);
        		MainActivity.this.startActivity(intent);
        		MainActivity.this.finish();
        		return true;
            case R.id.action_exit:
            	MainActivity.this.finish();
        		return true;
            default:
            	return super.onOptionsItemSelected(item);
        }
    }

}
