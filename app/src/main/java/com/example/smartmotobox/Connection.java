package com.example.smartmotobox;

public class Connection {
    String Connection;
    String SSID;

    public Connection() {}

    public Connection(String Connection, String SSID) {
        this.Connection = Connection;
        this.SSID = SSID;
    }

    public String getConnection() { return Connection; }
    public void setConnection(String Connection) { this.Connection = Connection; }

    public String getSSID() { return SSID; }
    public void setSSID(String SSID) { this.SSID = SSID; }
}
