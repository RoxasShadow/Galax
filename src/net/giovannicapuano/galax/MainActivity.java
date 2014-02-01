package net.giovannicapuano.galax;

/*
  Copyright (C) 2014  Giovanni Capuano <webmaster@giovannicapuano.net>
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import net.giovannicapuano.galax.controller.User;
import net.giovannicapuano.galax.util.GPSTracker;
import net.giovannicapuano.galax.util.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
  private User[] people;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    people = new User[] {};
    final int interval = 5000;
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        MainActivity.this.runOnUiThread(getAddresses);
      }
    }, 0, interval);
  }

  private Runnable getAddresses = new Runnable() {
    public void run() {
      GPSTracker gps = new GPSTracker(MainActivity.this, LoginActivity.class);
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
      ArrayList<String> data = new ArrayList<String>();
      Log.v("MainActivity", "Your address is: " + address);

      people = User.getPeople(MainActivity.this);
      if(people != null && people.length > 0)
        for(User person : people)
          data.add(person.toString());
      else
        data.add("No friends found around you.");

      ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
      
      ListView listView = (ListView)findViewById(R.id.addresses);
      listView.setAdapter(arrayAdapter);
      listView.setOnItemClickListener(openChat);
    }
  };
  
  public OnItemClickListener openChat = new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
      if(people.length < position)
        return;
      User user = people[position];
      
      Intent intent = new Intent(MainActivity.this, ChatActivity.class);
      intent.putExtra("recipient", user);
      startActivity(intent);
      finish();
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
      
      startActivity(new Intent(this, LoginActivity.class));
      finish();
      return true;
    case R.id.action_exit:
      finish();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }
}
