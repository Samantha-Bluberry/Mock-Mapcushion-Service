package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

import java.util.Date;

public class Users extends CommonState {

    public Long addressID = -1L;
    public Long userIdentityID = -1L;

    public String userFirstName = "";
    public String userLastName = "";
    public String userEmail = "";
    public String userPassword = "";
    public String userColor = "";
    public Date userDOB = null;
    public Character userGender = ' ';
    public String userEyeColor = "";
    public Integer userHeight = -1;
    public Integer userWeight = -1;

    public Address currentAddress = null;
    public UserIdentity currentUserIdentity = null;

    public Users() { super(); }
}
