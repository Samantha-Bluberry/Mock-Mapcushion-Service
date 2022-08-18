package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

import java.util.Date;


public class Guest extends CommonState {

    public Long userID = -1L;
    public Long majorID = -1L;

    public GuestState guestState = null;
    public String guestDestination = "";
    public Date guestCheckInTime = null;

    public Users currentUser = null;
    public Location currentLocation = null;

    public Guest() { super(); }
}
