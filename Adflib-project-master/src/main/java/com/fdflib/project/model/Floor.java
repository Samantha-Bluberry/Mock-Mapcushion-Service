package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class Floor extends CommonState {

    public Long majorID = -1L;

    public Double minAltitude = -0.1;
    public Double maxAltitude = -0.1;
    public Double minLatitude = -0.1;
    public Double maxLatitude = -0.1;
    public Double minLongitude = -0.1;
    public Double maxLongitude = -0.1;

    public Location currentLocation = null;

    public Floor() {
        super();
    }
}
