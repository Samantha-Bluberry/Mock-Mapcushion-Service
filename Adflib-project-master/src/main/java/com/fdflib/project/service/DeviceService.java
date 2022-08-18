package com.fdflib.project.service;

import com.fdflib.project.model.Device;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

public class DeviceService extends FdfCommonServices{

    public Device saveDevice(Device device) {
        if(device != null) {
            //make sure required elements are present
            if (device.userID != -1L && device.majorID != -1L ) {
                return this.save(Device.class, device).current;
            }
        }
        return null;
    }

    public Device getDeviceById(long id) {
        return getDeviceWithHistoryById(id).current;

    }

    public FdfEntity<Device> getDeviceWithHistoryById(long id) {
        FdfEntity<Device> device = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            device = this.getEntityById(Device.class, id);
        }

        return device;
    }
}
