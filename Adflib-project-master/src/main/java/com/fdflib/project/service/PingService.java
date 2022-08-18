package com.fdflib.project.service;

import com.fdflib.project.model.Ping;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class PingService extends FdfCommonServices{

    public Ping savePing(Ping ping) {
        if(ping != null) {
            //make sure required elements are present
            if (ping.majorID != -1 && ping.floorID != -1
                    && ping.uuid != -1 && ping.deviceID != -1
                    && ping.rssi != -1 && ping.txPower != -1
                    && ping.accuracy != -0.1 && ping.remoteTime != -1L
                    && ping.pingLat != -0.1 && ping.pingLong != 0.1
                    && ping.timeStamp != null) {
                return this.save(Ping.class, ping).current;
            }
        }
        return null;
    }

    public Ping getPingById(long id) {
        return getPingWithHistoryById(id).current;

    }

    public FdfEntity<Ping> getPingWithHistoryById(long id) {
        FdfEntity<Ping> ping = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            ping = this.getEntityById(Ping.class, id);
        }

        return ping;
    }
}
