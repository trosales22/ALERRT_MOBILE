package com.capstone.alerrt_app.classes.data_objects;

public class NewsfeedDO {
    String topicPostID,topicPosterUserID,topic_postedBy,topic_dateAndTimePosted,topic_locationAddress,topicTitle,topicImage,topicDetails;

    public NewsfeedDO(String topicPostID, String topicPosterUserID, String topic_postedBy, String topic_dateAndTimePosted, String topic_locationAddress, String topicTitle, String topicImage, String topicDetails) {
        this.topicPostID = topicPostID;
        this.topicPosterUserID = topicPosterUserID;
        this.topic_postedBy = topic_postedBy;
        this.topic_dateAndTimePosted = topic_dateAndTimePosted;
        this.topic_locationAddress = topic_locationAddress;
        this.topicTitle = topicTitle;
        this.topicImage = topicImage;
        this.topicDetails = topicDetails;
    }

    public String getTopicPostID() {
        return topicPostID;
    }

    public String getTopicPosterUserID() {
        return topicPosterUserID;
    }

    public String getTopic_postedBy() {
        return topic_postedBy;
    }

    public String getTopic_dateAndTimePosted() {
        return topic_dateAndTimePosted;
    }

    public String getTopic_locationAddress() {
        return topic_locationAddress;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public String getTopicDetails() {
        return topicDetails;
    }
}
