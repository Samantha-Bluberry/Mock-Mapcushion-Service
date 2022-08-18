package com.fdflib.project.service;

import com.fdflib.project.model.ClientRole;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class ClientRoleService extends FdfCommonServices{

    public ClientRole saveClientRole(ClientRole clientRole) {
        if(clientRole != null) {
            //make sure required elements are present
            if (clientRole.uuid != -1L && clientRole.roleName != ""
                && clientRole.roleDescription != "") {
                return this.save(ClientRole.class, clientRole).current;
            }
        }
        return null;
    }

    public ClientRole getClientRoleById(long id) {
        return getClientRoleWithHistoryById(id).current;

    }

    public FdfEntity<ClientRole> getClientRoleWithHistoryById(long id) {
        FdfEntity<ClientRole> clientRole = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            clientRole = this.getEntityById(ClientRole.class, id);
        }

        return clientRole;
    }
}
