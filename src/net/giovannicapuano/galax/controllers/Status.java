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
