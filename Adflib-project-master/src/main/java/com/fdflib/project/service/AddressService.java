package com.fdflib.project.service;

import com.fdflib.project.model.Address;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;



public class AddressService extends FdfCommonServices {

    public Address saveAddress(Address address) {
        if(address != null) {
            //make sure required elements are present
            if (address.addressLine1 != "" && address.addressCity != ""
                    && address.addressCountry != "") {
                return this.save(Address.class, address).current;
            }
        }
        return null;
    }

    public Address getAddressById(long id) {
        return getAddressWithHistoryById(id).current;

    }

    public FdfEntity<Address> getAddressWithHistoryById(long id) {
        FdfEntity<Address> address = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            address = this.getEntityById(Address.class, id);
        }

        return address;
    }
}
