package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class Beacon extends CommonState {

    public Long majorID = -1L;
    public Long floorID = -1L;

    public Double latitude = -0.1;
    public Double longitude = -0.1;
    public Double altitude = -0.1;

    public Location currentLocation = null;
    public Floor currentFloor = null;

    public Beacon() { super(); }
}
