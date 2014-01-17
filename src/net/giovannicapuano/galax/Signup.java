package net.giovannicapuano.galax;

import net.giovannicapuano.galax.controllers.Status;
import net.giovannicapuano.galax.controllers.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

        ((Button)findViewById(R.id.button1)).setOnClickListener(registration);
	}

    View.OnClickListener registration = new View.OnClickListener() {
    	public void onClick(View v) {
        	String username = ((EditText)findViewById(R.id.editText1)).getText().toString();
        	String email    = ((EditText)findViewById(R.id.editText2)).getText().toString();
        	String password = ((EditText)findViewById(R.id.editText3)).getText().toString();
        	
        	Status status = User.registration(username, email, password, Signup.this);
        	if(status == null)
        		Toast.makeText(Signup.this, getString(R.string.error_during_signup), Toast.LENGTH_SHORT).show();
        	else if(status.getStatus() == Status.SUCCESS) {
        		Toast.makeText(Signup.this, getString(R.string.signed_up), Toast.LENGTH_SHORT).show();
        		Intent intent = new Intent(Signup.this, Login.class);
        		Signup.this.startActivity(intent);
        		Signup.this.finish();
        	}
        	else
        		Toast.makeText(Signup.this, status.getError(), Toast.LENGTH_SHORT).show();
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
        		Intent intent = new Intent(Signup.this, Login.class);
        		Signup.this.startActivity(intent);
        		Signup.this.finish();
        		return true;
            case R.id.action_exit:
        		Signup.this.finish();
        		return true;
            default:
            	return super.onOptionsItemSelected(item);
        }
    }

}
