package com.fdflib.project.service;

import com.fdflib.project.model.PingBeacon;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.SqlStatement;
import com.fdflib.model.util.WhereClause;
import com.fdflib.service.impl.FdfCommonServices;
import java.util.List;

public class PingBeaconService extends  FdfCommonServices{

    public PingBeacon savePingBeacon(PingBeacon pingBeacon) {
        if(pingBeacon != null) {
            //make sure required elements are present
            if (pingBeacon.pingID != -1L
                    && pingBeacon.beaconID != -1L) {

                PingBeacon existingPingBeacon =
                        getPingBeaconByPK(pingBeacon.pingID, pingBeacon.beaconID);

                if(existingPingBeacon != null) {
                    pingBeacon.id = existingPingBeacon.id;
                }

                return this.save(PingBeacon.class, pingBeacon).current;

            }
        }
        return null;
    }

    public PingBeacon getPingBeaconById(long id) {
        return getPingBeaconWithHistoryById(id).current;

    }

    public FdfEntity<PingBeacon> getPingBeaconWithHistoryById(long id) {
        FdfEntity<PingBeacon> pingBeacon = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            pingBeacon = this.getEntityById(PingBeacon.class, id);
        }

        return pingBeacon;
    }

    public PingBeacon getPingBeaconByPK(long pingID, long beaconID) {
        FdfEntity<PingBeacon> pingBeacon = getPingBeaconWithHistoryByPK(pingID, beaconID);

        if(pingBeacon != null && pingBeacon.current != null){
            return pingBeacon.current;
        }

        return null;
    }


    public FdfEntity<PingBeacon> getPingBeaconWithHistoryByPK(long pingID, long beaconID) {
        FdfEntity<PingBeacon> pingBeacon = new FdfEntity<>();

        if (pingBeacon != null) {
            WhereClause pingIDClause = new WhereClause();
            pingIDClause.name = "pingID";
            pingIDClause.value = "" + pingID;
            pingIDClause.valueDataType = Long.class;

            WhereClause beaconIDClause = new WhereClause();
            beaconIDClause.name = "beaconID";
            beaconIDClause.value = "" + beaconID;
            beaconIDClause.valueDataType = Long.class;

            List<PingBeacon> returnedStates =
                    SqlStatement.build().where(pingIDClause).where(beaconIDClause).run(PingBeacon.class);
            if (returnedStates.size() > 0) {
                return manageReturnedEntity(returnedStates);
            }
        }
        return null;
    }
}
