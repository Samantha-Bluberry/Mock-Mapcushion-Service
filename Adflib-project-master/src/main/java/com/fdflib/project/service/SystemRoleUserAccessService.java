package com.fdflib.project.service;

import com.fdflib.project.model.SystemRoleUserAccess;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class SystemRoleUserAccessService extends FdfCommonServices{

    public SystemRoleUserAccess saveSystemRoleUserAccess(SystemRoleUserAccess systemRoleUserAccess) {
        if(systemRoleUserAccess != null) {
            //make sure required elements are present
            if (systemRoleUserAccess.userID != -1L
                    && systemRoleUserAccess.uuid != -1L) {

                SystemRoleUserAccess existingSystemRoleUserAccess =
                        getSystemRoleUserAccessByPK(systemRoleUserAccess.uuid, systemRoleUserAccess.userID);

                if(existingSystemRoleUserAccess != null) {
                    systemRoleUserAccess.id = existingSystemRoleUserAccess.id;
                }

                return this.save(SystemRoleUserAccess.class, systemRoleUserAccess).current;

            }
        }
        return null;
    }

    public SystemRoleUserAccess getSystemRoleUserAccess(long id) {
        return getSystemRoleUserAccessWithHistoryById(id).current;

    }

    public FdfEntity<SystemRoleUserAccess> getSystemRoleUserAccessWithHistoryById(long id) {
        FdfEntity<SystemRoleUserAccess> systemRoleUserAccess = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            systemRoleUserAccess = this.getEntityById(SystemRoleUserAccess.class, id);
        }

        return systemRoleUserAccess;
    }

    public SystemRoleUserAccess getSystemRoleUserAccessByPK(long uuid, long userID) {
        FdfEntity<SystemRoleUserAccess> systemRoleUserAccess = getSystemRoleUserAccessWithHistoryByPK(uuid, userID);

        if(systemRoleUserAccess != null && systemRoleUserAccess.current != null){
            return systemRoleUserAccess.current;
        }

        return null;
    }


    public FdfEntity<SystemRoleUserAccess> getSystemRoleUserAccessWithHistoryByPK(long uuid, long userID) {
        FdfEntity<SystemRoleUserAccess> systemRoleUserAccess = new FdfEntity<>();

        if(systemRoleUserAccess != null) {
            WhereClause uuidClause = new WhereClause();
            uuidClause.name = "uuid";
            uuidClause.value = ""+uuid;
            uuidClause.valueDataType = Long.class;

            WhereClause userIDClause = new WhereClause();
            userIDClause.name = "userID";
            userIDClause.value = ""+userID;
            userIDClause.valueDataType = Long.class;

            List<SystemRoleUserAccess> returnedStates =
                    SqlStatement.build().where(uuidClause).where(userIDClause).run(SystemRoleUserAccess.class);
            if (returnedStates.size() > 0) {
                return manageReturnedEntity(returnedStates);
            }
        }
        return null;
    }

    public List<SystemRoleUserAccess> getListOfSystemRoleUserAccessByUUID(long uuid){
        WhereClause idClause = new WhereClause();
        idClause.name = "uuid";
        idClause.value = ""+uuid;
        idClause.valueDataType = Long.class;

        List<SystemRoleUserAccess> systemRoleUserAccessList = SqlStatement.build().where(idClause).run(SystemRoleUserAccess.class);
        return systemRoleUserAccessList;
    }

    public List<SystemRoleUserAccess> getListOfSystemRoleUserAccessByUserID(long userID){
        WhereClause idClause = new WhereClause();
        idClause.name = "userID";
        idClause.value = ""+userID;
        idClause.valueDataType = Long.class;

        List<SystemRoleUserAccess> systemRoleUserAccessList = SqlStatement.build().where(idClause).run(SystemRoleUserAccess.class);
        return systemRoleUserAccessList;
    }
}
