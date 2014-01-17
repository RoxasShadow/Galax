package net.giovannicapuano.galax.controllers;

public class HttpData {
	private int    status;
	private String body;
	
	public HttpData() {}
	
	public HttpData(int status, String body) {
		this.status = status;
		this.body   = body;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}
