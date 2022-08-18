package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class Location extends CommonState {

    public Long uuid = -1L;
    public Long addressID = -1L;

    public Double minLatitude = -0.1;
    public Double maxLatitude = -0.1;
    public Double minLongitude = -0.1;
    public Double maxLongitude = -0.1;

    public Client currentClient = null;
    public Address currentAddress = null;

    public Location() { super(); }
}
