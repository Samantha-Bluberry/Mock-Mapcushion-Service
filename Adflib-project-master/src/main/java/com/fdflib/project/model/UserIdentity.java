package com.fdflib.project.model;

import com.fdflib.model.state.CommonState;

import java.util.Date;

public class UserIdentity extends CommonState {

    public String userIdType = "";
    public String userIdNum1 = "";
    public String userIdNum2 = "";
    public Date userIdDateIssue = null;
    public Date userIdDateExpire = null;

    public UserIdentity() { super(); }
}
