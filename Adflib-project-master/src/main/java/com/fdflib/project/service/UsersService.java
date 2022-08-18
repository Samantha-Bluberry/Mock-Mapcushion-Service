package com.fdflib.project.service;

import com.fdflib.project.model.Users;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class UsersService extends FdfCommonServices{

    public Users saveUsers(Users users) {
        if(users != null) {
            //make sure required elements are present
            if (users.addressID != -1L && users.userIdentityID != -1L
                    && users.userFirstName != "" && users.userLastName != null
                    && users.userEmail != null && users.userPassword != "") {
                return this.save(Users.class, users).current;
            }
        }
        return null;
    }

    public Users getUsersById(long id) {
        return getUsersWithHistoryById(id).current;

    }

    public FdfEntity<Users> getUsersWithHistoryById(long id) {
        FdfEntity<Users> users = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            users = this.getEntityById(Users.class, id);
        }

        return users;
    }
}
