package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class ClientRoleUserAccess extends CommonState {
    public Long userID = -1L;
    public Long clientRoleID = -1L;
    public Long uuid =-1L;

    public Users currentUser = null;
    public ClientRole currentClientRole = null;
    public Client currentClient = null;

    public ClientRoleUserAccess() { super(); }
}
