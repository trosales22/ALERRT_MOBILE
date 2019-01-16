package com.capstone.alerrt_app.classes.data_objects;

public class AgencyDO {
    private String agencyID,agencyCaption,agencyDescription,agencyFirstname,agencyLastname,agencyPosition,agencyContactNumber,agencyStatus,agencyImage;

    public AgencyDO(String agencyID, String agencyCaption, String agencyDescription, String agencyFirstname, String agencyLastname, String agencyPosition, String agencyContactNumber, String agencyStatus, String agencyImage) {
        this.agencyID = agencyID;
        this.agencyCaption = agencyCaption;
        this.agencyDescription = agencyDescription;
        this.agencyFirstname = agencyFirstname;
        this.agencyLastname = agencyLastname;
        this.agencyPosition = agencyPosition;
        this.agencyContactNumber = agencyContactNumber;
        this.agencyStatus = agencyStatus;
        this.agencyImage = agencyImage;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public String getAgencyCaption() {
        return agencyCaption;
    }

    public String getAgencyDescription() {
        return agencyDescription;
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

    public String getAgencyImage() {
        return agencyImage;
    }
}
