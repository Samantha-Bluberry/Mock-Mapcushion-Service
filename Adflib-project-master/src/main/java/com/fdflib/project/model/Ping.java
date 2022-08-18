package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

import java.util.Date;

public class Ping extends CommonState {

    public Long uuid = -1L;
    public Long floorID = -1L;
    public Long majorID = -1L;
    public Long deviceID = -1L;

    public Integer rssi = -1;
    public Integer txPower = -1;
    public Double accuracy = -0.1;
    public Long remoteTime = -1L;
    public Double pingLat = -0.1;
    public Double pingLong = -0.1;
    public Date timeStamp = null;

    public Client currentClient = null;
    public Floor currentFloor = null;
    public Location currentLocation = null;
    public Device currentDevice = null;

    public Ping() {
        super();
    }
}
