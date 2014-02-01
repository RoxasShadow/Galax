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
import net.giovannicapuano.galax.controller.Status;
import net.giovannicapuano.galax.controller.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);

    ((Button)findViewById(R.id.registration)).setOnClickListener(registration);
  }

  OnClickListener registration = new OnClickListener() {
    public void onClick(View v) {
      String username = ((EditText)findViewById(R.id.username)).getText().toString();
      String email    = ((EditText)findViewById(R.id.email)).getText().toString();
      String password = ((EditText)findViewById(R.id.password)).getText().toString();

      Status status = User.registration(username, email, password, SignupActivity.this);
      if(status == null)
        Toast.makeText(SignupActivity.this, getString(R.string.error_during_signup), Toast.LENGTH_SHORT).show();
      else if(status.getStatus() == Status.SUCCESS) {
        Toast.makeText(SignupActivity.this, getString(R.string.signed_up), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
      }
      else
        Toast.makeText(SignupActivity.this, status.getError(), Toast.LENGTH_SHORT).show();
    }
  };

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.registration, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
    case R.id.action_back:
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
