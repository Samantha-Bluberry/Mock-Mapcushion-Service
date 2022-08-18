package com.fdflib.project.service;

import com.fdflib.project.model.Floor;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class FloorService extends FdfCommonServices{

    public Floor saveFloor(Floor floor) {
        if(floor != null) {
            //make sure required elements are present
            if (floor.majorID != -1 && floor.minLatitude != -0.1
                    && floor.maxLatitude != -0.1 && floor.minLongitude != -0.1
                    && floor.maxLongitude != -0.1 && floor.minAltitude != -0.1
                    && floor.maxAltitude != -0.1) {
                return this.save(Floor.class, floor).current;
            }
        }
        return null;
    }

    public Floor getFloorById(long id) {
        return getFloorWithHistoryById(id).current;

    }

    public FdfEntity<Floor> getFloorWithHistoryById(long id) {
        FdfEntity<Floor> floor = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            floor = this.getEntityById(Floor.class, id);
        }

        return floor;
    }

    public List<Floor> getListOfFloorsByLocation(long majorID){
        WhereClause idClause = new WhereClause();
        idClause.name = "majorID";
        idClause.value = ""+majorID;
        idClause.valueDataType = Long.class;

        List<Floor> floorList = SqlStatement.build().where(idClause).run(Floor.class);
        return floorList;
    }
}
