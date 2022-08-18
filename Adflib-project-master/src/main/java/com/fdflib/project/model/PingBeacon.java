package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class PingBeacon extends CommonState {
    public Long pingID = -1L;
    public Long beaconID = -1L;

    public Ping currentPing = null;
    public Beacon currentBeacon = null;

    public PingBeacon() {
        super();
    }
}
