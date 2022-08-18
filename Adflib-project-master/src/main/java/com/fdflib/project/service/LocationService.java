package com.fdflib.project.service;

import com.fdflib.project.model.Location;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class LocationService extends FdfCommonServices {

    public Location saveLocation(Location location) {
        if(location != null) {
            //make sure required elements are present
            if (location.uuid != -1 && location.minLatitude != -0.1
                    && location.maxLatitude != -0.1 && location.minLongitude != -0.1
                    && location.maxLongitude != -0.1 && location.addressID != -1) {
                return this.save(Location.class, location).current;
            }
        }
        return null;
    }

    public Location getLocationById(long id) {
        return getLocationWithHistoryById(id).current;

    }

    public FdfEntity<Location> getLocationWithHistoryById(long id) {
        FdfEntity<Location> location = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            location = this.getEntityById(Location.class, id);
        }

        return location;
    }

    public List<Location> getListOfLocationsByUUID(long uuid){
        WhereClause idClause = new WhereClause();
        idClause.name = "uuid";
        idClause.value = ""+uuid;
        idClause.valueDataType = Long.class;

        List<Location> locationList = SqlStatement.build().where(idClause).run(Location.class);
        return locationList;
    }
}
