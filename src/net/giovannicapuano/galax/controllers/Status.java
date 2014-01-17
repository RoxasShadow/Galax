package net.giovannicapuano.galax.controllers;

public class Status {
	public static final int FAIL                 = 0;
	public static final int SUCCESS              = 1;
	public static final int ALREADY_LOGGED_IN    = 2;
	public static final int NOT_LOGGED_IN        = 3;
	public static final int EMPTY_REQUIRED_FIELD = 4;
	public static final int DENIED               = 5;
	public static final int NOT_FOUND            = 6;
	
	private int       status;
	private String    error;
	
	public Status(int status, String error) {
		this.status = status;
		this.error  = error;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
}
