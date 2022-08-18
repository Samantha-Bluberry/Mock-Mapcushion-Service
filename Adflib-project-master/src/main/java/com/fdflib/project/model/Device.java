package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class Device extends CommonState {

    public Long userID = -1L;
    public Long majorID = -1L;

    public Users currentUser = null;
    public Location currentLocation = null;

    public Device() { super(); }
}
