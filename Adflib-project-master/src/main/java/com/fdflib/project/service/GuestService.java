package com.fdflib.project.service;

import com.fdflib.project.model.Guest;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class GuestService extends FdfCommonServices {

    public Guest saveGuest(Guest guest) {
        if(guest != null) {
            //make sure required elements are present
            if (guest.majorID != -1 && guest.userID != -1
                    && guest.guestState != null && guest.guestCheckInTime != null) {
                return this.save(Guest.class, guest).current;
            }
        }
        return null;
    }

    public Guest getGuestById(long id) {
        return getGuestWithHistoryById(id).current;

    }

    public FdfEntity<Guest> getGuestWithHistoryById(long id) {
        FdfEntity<Guest> guest = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            guest = this.getEntityById(Guest.class, id);
        }

        return guest;
    }

    public List<Guest> getListOfGuestByLocation(long majorID){
        WhereClause idClause = new WhereClause();
        idClause.name = "majorID";
        idClause.value = ""+majorID;
        idClause.valueDataType = Long.class;

        List<Guest> guestList = SqlStatement.build().where(idClause).run(Guest.class);
        return guestList;
    }

    public FdfEntity<Guest> getGuestByUserWithHistory(Long userID) {
        List<FdfEntity<Guest>> guestsWithHistory = getEntitiesByValueForPassedField(Guest.class, "userID", ""+userID);
        if(guestsWithHistory.size() > 0) {
            return guestsWithHistory.get(0);
        }
        return null;
    }
}
