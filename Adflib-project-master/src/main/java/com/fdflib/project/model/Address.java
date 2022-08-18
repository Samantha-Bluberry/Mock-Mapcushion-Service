package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

public class Address extends CommonState {

    public String addressLine1 = "";
    public String addressLine2 = "";
    public String addressCity = "";
    public String addressState = "";
    public String addressZIP = "";
    public String addressCountry = "";

    public Address() { super(); }
}
