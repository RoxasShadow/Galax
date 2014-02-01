package net.giovannicapuano.galax.controller;

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
public class Message {
  private int    id;
  private String from, to, text;

  public Message(int id, String from, String to, String text) {
    this.id   = id;
    this.from = from;
    this.to   = to;
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSender() {
    return from;
  }

  public void setSender(String from) {
    this.from = from;
  }

  public String getRecipient() {
    return to;
  }

  public void setRecipient(String to) {
    this.to = to;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String toString() {
    return "ID: " + id + " From: " + from + " To: " + to + " Message: " + text;
  }
}
