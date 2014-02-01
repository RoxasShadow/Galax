package net.giovannicapuano.galax.util;

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
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import net.giovannicapuano.galax.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;

public class Utils {
  /**
   * Perform a HTTP POST request.
   */
  public static HttpData postData(String path, List<NameValuePair> post, Context context) {
    HttpParams httpParameters = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
    HttpConnectionParams.setSoTimeout(httpParameters, 10000);

    int    status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    String body   = "";

    HttpClient   httpClient   = new DefaultHttpClient(httpParameters);
    HttpContext  localContext = new BasicHttpContext();

    localContext.setAttribute(ClientContext.COOKIE_STORE, new PersistentCookieStore(context));
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

    try {
      HttpPost httpPost = new HttpPost(context.getString(R.string.server) + path);
      httpPost.setEntity(new UrlEncodedFormEntity(post));
      HttpResponse response = httpClient.execute(httpPost, localContext);

      status = response.getStatusLine().getStatusCode();
      body   = EntityUtils.toString(response.getEntity());
    }
    catch(ClientProtocolException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    return new HttpData(status, body);
  }

  /**
   * Perform a HTTP GET request.
   */
  public static HttpData get(String path, Context context) {
    HttpParams httpParameters = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
    HttpConnectionParams.setSoTimeout(httpParameters, 10000);

    int    status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    String body   = "";

    HttpClient   httpClient   = new DefaultHttpClient(httpParameters);
    HttpContext  localContext = new BasicHttpContext();

    localContext.setAttribute(ClientContext.COOKIE_STORE, new PersistentCookieStore(context));
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

    try {
      HttpGet      httpGet  = new HttpGet(context.getString(R.string.server) + path);
      HttpResponse response = httpClient.execute(httpGet, localContext);

      status = response.getStatusLine().getStatusCode();
      body   = EntityUtils.toString(response.getEntity());
    }
    catch(ClientProtocolException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    return new HttpData(status, body);
  }

  /**
   * Convert geographic coordinates to a human-readable address.
   */
  public static String coordinatesToAddress(double latitude, double longitude, Context context) {
    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    if(!Geocoder.isPresent())
      return "";

    try {
      List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

      if(addresses != null && !addresses.isEmpty()) {
        Address returnedAddress = addresses.get(0);
        StringBuilder address   = new StringBuilder();
        for(int i = 0; i < returnedAddress.getMaxAddressLineIndex(); ++i)
          address.append(returnedAddress.getAddressLine(i)).append("\n");
        return address.toString();
      }
      else {
        return "";
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}