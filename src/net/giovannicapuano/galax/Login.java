package net.giovannicapuano.galax;

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

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        ((Button)findViewById(R.id.button1)).setOnClickListener(login);
        ((Button)findViewById(R.id.button2)).setOnClickListener(registration);
        
        if(User.isLogged(this)) {
    		Intent intent = new Intent(Login.this, MainActivity.class);
    		Login.this.startActivity(intent);
    		Login.this.finish();
        }
    }

    View.OnClickListener login = new View.OnClickListener() {
    	public void onClick(View v) {
        	String username = ((EditText)findViewById(R.id.editText1)).getText().toString();
        	String password = ((EditText)findViewById(R.id.editText2)).getText().toString();
        	
        	if(User.login(username, password, Login.this)) {
        		Toast.makeText(Login.this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
        		Intent intent = new Intent(Login.this, MainActivity.class);
        		Login.this.startActivity(intent);
        		Login.this.finish();
        	}
        	else
        		Toast.makeText(Login.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    	}
    };

    View.OnClickListener registration = new View.OnClickListener() {
    	public void onClick(View v) {
    		Intent intent = new Intent(Login.this, Signup.class);
    		Login.this.startActivity(intent);
    	}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_exit:
            	Login.this.finish();
        		return true;
            default:
            	return super.onOptionsItemSelected(item);
        }
    }
    
}
