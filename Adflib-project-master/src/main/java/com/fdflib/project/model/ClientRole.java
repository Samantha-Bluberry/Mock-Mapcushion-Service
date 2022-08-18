package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class ClientRole extends CommonState {

    public Long uuid = -1L;

    public String roleName = "";
    public String roleDescription = "";

    public Client currentClient = null;

    public ClientRole() { super(); }
}
