package com.fdflib.project.service;

import com.fdflib.project.model.ClientRoleUserAccess;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class ClientRoleUserAccessService extends FdfCommonServices {

    public ClientRoleUserAccess saveClientRoleUserAccess(ClientRoleUserAccess clientRoleUserAccess) {
        if(clientRoleUserAccess != null) {
            //make sure required elements are present
            if (clientRoleUserAccess.userID != -1L
                    && clientRoleUserAccess.clientRoleID != -1L
                    && clientRoleUserAccess.uuid != -1L) {

                ClientRoleUserAccess existingClientRoleUserAccess =
                        getClientRoleUserAccessByPK(clientRoleUserAccess.uuid, clientRoleUserAccess.clientRoleID,
                        clientRoleUserAccess.userID);

                if(existingClientRoleUserAccess != null) {
                    clientRoleUserAccess.id = existingClientRoleUserAccess.id;
                }

                return this.save(ClientRoleUserAccess.class, clientRoleUserAccess).current;

            }
        }
        return null;
    }

    public ClientRoleUserAccess getClientRoleUserAccessById(long id) {
        return getClientRoleUserAccessWithHistoryById(id).current;

    }

    public FdfEntity<ClientRoleUserAccess> getClientRoleUserAccessWithHistoryById(long id) {
        FdfEntity<ClientRoleUserAccess> clientRoleUserAccess = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            clientRoleUserAccess = this.getEntityById(ClientRoleUserAccess.class, id);
        }

        return clientRoleUserAccess;
    }

    public ClientRoleUserAccess getClientRoleUserAccessByPK(long uuid, long clientRoleID, long userID) {
        FdfEntity<ClientRoleUserAccess> clientRoleUserAccess = getClientRoleUserAccessWithHistoryByPK(uuid, clientRoleID, userID);

        if(clientRoleUserAccess != null && clientRoleUserAccess.current != null){
            return clientRoleUserAccess.current;
        }

        return null;
    }


    public FdfEntity<ClientRoleUserAccess> getClientRoleUserAccessWithHistoryByPK(long uuid, long clientRoleID, long userID) {
        FdfEntity<ClientRoleUserAccess> clientRoleUserAccess = new FdfEntity<>();

        if(clientRoleUserAccess != null) {
            WhereClause uuidClause = new WhereClause();
            uuidClause.name = "uuid";
            uuidClause.value = ""+uuid;
            uuidClause.valueDataType = Long.class;

            WhereClause clientRoleIDClause = new WhereClause();
            clientRoleIDClause.name = "clientRoleID";
            clientRoleIDClause.value = ""+clientRoleID;
            clientRoleIDClause.valueDataType = Long.class;

            WhereClause userIDClause = new WhereClause();
            userIDClause.name = "userID";
            userIDClause.value = ""+userID;
            userIDClause.valueDataType = Long.class;

            List<ClientRoleUserAccess> returnedStates =
                    SqlStatement.build().where(uuidClause).where(clientRoleIDClause).where(userIDClause).run(ClientRoleUserAccess.class);
            if (returnedStates.size() > 0) {
                return manageReturnedEntity(returnedStates);
            }
        }
        return null;
    }

    public List<ClientRoleUserAccess> getListOfClientRoleUserAccessByUUID(long uuid){
        WhereClause idClause = new WhereClause();
        idClause.name = "uuid";
        idClause.value = ""+uuid;
        idClause.valueDataType = Long.class;

        List<ClientRoleUserAccess> clientRoleUserAccessList = SqlStatement.build().where(idClause).run(ClientRoleUserAccess.class);
        return clientRoleUserAccessList;
    }

    public List<ClientRoleUserAccess> getListOfClientRoleUserAccessByUserID(long userID){
        WhereClause idClause = new WhereClause();
        idClause.name = "userID";
        idClause.value = ""+userID;
        idClause.valueDataType = Long.class;

        List<ClientRoleUserAccess> clientRoleUserAccessList = SqlStatement.build().where(idClause).run(ClientRoleUserAccess.class);
        return clientRoleUserAccessList;
    }
}
