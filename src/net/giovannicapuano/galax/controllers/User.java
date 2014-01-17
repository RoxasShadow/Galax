package net.giovannicapuano.galax.controllers;

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
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class User {
	private int    id;
	private String username, email, address;
	
	public User(int id, String username, String email, String address) {
		this.id       = id;
		this.username = username;
		this.email    = email;
		this.address  = address;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {
		return "ID: " + id + " Username: " + username + " Email: " + email + " Address: " + address;
	}

	/**
	 * Perform my sign up.
	 */
	public static Status registration(String username, String email, String password, Context context) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email",    email));
		params.add(new BasicNameValuePair("password", password));

		User.logout(context);
		String body = Utils.postData("/user/signup", params, context).getBody();
		if(body.equals(""))
			return null;
		
		try {
			JSONObject json = new JSONObject(body);
			
			int statusCode  = json.getInt("status");
			String error = statusCode == Status.SUCCESS ? null : json.getString("error");
			return new Status(statusCode, error);
		}
		catch(JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Perform my sign in.
	 */
	public static boolean login(String username, String password, Context context) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		try {
			User.logout(context);
			return new JSONObject(Utils.postData("/user/login", params, context).getBody()).getInt("status") == Status.SUCCESS;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Perform the sign out.
	 */
	public static void logout(Context context) {
		new PersistentCookieStore(context).clear();
	}

	/**
	 * Check if I am logged in.
	 */
	public static boolean isLogged(Context context) {
		return Utils.get("/user/logged_in", context).getBody().equals(Integer.toString(Status.SUCCESS));
	}
	
	/**
	 * Return my own current address.
	 */
	public static String getAddress(Context context) {
		try {
			return new JSONObject(Utils.get("/get_address", context).getBody()).getString("address");
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	/**
	 * Set my new address.
	 */
	public static boolean setAddress(String address, Context context) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("address", address));

		String body = Utils.postData("/set_address", params, context).getBody();
		
		try {
			return new JSONObject(body).getInt("status") == Status.SUCCESS;
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Return all the people around me.
	 */
	public static User[] getPeople(Context context) {
		String body = Utils.get("/people", context).getBody();
		
		try {
			JSONArray json   = new JSONArray(body);
			int       length = json.length();
			User[]    users  = new User[length];
			for(int i = 0; i < length; ++i) {
				JSONObject obj      = json.getJSONObject(i);
				int        id       = obj.getInt("id");
				String     username = obj.getString("username");
				String     email    = obj.getString("email");
				String     address  = obj.getString("address");
				           users[i] = new User(id, username, email, address);
			}
			return users;			
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
