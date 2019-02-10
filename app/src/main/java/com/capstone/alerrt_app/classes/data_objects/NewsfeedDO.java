package com.capstone.alerrt_app.classes.data_objects;

public class NewsfeedDO {
    String topicPostID,topicPosterUserID,topicPostedBy,topicDateAndTimePosted,topicLocationAddress,topicSeverity,topicTitle,topicImage,topicAgency,topicStatus;

    public NewsfeedDO(String topicPostID, String topicPosterUserID, String topicPostedBy, String topicDateAndTimePosted, String topicLocationAddress, String topicSeverity, String topicTitle, String topicImage, String topicAgency, String topicStatus) {
        this.topicPostID = topicPostID;
        this.topicPosterUserID = topicPosterUserID;
        this.topicPostedBy = topicPostedBy;
        this.topicDateAndTimePosted = topicDateAndTimePosted;
        this.topicLocationAddress = topicLocationAddress;
        this.topicSeverity = topicSeverity;
        this.topicTitle = topicTitle;
        this.topicImage = topicImage;
        this.topicAgency = topicAgency;
        this.topicStatus = topicStatus;
    }

    public String getTopicPostID() {
        return topicPostID;
    }

    public String getTopicPosterUserID() {
        return topicPosterUserID;
    }

    public String getTopicPostedBy() {
        return topicPostedBy;
    }

    public String getTopicDateAndTimePosted() {
        return topicDateAndTimePosted;
    }

    public String getTopicLocationAddress() {
        return topicLocationAddress;
    }

    public String getTopicSeverity() {
        return topicSeverity;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public String getTopicAgency() {
        return topicAgency;
    }

    public String getTopicStatus() {
        return topicStatus;
    }
}
