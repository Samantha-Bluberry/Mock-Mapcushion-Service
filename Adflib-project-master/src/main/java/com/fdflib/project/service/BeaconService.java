package com.fdflib.project.service;

import com.fdflib.project.model.Beacon;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;


public class BeaconService extends FdfCommonServices {

    public Beacon saveBeacon(Beacon beacon) {
        if(beacon != null) {
            //make sure required elements are present
            if (beacon.majorID != -1 && beacon.floorID != -1
                    && beacon.latitude != -0.1 && beacon.longitude != -0.1
                    && beacon.altitude != -0.1) {
                return this.save(Beacon.class, beacon).current;
            }
        }
        return null;
    }

    public Beacon getBeaconById(long id) {
        return getBeaconWithHistoryById(id).current;

    }

    public FdfEntity<Beacon> getBeaconWithHistoryById(long id) {
        FdfEntity<Beacon> beacon = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            beacon = this.getEntityById(Beacon.class, id);
        }

        return beacon;
    }

    public List<Beacon> getListOfBeaconsByFloor(long floorID){
        WhereClause idClause = new WhereClause();
        idClause.name = "floorID";
        idClause.value = ""+floorID;
        idClause.valueDataType = Long.class;

        List<Beacon> beaconList = SqlStatement.build().where(idClause).run(Beacon.class);
        return beaconList;
    }
}
