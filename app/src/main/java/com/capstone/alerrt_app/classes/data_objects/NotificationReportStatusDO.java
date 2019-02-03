package com.capstone.alerrt_app.classes.data_objects;

public class NotificationReportStatusDO {
    private String statusID,statusPostID,statusTopicTitle,statusAgencyCaption,statusType,updatedBy_fullname,updatedBy_profilePic,updatedBy_userID,statusDateAndTimeUpdated;

    public NotificationReportStatusDO(String statusID, String statusPostID, String statusTopicTitle, String statusAgencyCaption, String statusType, String updatedBy_fullname, String updatedBy_profilePic, String updatedBy_userID, String statusDateAndTimeUpdated) {
        this.statusID = statusID;
        this.statusPostID = statusPostID;
        this.statusTopicTitle = statusTopicTitle;
        this.statusAgencyCaption = statusAgencyCaption;
        this.statusType = statusType;
        this.updatedBy_fullname = updatedBy_fullname;
        this.updatedBy_profilePic = updatedBy_profilePic;
        this.updatedBy_userID = updatedBy_userID;
        this.statusDateAndTimeUpdated = statusDateAndTimeUpdated;
    }

    public String getStatusID() {
        return statusID;
    }

    public String getStatusPostID() {
        return statusPostID;
    }

    public String getStatusTopicTitle() {
        return statusTopicTitle;
    }

    public String getStatusAgencyCaption() {
        return statusAgencyCaption;
    }

    public String getStatusType() {
        return statusType;
    }

    public String getUpdatedBy_fullname() {
        return updatedBy_fullname;
    }

    public String getUpdatedBy_profilePic() {
        return updatedBy_profilePic;
    }

    public String getUpdatedBy_userID() {
        return updatedBy_userID;
    }

    public String getStatusDateAndTimeUpdated() {
        return statusDateAndTimeUpdated;
    }
}
