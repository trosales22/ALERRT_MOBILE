package com.capstone.alerrt_app.classes.data_objects;

public class AgencyDO {
    private String agencyID,agencyCaption,agencyFirstname,agencyLastname,agencyPosition,agencyContactNumber,agencyStatus;

    public AgencyDO(String agencyID, String agencyCaption, String agencyFirstname, String agencyLastname, String agencyPosition, String agencyContactNumber, String agencyStatus) {
        this.agencyID = agencyID;
        this.agencyCaption = agencyCaption;
        this.agencyFirstname = agencyFirstname;
        this.agencyLastname = agencyLastname;
        this.agencyPosition = agencyPosition;
        this.agencyContactNumber = agencyContactNumber;
        this.agencyStatus = agencyStatus;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public String getAgencyCaption() {
        return agencyCaption;
    }

    public String getAgencyFirstname() {
        return agencyFirstname;
    }

    public String getAgencyLastname() {
        return agencyLastname;
    }

    public String getAgencyPosition() {
        return agencyPosition;
    }

    public String getAgencyContactNumber() {
        return agencyContactNumber;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }
}
