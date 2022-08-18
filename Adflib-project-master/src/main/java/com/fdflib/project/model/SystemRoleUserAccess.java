package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class SystemRoleUserAccess extends CommonState {
    public Long userID = -1L;
    public Long systemRoleID = -1L;
    public Long uuid = -1L;

    public Users currentUser = null;
    public SystemRole currentSystemRole = null;
    public Client currentClient = null;

    public SystemRoleUserAccess() { super(); }
}
