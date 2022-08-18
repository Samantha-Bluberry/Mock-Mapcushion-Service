package com.fdflib.project.service;

import com.fdflib.project.model.UserIdentity;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class UserIdentityService extends FdfCommonServices{

    public UserIdentity saveUserIdentity(UserIdentity userIdentity) {
        if(userIdentity != null) {
            //make sure required elements are present
            if (userIdentity.userIdType != "" && userIdentity.userIdNum1 != ""
                    && userIdentity.userIdNum2 != "" && userIdentity.userIdDateIssue != null
                    && userIdentity.userIdDateExpire != null) {
                return this.save(UserIdentity.class, userIdentity).current;
            }
        }
        return null;
    }

    public UserIdentity getUserIdentityById(long id) {
        return getUserIdentityWithHistoryById(id).current;

    }

    public FdfEntity<UserIdentity> getUserIdentityWithHistoryById(long id) {
        FdfEntity<UserIdentity> userIdentity = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            userIdentity = this.getEntityById(UserIdentity.class, id);
        }

        return userIdentity;
    }
}
