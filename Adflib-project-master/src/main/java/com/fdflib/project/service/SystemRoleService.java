package com.fdflib.project.service;

import com.fdflib.project.model.SystemRole;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class SystemRoleService extends FdfCommonServices{

    public SystemRole saveSystemRole(SystemRole systemRole) {
        if(systemRole != null) {
            //make sure required elements are present
            if (systemRole.roleName != "" && systemRole.roleDescription != "") {
                return this.save(SystemRole.class, systemRole).current;
            }
        }
        return null;
    }

    public SystemRole getSystemRoleById(long id) {
        return getSystemRoleWithHistoryById(id).current;

    }

    public FdfEntity<SystemRole> getSystemRoleWithHistoryById(long id) {
        FdfEntity<SystemRole> systemRole = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            systemRole = this.getEntityById(SystemRole.class, id);
        }

        return systemRole;
    }
}
