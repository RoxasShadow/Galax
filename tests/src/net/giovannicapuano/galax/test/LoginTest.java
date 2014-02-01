package net.giovannicapuano.galax.test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import net.giovannicapuano.galax.LoginActivity;
import net.giovannicapuano.galax.controller.User;
import net.giovannicapuano.galax.util.GPSTracker;
import net.giovannicapuano.galax.util.Utils;

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
  private Context context;
  private String  username, password, address;
  
  public LoginTest() {
    super(LoginActivity.class);
  }
  
  public void setUp() {
    context  = getInstrumentation().getContext();
    username = "Hajime";
    password = "gacciassu";
  }

  /*public void testRegistration() {
    String email  = "hajimessu@gaccia.ssu";
    
    Status status = User.registration(username, email, password, context);
    assertNotNull(status);
    
    int statusCode = status.getStatus();
    assertEquals(Status.SUCCESS, statusCode);
  }*/

  public void testLogin() {
    boolean login = User.login(username, password, context);
    assertTrue(login);
    
    boolean logged = User.isLogged(context);
    assertTrue(logged);
  }

  public void testSetAddress() {
    GPSTracker gps = new GPSTracker(context, LoginActivity.class);
    boolean canGetLocation = gps.canGetLocation();
    assertTrue(canGetLocation);
    
    double latitude  = 0.0;
    double longitude = 0.0;
    int    n         = 50;
    for(int i = 0; i < n; ++i) {
      latitude  += gps.getLocation().getLatitude();
      longitude += gps.getLocation().getLongitude();
    }
    assertTrue(latitude  > 0.0);
    assertTrue(longitude > 0.0);
    
            address    = Utils.coordinatesToAddress(latitude / n, longitude / n, context);
    boolean setAddress = User.setAddress(address, context);
    assertTrue(setAddress);
  }
}