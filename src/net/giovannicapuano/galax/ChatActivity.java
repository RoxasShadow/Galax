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
import java.util.Timer;
import java.util.TimerTask;

import net.giovannicapuano.galax.controller.Message;
import net.giovannicapuano.galax.controller.User;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class ChatActivity extends Activity {
  private User recipient;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    
    ((EditText)findViewById(R.id.message)).setOnEditorActionListener(sendMessage);
    
    recipient = getIntent().getExtras().getParcelable("recipient");
    final int interval = 1000;
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        ChatActivity.this.runOnUiThread(readMessages);
      }
    }, 0, interval);
  }

  OnEditorActionListener sendMessage = new OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
      boolean handled = false;
      if(event.getAction() == KeyEvent.ACTION_DOWN) {
        User.sendMessage(recipient, view.getText().toString(), ChatActivity.this);
        view.setText("");
        handled = true;
      }
      return handled;
    }
  };
  
  private Runnable readMessages = new Runnable() {
    public void run() {
      TextView chat = (TextView)findViewById(R.id.chat);

      try {
        Message[] messages = User.fetchMessages(recipient, ChatActivity.this);
        if(messages.length > 0) {
          chat.setText("");
          for(Message msg : messages) {
            String sender = Uri.decode(msg.getSender());
            if(sender.equals(recipient.getUsername()))
              chat.append(sender + " > " + msg.getText() + "\n");
            else
              chat.append("> " + msg.getText() + "\n");
          }
        }
      }
      catch(Exception e) {
        Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
      }
    }
  };

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.chat, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
    case R.id.action_back:
      startActivity(new Intent(this, MainActivity.class));
      finish();
      return true;
    default:
      return super.onOptionsItemSelected(item);
    }
  }
}
